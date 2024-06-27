package org.atom.client.module.impl.player;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;
import org.atom.Client;
import org.atom.api.event.annotations.EventTarget;
import org.atom.api.util.TimerUtil;
import org.atom.api.value.impl.BooleanValue;
import org.atom.api.value.impl.NumberValue;
import org.atom.client.event.client.EventTick;
import org.atom.client.event.network.EventPacketSend;
import org.atom.client.event.player.EventUpdate;
import org.atom.client.event.render.EventRender3D;
import org.atom.client.module.Category;
import org.atom.client.module.Module;
import org.atom.client.module.ModuleInfo;
import org.atom.client.module.impl.combat.KillAura;
import org.lwjgl.input.Keyboard;

import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

import static org.lwjgl.opengl.GL11.*;

// LiquidBounce
@ModuleInfo(name = "Blink", description = "Suspends all movement packets.", category = Category.Player, key = Keyboard.KEY_X)
public class Blink extends Module {

    private final LinkedBlockingQueue<Packet> packets = new LinkedBlockingQueue<>();
    private EntityOtherPlayerMP fakePlayer = null;
    private boolean disableLogger;
    private final LinkedList<double[]> positions = new LinkedList<>();

    private final BooleanValue pulseValue = new BooleanValue("Pulse", false);
    private final NumberValue pulseDelayValue = new NumberValue("PulseDelay", 1000, 500, 5000,100);
    private final BooleanValue cancelC0fValue = new BooleanValue("CancelC0F", true);

    private final TimerUtil pulseTimer = new TimerUtil();

    @EventTarget
    public void onTick(EventTick event) {
        KillAura killAura = new KillAura();
        if (killAura != null && killAura.isState()) {
            if (killAura.target.getName() == fakePlayer.getName()) {
                killAura.target = null;
            }
        }
    }
    @Override
    public void onEnable() {
        if(mc.thePlayer == null)
            return;

        if (!pulseValue.getValue()) {
            fakePlayer = new EntityOtherPlayerMP(mc.theWorld, mc.thePlayer.getGameProfile());
            fakePlayer.clonePlayer(mc.thePlayer, true);
            fakePlayer.copyLocationAndAnglesFrom(mc.thePlayer);
            fakePlayer.rotationYawHead = mc.thePlayer.rotationYawHead;
            mc.theWorld.addEntityToWorld(-1337, fakePlayer);
        }

        synchronized(positions) {
            positions.add(new double[] {mc.thePlayer.posX, mc.thePlayer.getEntityBoundingBox().minY + (mc.thePlayer.getEyeHeight() / 2), mc.thePlayer.posZ});
            positions.add(new double[] {mc.thePlayer.posX, mc.thePlayer.getEntityBoundingBox().minY, mc.thePlayer.posZ});
        }

        pulseTimer.reset();
    }

    @Override
    public void onDisable() {
        if(mc.thePlayer == null)
            return;

        blink();
        if (fakePlayer != null) {
            mc.theWorld.removeEntityFromWorld(fakePlayer.getEntityId());
            fakePlayer = null;
        }
    }

    @EventTarget
    public void onPacket(EventPacketSend event) {
        final Packet<?> packet = event.getPacket();

        if (mc.thePlayer == null || disableLogger)
            return;

        if (packet instanceof C03PacketPlayer) // Cancel all movement stuff
            event.setCancelled(true);

        if (packet instanceof C03PacketPlayer.C04PacketPlayerPosition || packet instanceof C03PacketPlayer.C06PacketPlayerPosLook ||
                packet instanceof C08PacketPlayerBlockPlacement ||
                packet instanceof C0APacketAnimation ||
                packet instanceof C0BPacketEntityAction || packet instanceof C02PacketUseEntity ||
                (cancelC0fValue.getValue() && packet instanceof C0FPacketConfirmTransaction)) {
            event.setCancelled(true);

            packets.add(packet);
        }
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        synchronized(positions) {
            positions.add(new double[] {mc.thePlayer.posX, mc.thePlayer.getEntityBoundingBox().minY, mc.thePlayer.posZ});
        }

        if(pulseValue.getValue() && pulseTimer.hasTimePassed(pulseDelayValue.getValue().longValue())) {
            blink();
            pulseTimer.reset();
        }
    }

    @EventTarget
    public void onRender3D(EventRender3D event) {

        synchronized(positions) {
            glPushMatrix();

            glDisable(GL_TEXTURE_2D);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            glEnable(GL_LINE_SMOOTH);
            glEnable(GL_BLEND);
            glDisable(GL_DEPTH_TEST);
            mc.entityRenderer.disableLightmap();
            glBegin(GL_LINE_STRIP);
            GlStateManager.resetColor();
            final double renderPosX = mc.getRenderManager().viewerPosX;
            final double renderPosY = mc.getRenderManager().viewerPosY;
            final double renderPosZ = mc.getRenderManager().viewerPosZ;

            for(final double[] pos : positions)
                glVertex3d(pos[0] - renderPosX, pos[1] - renderPosY, pos[2] - renderPosZ);

            glColor4d(1, 1, 1, 1);
            glEnd();
            glEnable(GL_DEPTH_TEST);
            glDisable(GL_LINE_SMOOTH);
            glDisable(GL_BLEND);
            glEnable(GL_TEXTURE_2D);
            glPopMatrix();
        }
    }

    @Override
    public String getTag() {
        return String.valueOf(packets.size());
    }

    private void blink() {
        try {
            disableLogger = true;

            while (!packets.isEmpty()) {
                mc.getNetHandler().getNetworkManager().sendPacket(packets.take());
            }

            disableLogger = false;
        }catch(final Exception e) {
            e.printStackTrace();
            disableLogger = false;
        }

        synchronized(positions) {
            positions.clear();
        }
    }
}
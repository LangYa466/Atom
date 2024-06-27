package org.atom.client.module.impl.combat;

import de.florianmichael.viamcp.fixes.AttackOrder;
import net.minecraft.entity.player.EntityPlayer;
import org.atom.Client;
import org.atom.api.event.annotations.EventTarget;
import org.atom.api.util.RotationUtil;
import org.atom.api.util.TimerUtil;
import org.atom.api.value.impl.NumberValue;
import org.atom.client.event.player.EventMotionUpdate;
import org.atom.client.event.player.EventMoveUpdate;
import org.atom.client.event.render.EventRender3D;
import org.atom.client.module.Category;
import org.atom.client.module.Module;
import org.atom.client.module.ModuleInfo;
import org.atom.client.module.impl.move.StrafeFix;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * @author cubk, langya
 */
@ModuleInfo(name = "KillAura",category = Category.Combat,key = Keyboard.KEY_R)
public class KillAura extends Module {

    public NumberValue maxCPSValue = new NumberValue("MaxCPS",13,1,20,1);
    public NumberValue minCPSValue = new NumberValue("MinCPS",8,1,20,1);
    public NumberValue rangeValue = new NumberValue("Range",3,3,5,0.1);

    public EntityPlayer target;
    private float[] angle;
    private TimerUtil attackTimer = new TimerUtil();

    public KillAura() {
        addValues(maxCPSValue,minCPSValue,rangeValue);
    }

    @EventTarget
    public void onUpdate(EventMotionUpdate event) {
        if (!Client.getInstance().getModuleManager().getModule("StrafeFix").isState()){
            if (angle != null){
                if (event.getYaw() != angle[0]) {
                    event.setYaw(angle[0]);
                }
                if (event.getPitch() != angle[1]) {
                    event.setPitch(angle[1]);
                }
            }
        }
    }


    @EventTarget
    public void onMotion(EventMoveUpdate event) {
        setTag("Single");

        for (Entity entity : mc.theWorld.loadedEntityList) {
            if (entity instanceof EntityPlayer && mc.thePlayer.getDistanceToEntity(entity) < rangeValue.getValue() && entity != mc.thePlayer && !entity.isDead && ((EntityPlayer) entity).getHealth() > 0){
                target = (EntityPlayer) entity;
                break;
            }
        }

        if (target == null) {
            angle = null;
            return;
        }
        if (target.getDistanceToEntity(mc.thePlayer) > rangeValue.getValue() || target.getHealth() <= 0 || target.isDead) {
            target = null;
            angle = null;
            return;
        }

        StrafeFix strafeFix = (StrafeFix) Client.getInstance().getModuleManager().getModule("StrafeFix");

        angle = RotationUtil.getRotationsNeeded(target);

        strafeFix.setAngle(angle);

        if (event.getYaw() != angle[0]) {
            event.setYaw(angle[0]);
        }
        if (event.getPitch() != angle[1]) {
            event.setPitch(angle[1]);
        }

        if(attackTimer.hasTimePassed(1000 / getCps())) {
            if (RotationUtil.isMouseOver(angle[0],angle[1],target,rangeValue.getValue().floatValue())) {
                AttackOrder.sendFixedAttack(mc.thePlayer, target);
                attackTimer.reset();
            }
        }

    }

    private int getCps() {
        final double maxValue = (maxCPSValue.getMaximum() - maxCPSValue.getValue()) * 20;
        final double minValue = (minCPSValue.getMaximum() - minCPSValue.getValue()) * 20;
        return (int) (Math.random() * (maxValue - minValue) + minValue);
    }

    @EventTarget
    public void onRender3D(EventRender3D event) {
        float range = rangeValue.getValue().floatValue();
        GL11.glPushMatrix();
        GL11.glTranslated(
                mc.thePlayer.lastTickPosX + (mc.thePlayer.posX - mc.thePlayer.lastTickPosX) * mc.timer.renderPartialTicks - RenderManager.renderPosX,
                mc.thePlayer.lastTickPosY + (mc.thePlayer.posY - mc.thePlayer.lastTickPosY) * mc.timer.renderPartialTicks - RenderManager.renderPosY,
                mc.thePlayer.lastTickPosZ + (mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ) * mc.timer.renderPartialTicks - RenderManager.renderPosZ
        );
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        Color color = Color.white;

        GL11.glLineWidth(1F);
        GL11.glColor4f(color.getRed(),color.getGreen(),color.getBlue(),color.getAlpha());
        GL11.glRotatef(90F, 1F, 0F, 0F);
        GL11.glBegin(GL11.GL_LINE_STRIP);

        for (int i = 0; i <= 360; i += 61 - 4) { // You can change circle accuracy (60 - accuracy)
            GL11.glVertex2f((float) Math.cos(i * Math.PI / 180.0) * range, (float) Math.sin(i * Math.PI / 180.0) * range);
        }
        GL11.glVertex2f((float) Math.cos(360 * Math.PI / 180.0) * range, (float) Math.sin(360 * Math.PI / 180.0) * range);

        GL11.glEnd();

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);

        GL11.glPopMatrix();
    }

}

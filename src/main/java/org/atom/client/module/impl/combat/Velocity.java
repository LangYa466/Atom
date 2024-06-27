package org.atom.client.module.impl.combat;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import org.atom.api.event.annotations.EventTarget;
import org.atom.api.value.impl.BooleanValue;
import org.atom.api.value.impl.NumberValue;
import org.atom.api.value.impl.StringValue;
import org.atom.client.event.client.EventTick;
import org.atom.client.event.network.EventPacketRead;
import org.atom.client.event.player.EventUpdate;
import org.atom.client.module.Category;
import org.atom.client.module.Module;
import org.atom.client.module.ModuleInfo;

/**
 * @author LangYa
 * @since 2024/06/21/下午4:26
 */
@ModuleInfo(name = "Velocity",category = Category.Combat)
public class Velocity extends Module {

    private final StringValue modeValue = new StringValue("Mode", "Grim", "Jump","Grim");
    private final NumberValue c02 = new NumberValue("C02", 12, 1, 12,1);
    private final BooleanValue c0b = new BooleanValue("C0B", true);


    public Velocity() {
        addValues(modeValue,c02,c0b);
    }

    @EventTarget
    public void onTick(EventTick event) {
        setTag(modeValue.getValue());

        if (mc.thePlayer != null && modeValue.getValue() == "Grim" && mc.thePlayer.hurtTime > 0 && new KillAura().target != null) {
            for (int i = 0; i < c02.getValue(); i++) {
                mc.getNetHandler().addToSendQueue(new C02PacketUseEntity(new KillAura().target, C02PacketUseEntity.Action.ATTACK));
                mc.thePlayer.swingItem();
            }
        }

    }

    @EventTarget
    public void onPacketRead(EventPacketRead event) {
        Packet packet = event.getPacket();

        if (packet instanceof S12PacketEntityVelocity) {

            if (mc.thePlayer == null || (mc.theWorld.getEntityByID(((S12PacketEntityVelocity) packet).getEntityID())) != mc.thePlayer) return;
            event.setCancelled(true);
            mc.thePlayer.motionY = ((S12PacketEntityVelocity) packet).getMotionY() / 8000.0;
        }
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {

        if (modeValue.getValue() == "Grim" && mc.thePlayer.hurtTime > 0 && new KillAura().target != null) {
            if (!mc.thePlayer.isSprinting() && c0b.getValue()) {
                mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));

                mc.thePlayer.setSprinting(true);
                mc.thePlayer.serverSprintState = true;
            }
            mc.thePlayer.motionX *= 0.077760000;
            mc.thePlayer.motionZ *= 0.077760000;
        }

        if (modeValue.getValue() == "Jump" && mc.thePlayer.hurtTime > 9) {
            if (mc.thePlayer.onGround) {
                mc.thePlayer.jump();
            }
        }

    }

}

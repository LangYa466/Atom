package org.atom.client.module.impl.move;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import org.atom.api.event.annotations.EventTarget;
import org.atom.api.util.MoveUtil;
import org.atom.api.value.impl.BooleanValue;
import org.atom.api.value.impl.NumberValue;
import org.atom.client.event.network.EventPacketRead;
import org.atom.client.event.network.EventPacketSend;
import org.atom.client.event.player.EventUpdate;
import org.atom.client.module.Category;
import org.atom.client.module.Module;
import org.atom.client.module.ModuleInfo;

/**
 * @author LangYa
 * @since 2024/06/21/下午5:16
 */
@ModuleInfo(name = "Speed", category = Category.Move)
public class Speed extends Module {

    BooleanValue onlyAir = new BooleanValue("OnlyAir", false);
    NumberValue distance = new NumberValue("Range", 1, 0, 2, 1);
    NumberValue speedV = new NumberValue("Speed", 0, 0, 10, 1);

    int f;
    @EventTarget
    public void onUpdate(EventUpdate event) {
        setTag(String.valueOf(MoveUtil.getSpeed()));
        f--;
        if (f >= 0) {
            mc.thePlayer.motionX = mc.thePlayer.motionX * 1;
            mc.gameSettings.keyBindForward.pressed = false;
            mc.gameSettings.keyBindLeft.pressed = false;
            mc.gameSettings.keyBindBack.pressed = false;
            mc.gameSettings.keyBindRight.pressed = false;
            mc.gameSettings.keyBindJump.pressed = false;
            return;
        }
        for (Entity entity : mc.theWorld.loadedEntityList) {
            if (entity instanceof EntityLivingBase && entity.getEntityId() != mc.thePlayer.getEntityId() && mc.thePlayer.getDistanceToEntity(entity) <= distance.getValue() && (!onlyAir.getValue() || !mc.thePlayer.onGround)) {
                double speed = (speedV.getValue() * (1 - mc.thePlayer.getDistanceToEntity(
                        entity
                ) / distance.getValue()));
                mc.thePlayer.motionX = (1 + (speed * 0.01));

                mc.thePlayer.motionZ = (1 + (speed * 0.01));
                return;
            }
        }
    }
    @EventTarget
    public void onPR(EventPacketRead event) {
        if (event.getPacket() instanceof S08PacketPlayerPosLook) {
            f = 10;
        }
    }
    @EventTarget
    public void onPs(EventPacketSend event) {
        if (event.getPacket() instanceof S08PacketPlayerPosLook) {
            f = 10;
        }
    }
}

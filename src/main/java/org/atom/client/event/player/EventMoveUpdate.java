package org.atom.client.event.player;

import lombok.Getter;
import lombok.Setter;
import org.atom.api.event.impl.CancellableEvent;
import net.minecraft.client.Minecraft;

/**
 * @author LangYa
 * @since 2024/06/19/下午10:11
 */
@Getter
@Setter
public class EventMoveUpdate extends CancellableEvent {
    private float strafe, forward, friction, yaw, pitch;

    public EventMoveUpdate(float strafe, float forward, float friction, float yaw, float pitch) {
        this.strafe = strafe;
        this.forward = forward;
        this.friction = friction;
        this.yaw = yaw;
        this.pitch = pitch;
    }
    public void setYaw(float yaw) {
        this.yaw = yaw;
        Minecraft mc = Minecraft.getMinecraft();
        mc.thePlayer.rotationYawHead = yaw;
    }
}

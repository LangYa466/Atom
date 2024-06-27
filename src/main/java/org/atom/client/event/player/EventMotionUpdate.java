package org.atom.client.event.player;

import lombok.Getter;
import lombok.Setter;
import org.atom.api.event.impl.CancellableEvent;
import net.minecraft.client.Minecraft;

/**
 * @author LangYa
 * @since 2024/06/19/下午10:20
 */
@Getter
@Setter
public class EventMotionUpdate extends CancellableEvent {
    private double x,y,z;
    private float yaw,pitch;
    private boolean onGround;

    public EventMotionUpdate(double x, double y, double z, float yaw, float pitch, boolean onGround) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
        Minecraft mc = Minecraft.getMinecraft();
        mc.thePlayer.rotationYawHead = yaw;
    }
}

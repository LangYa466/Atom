package org.atom.client.event.player;

import lombok.Getter;
import org.atom.api.event.impl.Event;
import net.minecraft.client.Minecraft;

/**
 * @author LangYa
 * @since 2024/06/19/下午10:02
 */
@Getter
public class EventJump implements Event {
    private float yaw;

    public EventJump(float yaw) {
        this.yaw = yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
        Minecraft mc = Minecraft.getMinecraft();
        mc.thePlayer.rotationYawHead = yaw;
    }

}

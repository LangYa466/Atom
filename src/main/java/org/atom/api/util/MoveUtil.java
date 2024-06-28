package org.atom.api.util;

import org.atom.Wrapper;

/**
 * @author LangYa
 * @since 2024/06/21/下午5:20
 */
public class MoveUtil implements Wrapper {

    public static boolean isMoving() {
        return mc.thePlayer != null && (mc.thePlayer.movementInput.moveForward != 0F || mc.thePlayer.movementInput.moveStrafe != 0F);
    }

    public static float getSpeed() {
        return (float) getSpeed(mc.thePlayer.motionX, mc.thePlayer.motionZ);
    }

    public static double getSpeed(double motionX, double motionZ) {
        return Math.sqrt(motionX * motionX + motionZ * motionZ);
    }

    public static void stop() {
        mc.thePlayer.motionX = 0;
        mc.thePlayer.motionZ = 0;
    }

    public static float getMoveYaw() {
        float moveYaw = mc.thePlayer.rotationYaw;
        if (mc.thePlayer.moveForward != 0F && mc.thePlayer.moveStrafing == 0F) {
            moveYaw += (mc.thePlayer.moveForward > 0) ? 0 : 180;
        } else if (mc.thePlayer.moveForward != 0F && mc.thePlayer.moveStrafing != 0F) {
            if (mc.thePlayer.moveForward > 0) {
                moveYaw += (mc.thePlayer.moveStrafing > 0) ? -45 : 45;
            } else {
                moveYaw -= (mc.thePlayer.moveStrafing > 0) ? -45 : 45;
            }
            moveYaw += (mc.thePlayer.moveForward > 0) ? 0 : 180;
        } else if (mc.thePlayer.moveStrafing != 0F && mc.thePlayer.moveForward == 0F) {
            moveYaw += (mc.thePlayer.moveStrafing > 0) ? -90 : 90;
        }
        return moveYaw;
    }


}

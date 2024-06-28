package org.atom.api.util;

import org.atom.Wrapper;

/**
 * @author LangYa
 * @since 2024/06/28/上午11:10
 */
public class MovementUtil implements Wrapper {
    public static boolean isMoving() {
        if (mc.thePlayer == null) {
            return false;
        }
        return (mc.thePlayer.movementInput.moveForward != 0F || mc.thePlayer.movementInput.moveStrafe != 0F);
    }
}

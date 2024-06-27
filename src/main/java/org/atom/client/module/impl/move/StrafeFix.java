package org.atom.client.module.impl.move;

import org.atom.api.event.annotations.EventTarget;
import org.atom.api.value.impl.BooleanValue;
import org.atom.client.event.player.EventJump;
import org.atom.client.event.player.EventMoveUpdate;
import org.atom.client.module.Category;
import org.atom.client.module.Module;
import org.atom.client.module.ModuleInfo;
import net.minecraft.util.MathHelper;

/**
 * @author cubk
 */
@ModuleInfo(name = "StrafeFix",category = Category.Move)
public class StrafeFix extends Module {

    public BooleanValue strictValue = new BooleanValue("Strict",false);

    private boolean strict;
    private float[] angle;
    private boolean needUpdate;

    public StrafeFix() {
        addValues(strictValue);
    }

    public void setAngle(float[] angle){
        this.angle = angle;
        this.needUpdate = true;
    }

    @EventTarget
    public void onMoveUpdate(EventMoveUpdate event) {
        if(!strictValue.getValue()) {
            if(angle == null|| !needUpdate) return;

            final float forward = event.getForward();
            final float strafe = event.getStrafe();

            final double yaw = MathHelper.wrapAngleTo180_double(Math.toDegrees(direction(mc.thePlayer.rotationYaw, forward, strafe)));

            if (forward == 0 && strafe == 0) {
                return;
            }

            float closestForward = 0, closestStrafe = 0, closestDifference = Float.MAX_VALUE;

            for (float predictedForward = -1F; predictedForward <= 1F; predictedForward += 1F) {
                for (float predictedStrafe = -1F; predictedStrafe <= 1F; predictedStrafe += 1F) {
                    if (predictedStrafe == 0 && predictedForward == 0) continue;

                    final double predictedAngle = MathHelper.wrapAngleTo180_double(Math.toDegrees(direction(angle[0], predictedForward, predictedStrafe)));
                    final double difference = Math.abs(yaw - predictedAngle);

                    if (difference < closestDifference) {
                        closestDifference = (float) difference;
                        closestForward = predictedForward;
                        closestStrafe = predictedStrafe;
                    }
                }
            }

            event.setForward(closestForward);
            event.setStrafe(closestStrafe);
        }

        if (needUpdate) {
            if (event.getYaw() != angle[0]) {
                event.setYaw(angle[0]);
            }
            if (event.getPitch() != angle[1]) {
                event.setPitch(angle[1]);
            }
            needUpdate = false;
        }

    }

    public double direction(float rotationYaw, final double moveForward, final double moveStrafing) {
        if (moveForward < 0F) rotationYaw += 180F;

        float forward = 1F;

        if (moveForward < 0F) forward = -0.5F;
        else if (moveForward > 0F) forward = 0.5F;

        if (moveStrafing > 0F) rotationYaw -= 90F * forward;
        if (moveStrafing < 0F) rotationYaw += 90F * forward;

        return Math.toRadians(rotationYaw);
    }

    @EventTarget
    public void onJump(EventJump event) {
        if (needUpdate){
            if (event.getYaw() != angle[0]) {
                event.setYaw(angle[0]);
            }
        }
    }

}

package org.atom.client.module.impl.move;

import net.minecraft.entity.*;
import org.atom.api.event.annotations.EventTarget;
import org.atom.api.value.impl.*;
import org.atom.client.event.player.EventStrafe;
import org.atom.client.module.*;

import java.util.Random;

/**
 * @author LangYa
 * @since 2024/06/21/下午5:16
 */
@ModuleInfo(name = "Speed", category = Category.Move)
public class Speed extends Module {

    BooleanValue hurtCancel = new BooleanValue("HurtCancel", true);
    private final Random random = new Random();

    @EventTarget
    public void onStrafe(EventStrafe event) {
        if (!isPlayerValid()) return;

        boolean shouldCancel = shouldCancelHurt();

        if (!shouldCancel) {
            for (Entity entity : mc.theWorld.loadedEntityList) {
                if (isValidEntity(entity)) {
                    applySpeedEffect();
                    break;
                }
            }
        }
    }

    private void applySpeedEffect() {
        // 随机增加速度
        double multiplier = 1.0 + random.nextDouble() * 0.15; //燃动 1-1.15
        mc.thePlayer.motionX *= multiplier;
        mc.thePlayer.motionZ *= multiplier;
    }

    private boolean isValidEntity(Entity entity) {
        return entity instanceof EntityLivingBase
                && entity.getEntityId() != mc.thePlayer.getEntityId()
                && entity.getDistanceSqToEntity(mc.thePlayer) <= 4.0;
    }

    private boolean shouldCancelHurt() {
        return mc.thePlayer.hurtTime > 0 && hurtCancel.getValue();
    }

    private boolean isPlayerValid() {
        return mc.thePlayer != null && mc.theWorld != null;
    }
}

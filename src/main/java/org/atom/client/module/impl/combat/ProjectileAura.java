package org.atom.client.module.impl.combat;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import org.atom.Client;
import org.atom.api.event.annotations.EventTarget;
import org.atom.api.util.RotationUtil;
import org.atom.api.value.impl.NumberValue;
import org.atom.client.event.player.EventMoveUpdate;
import org.atom.client.module.Category;
import org.atom.client.module.Module;
import org.atom.client.module.ModuleInfo;
import org.atom.client.module.impl.move.StrafeFix;

/**
 * @author LangYa
 * @since 2024/06/28/上午11:18
 */
@ModuleInfo(name = "ProjectileAura",category = Category.Combat)
public class ProjectileAura extends Module {
    public NumberValue rangeValue = new NumberValue("Range", 8, 5, 15, 1);

    public EntityLivingBase target;
    private float[] angle;
    private KillAura killAura = new KillAura();

    @EventTarget
    public void onMotion(EventMoveUpdate event) {
        setTag("Single");

        if (killAura.target != null) return;

        for (Entity entity : mc.theWorld.loadedEntityList) {
            if (entity instanceof EntityLivingBase && mc.thePlayer.getDistanceToEntity(entity) < rangeValue.getValue() && entity != mc.thePlayer && !entity.isDead && ((EntityLivingBase) entity).getHealth() > 0) {
                target = (EntityLivingBase) entity;
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


        if (RotationUtil.isMouseOver(angle[0],angle[1],target,rangeValue.getValue().floatValue())) {
            int lastSlot = mc.thePlayer.inventory.currentItem;
            if (getBestSlot(mc.thePlayer)) {
                mc.playerController.updateController();
                mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
                mc.thePlayer.inventory.currentItem = lastSlot;
                mc.playerController.updateController();
            }
        }
    }

    private boolean getBestSlot(EntityPlayerSP player) {
        int bestSlot = -1;
        for (int i = 0; i <= 8; i++) {
            ItemStack item = player.inventory.getStackInSlot(i);
            if (item != null && (item.getItem() instanceof ItemSnowball || item.getItem() instanceof ItemEgg)) {
                bestSlot = i;
            }
        }
        if (bestSlot != -1) {
            player.inventory.currentItem = bestSlot;
            return true;
        }

        return false;
    }


}

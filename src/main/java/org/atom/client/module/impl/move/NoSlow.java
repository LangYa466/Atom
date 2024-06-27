package org.atom.client.module.impl.move;

import net.minecraft.item.Item;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.atom.api.event.annotations.EventTarget;
import org.atom.client.event.network.EventPacketSend;
import org.atom.client.event.player.EventMotionUpdate;
import org.atom.client.event.player.EventMoveUpdate;
import org.atom.client.event.player.EventSlowDown;
import org.atom.client.event.player.EventUpdate;
import org.atom.client.module.Category;
import org.atom.client.module.Module;
import org.atom.client.module.ModuleInfo;

/**
 * @author LangYa
 * @since 2024/06/21/下午4:58
 */
@ModuleInfo(name = "NoSlow",category = Category.Move)
public class NoSlow extends Module {

    private boolean drop;

    @EventTarget
    public void onMove(EventMoveUpdate event) {
        setTag("Grim");
        mc.getNetHandler().addToSendQueue(new C0FPacketConfirmTransaction());
        mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.getHeldItem(), 0.0F, 0.0F, 0.0F));

            mc.getNetHandler().addToSendQueue(
                    new C07PacketPlayerDigging(
                            C07PacketPlayerDigging.Action.RELEASE_USE_ITEM,
                            BlockPos.ORIGIN,
                            EnumFacing.DOWN
                    )
            );

        if (mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemAppleGold && mc.thePlayer.itemInUseCount > 2 && drop) {
            int count = mc.thePlayer.getItemInUseCount();
            mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.DROP_ITEM, new BlockPos(0, 0, 0), EnumFacing.DOWN));
            drop = false;
            mc.thePlayer.itemInUseCount = count - 1;
        }
    }

    @EventTarget
    public void onSlowDown(EventSlowDown event) {
        event.setCancelled(true);
    }
    @EventTarget
    public void onPacket(EventPacketSend event) {
        Packet packet = event.getPacket();
        if (packet instanceof C08PacketPlayerBlockPlacement) {
            Item pi = ((C08PacketPlayerBlockPlacement) packet).getStack().getItem();
            if (pi instanceof ItemAppleGold && mc.gameSettings.keyBindRight.pressed) {
                drop = true;
            }
        }
    }
}

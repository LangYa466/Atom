package org.atom.client.module.impl.move;

import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.item.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.*;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.atom.api.event.annotations.EventTarget;
import org.atom.api.util.MovementUtil;
import org.atom.api.value.impl.*;
import org.atom.client.event.player.*;
import org.atom.client.module.*;

/**
 * @author LangYa
 * @since 2024/06/21/下午4:58
 */
@ModuleInfo(name = "NoSlow",category = Category.Move)
public class NoSlow extends Module {
    BooleanValue food = new BooleanValue("Food", true);
    BooleanValue bow = new BooleanValue("Bow", true);

    private boolean hasDroppedFood = false;
    public static boolean fix = false;

    public static boolean hasSwordwithout() {
        return Minecraft.getMinecraft().thePlayer.getHeldItem().getItem() instanceof ItemSword;
    }



    @EventTarget
    public void onSlowDown(EventSlowDown event) {
        if (mc.thePlayer == null || mc.theWorld == null || mc.thePlayer.getHeldItem() == null) return;
        if (mc.thePlayer.getHeldItem().getItem() instanceof ItemFood && food.getValue()) return;
        if (mc.thePlayer.getHeldItem() != null && (mc.thePlayer.getHeldItem().getItem() instanceof ItemSword
                || (mc.thePlayer.getHeldItem().getItem() instanceof ItemBow && bow.getValue())) && mc.thePlayer.isUsingItem())
            event.setCancelled(true);
        if (!mc.thePlayer.isSprinting() && !mc.thePlayer.isSneaking() && MovementUtil.isMoving()) {
            mc.thePlayer.setSprinting(true);
        }
    }

    @EventTarget
    public void onMove(EventMotionUpdate event) {
        setTag("Grim");
        /*
        if (!mc.isSingleplayer()) {
            if (e.isPre()) {
                if (mc.thePlayer == null || mc.theWorld == null || mc.thePlayer.getHeldItem() == null) return;
                ItemStack itemInHand = mc.thePlayer.getCurrentEquippedItem();
                ItemStack itemStack = mc.thePlayer.getHeldItem();
                int itemID = Item.getIdFromItem(itemInHand.getItem());
                int itemMeta = itemInHand.getMetadata();
                String itemId = itemInHand.getItem().getUnlocalizedName();
                if (mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemFood && food.getValue()) {
                    if (mc.thePlayer.getHeldItem() != null && (!((itemID == 322 && itemMeta == 1) || itemId.equals("item.appleGoldEnchanted")))) {
                        if (Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem() != null) {

                            if (Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock) {
                                Minecraft.getMinecraft().rightClickDelayTimer = 4;
                            } else {
                                Minecraft.getMinecraft().rightClickDelayTimer = 4;
                            }
                        }
                        if (mc.thePlayer.isUsingItem() && !hasDroppedFood && itemStack.stackSize > 1) {
                            mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.DROP_ITEM, new BlockPos(0, 0, 0), EnumFacing.DOWN));
                            hasDroppedFood = true;
                            fix = true;
                        } else if (!mc.thePlayer.isUsingItem()) {
                            hasDroppedFood = false;
                            new Thread(() -> {
                                try {
                                    Thread.sleep(500);
                                    fix = false;
                                } catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                }
                            }).start();

                        }
                    }
                } else {
                    fix = false;
                }
                if (Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem() != null) {
                    if ((mc.thePlayer.isBlocking()) || mc.thePlayer.isUsingItem() && hasSwordwithout()) {
                        mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange((mc.thePlayer.inventory.currentItem + 1) % 9));
                        mc.getNetHandler().addToSendQueue(new C17PacketCustomPayload("MadeByFire", new PacketBuffer(Unpooled.buffer())));
                        mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                    }
                    if (mc.thePlayer.getHeldItem().getItem() instanceof ItemBow && mc.thePlayer.isUsingItem() && bow.getValue() && !mc.thePlayer.isSneaking()) {
                        mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange((mc.thePlayer.inventory.currentItem + 1) % 9));
                        mc.getNetHandler().addToSendQueue(new C17PacketCustomPayload("MadeByFire", new PacketBuffer(Unpooled.buffer())));
                        mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                    }
                }
            }
            if (e.isPost()) {
                if (mc.thePlayer.getHeldItem() == null) return;
                if (mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword && mc.thePlayer.isUsingItem()) {
                    PacketWrapper useItem = PacketWrapper.create(29, null, Via.getManager().getConnectionManager().getConnections().iterator().next());
                    useItem.write(Type.VAR_INT, 1);
                    PacketUtil.sendToServer(useItem, Protocol1_8To1_9.class, true, true);
                    PacketWrapper useItem2 = PacketWrapper.create(29, null, Via.getManager().getConnectionManager().getConnections().iterator().next());
                    useItem2.write(Type.VAR_INT, 0);
                    PacketUtil.sendToServer(useItem2, Protocol1_8To1_9.class, true, true);
                }
                if (mc.thePlayer.getHeldItem().getItem() instanceof ItemBow && mc.thePlayer.isUsingItem() && bow.getValue()) {
                    PacketWrapper useItem = PacketWrapper.create(29, null, Via.getManager().getConnectionManager().getConnections().iterator().next());
                    useItem.write(Type.VAR_INT, 1);
                    PacketUtil.sendToServer(useItem, Protocol1_8To1_9.class, true, true);
                    PacketWrapper useItem2 = PacketWrapper.create(29, null, Via.getManager().getConnectionManager().getConnections().iterator().next());
                    useItem2.write(Type.VAR_INT, 0);
                    PacketUtil.sendToServer(useItem2, Protocol1_8To1_9.class, true, true);
                }
            }
            break;
        }

         */
    }
}
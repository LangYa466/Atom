package org.atom.client.module.impl.world;

import org.atom.api.event.annotations.EventTarget;
import org.atom.client.event.player.EventUpdate;
import org.atom.client.module.Category;
import org.atom.client.module.Module;
import org.atom.client.module.ModuleInfo;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Keyboard;

/**
 * @author LangYa
 * @since 2024/06/19/下午8:47
 */
@ModuleInfo(name = "SafeWalk",category = Category.Player,key = Keyboard.KEY_F)
public class SafeWalk extends Module {

    @EventTarget
    public void onUpdate(EventUpdate event) {
        mc.gameSettings.keyBindSneak.pressed = mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0, mc.thePlayer.posZ)).getBlock() == Blocks.air;
    }

    @Override
    public void onDisable() {
        if (mc.gameSettings.keyBindSneak.pressed) {
            mc.gameSettings.keyBindSneak.pressed = false;
        }
    }

}

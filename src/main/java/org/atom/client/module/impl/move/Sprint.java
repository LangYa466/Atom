package org.atom.client.module.impl.move;

import org.atom.api.event.annotations.EventTarget;
import org.atom.client.event.player.EventUpdate;
import org.atom.client.module.Category;
import org.atom.client.module.Module;
import org.atom.client.module.ModuleInfo;

/**
 * @author LangYa
 * @since 2024/06/19/下午8:05
 */
@ModuleInfo(name = "Sprint",category = Category.Move,startEnable = true)
public class Sprint extends Module {

    public Sprint() {
        setTag("Legit");
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        mc.gameSettings.keyBindSprint.pressed = true;
    }

}

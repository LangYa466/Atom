package org.atom.client.module.impl.player;

import org.atom.api.event.annotations.EventTarget;
import org.atom.api.value.impl.NumberValue;
import org.atom.client.event.player.EventUpdate;
import org.atom.client.module.Category;
import org.atom.client.module.Module;
import org.atom.client.module.ModuleInfo;

/**
 * @author LangYa
 * @since 2024/06/19/下午9:21
 */
@ModuleInfo(name = "FastPlace",category = Category.Player,startEnable = true)
public class FastPlace extends Module {
    public NumberValue speedValue = new NumberValue("Speed",0,3,3,1);

    public FastPlace() {
        addValues(speedValue);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        mc.rightClickDelayTimer = speedValue.getValue().intValue();
    }

    @Override
    public void onDisable() {
        mc.rightClickDelayTimer = 3;
    }
}

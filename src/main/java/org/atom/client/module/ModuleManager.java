package org.atom.client.module;

import lombok.Getter;
import org.atom.Client;
import org.atom.Wrapper;
import org.atom.api.event.annotations.EventTarget;
import org.atom.api.gui.click.ClickGuiScreen;
import org.atom.api.shader.PostProcessing;
import org.atom.client.event.client.EventKey;
import org.atom.client.module.impl.combat.KillAura;
import org.atom.client.module.impl.combat.Velocity;
import org.atom.client.module.impl.move.NoSlow;
import org.atom.client.module.impl.move.Speed;
import org.atom.client.module.impl.move.Sprint;
import org.atom.client.module.impl.move.StrafeFix;
import org.atom.client.module.impl.player.Blink;
import org.atom.client.module.impl.player.FastPlace;
import org.atom.client.module.impl.render.HUD;
import org.atom.client.module.impl.world.SafeWalk;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LangYa
 * @since 2024/06/19/下午7:01
 */

public class ModuleManager implements Wrapper {
    @Getter
    private List<Module> modules;

    public ModuleManager() {
        modules = new ArrayList<>();

        addModule(new Sprint());
        addModule(new HUD());
        addModule(new SafeWalk());
        addModule(new FastPlace());
        addModule(new StrafeFix());
        addModule(new KillAura());
        addModule(new Blink());
        // addModule(new Velocity());
        // addModule(new PostProcessing());
        // addModule(new NoSlow());
        // addModule(new Speed());

        Client.getInstance().getEventManager().register(this);
    }

    private void addModule(Module module) {
        modules.add(module);
        if (module.getInfo().startEnable()) {
            module.setSilentState(true);
        }
    }

    @EventTarget
    public void onKey(EventKey event) {
        for (Module module : modules) {
            if (module.getKeyCode() == event.getKey()) {
                module.toggle();
                break;
            }
        }

        if (event.getKey() == Keyboard.KEY_RSHIFT) {
            mc.displayGuiScreen(new ClickGuiScreen());
        }
    }

    public Module getModule(String moduleName) {
        for (Module module : modules) {
            if (module.getName().equalsIgnoreCase(moduleName)) {
                return module;
            }
        }
        return null;
    }

    public Module getModule(Module module) {
        for (Module moduleC : modules) {
            if (moduleC == module) {
                return moduleC;
            }
        }
        return null;
    }

    public List<Module> getModulesByCategory(Category category) {
        List<Module> modules = new ArrayList<>();
        for (Module module : this.modules) {
            if (module.getInfo().category() == category) {
                modules.add(module);
            }
        }
        return modules;
    }
}

package org.atom.client.module.impl.render;

import org.atom.Client;
import org.atom.api.event.annotations.EventTarget;
import org.atom.api.font.FontManager;
import org.atom.api.util.ClientColors;
import org.atom.api.value.impl.BooleanValue;
import org.atom.api.value.impl.NumberValue;
import org.atom.client.event.render.EventRender2D;
import org.atom.client.module.Category;
import org.atom.client.module.Module;
import org.atom.client.module.ModuleInfo;

import java.awt.*;
import java.util.ArrayList;

@ModuleInfo(name = "HUD",category = Category.Render,startEnable = true)
public class HUD extends Module {

    public BooleanValue array = new BooleanValue("Array", true);

    public NumberValue spacing = new NumberValue("Spacing",3,1,5,1);

    public HUD() {
        addValues(array,spacing);
    }

    public static ClientColors clientColors = new ClientColors();

    /**
     * Subscribe a {@link EventRender2D}
     * @author cubk
     * @param event Event
     */
    @EventTarget
    public void onRender2D(EventRender2D event) {

        FontManager.F40.drawStringWithShadow("Atom Client", 5, 5, -1);

        if(array.getValue()){
            int width = event.getScaledresolution().getScaledWidth();
            int y = 4;
            ArrayList<Module> enabledModules = new ArrayList<>();
            for (Module m : Client.getInstance().getModuleManager().getModules()) {
                if (m.isState() && m.isArray()) {
                    enabledModules.add(m);
                }
            }
            enabledModules.sort((o1, o2) -> FontManager.F18.getWidth(o2.getName()) - FontManager.F18.getWidth(o1.getName()));
            for (Module module : enabledModules) {
                int moduleWidth = FontManager.F18.getWidth(module.getName());
                FontManager.F18.drawStringWithShadow(module.getName(), width - moduleWidth - 4, y, -1);
                y += (int) (FontManager.F18.getHeight() + spacing.getValue());
            }
        }
    }
}

package org.atom.client.event.render;

import lombok.Getter;
import org.atom.api.event.impl.Event;
import net.minecraft.client.gui.ScaledResolution;

/**
 * @author LangYa
 * @since 2024/06/19/下午8:24
 */

@Getter
public class EventRender2D implements Event {
    private float partialTicks;
    private ScaledResolution scaledresolution;
    public EventRender2D(float partialTicks, ScaledResolution scaledresolution) {
        this.partialTicks = partialTicks;
        this.scaledresolution = scaledresolution;
    }
}

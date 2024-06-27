package org.atom.client.event.render;

import lombok.Getter;
import org.atom.api.event.impl.Event;

/**
 * @author LangYa
 * @since 2024/06/19/下午10:30
 */
@Getter
public class EventRender3D implements Event {
    private float partialTicks;
    public EventRender3D(float partialTicks) {
        this.partialTicks = partialTicks;
    }
}

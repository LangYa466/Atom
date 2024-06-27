package org.atom.client.event.client;

import lombok.Getter;
import org.atom.api.event.impl.Event;

/**
 * @author LangYa
 * @since 2024/06/19/下午8:07
 */
public class EventKey implements Event {
    @Getter
    private int key;

    public EventKey(int key) {
        this.key = key;
    }

}

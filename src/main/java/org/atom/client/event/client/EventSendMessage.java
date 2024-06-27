package org.atom.client.event.client;

import lombok.Getter;
import lombok.Setter;
import org.atom.api.event.impl.CancellableEvent;

/**
 * @author LangYa
 * @since 2024/06/19/下午9:30
 */
public class EventSendMessage extends CancellableEvent {
    @Getter
    @Setter
    private String message;

    public EventSendMessage(String message) {
        this.message = message;
    }
}

package org.atom.client.event.network;

import lombok.Getter;
import net.minecraft.network.Packet;
import org.atom.api.event.impl.CancellableEvent;

/**
 * @author LangYa
 * @since 2024/06/21/下午5:05
 */
@Getter
public class EventPacketSend extends CancellableEvent {
    private Packet packet;

    public EventPacketSend(Packet packet) {
        this.packet = packet;
    }
}

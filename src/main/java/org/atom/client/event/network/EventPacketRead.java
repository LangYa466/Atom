package org.atom.client.event.network;

import lombok.Getter;
import net.minecraft.network.Packet;
import org.atom.api.event.impl.CancellableEvent;

/**
 * @author LangYa
 * @since 2024/06/21/下午5:06
 */
@Getter
public class EventPacketRead extends CancellableEvent {
    private Packet packet;

    public EventPacketRead(Packet packet) {
        this.packet = packet;
    }
}
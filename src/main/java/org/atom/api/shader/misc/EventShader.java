package org.atom.api.shader.misc;
import lombok.Getter;
import org.atom.api.event.impl.Event;

@Getter
public class EventShader implements Event {

    private final boolean bloom;

    public EventShader(boolean bloom) {
        this.bloom = bloom;
    }

}

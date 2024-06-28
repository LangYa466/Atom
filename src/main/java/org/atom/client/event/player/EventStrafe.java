package org.atom.client.event.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.atom.api.event.impl.CancellableEvent;
import org.atom.api.util.MoveUtil;

import static org.atom.Wrapper.mc;

@Getter
@Setter
@AllArgsConstructor
public final class EventStrafe extends CancellableEvent {

    private float forward;
    private float strafe;
    private float friction;
    private float yaw;
    

    public void setSpeed(final double speed) {
        setFriction((float) (getForward() != 0 && getStrafe() != 0 ? speed * 0.98F : speed));
        MoveUtil.stop();
    }
}

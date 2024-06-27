package org.atom.client.module;

import lombok.Getter;
import lombok.Setter;
import org.atom.Client;
import org.atom.Wrapper;
import org.atom.api.notification.NotificationType;
import org.atom.api.value.AbstractValue;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author LangYa
 * @since 2024/06/19/下午7:01
 */

@Getter
@Setter
public class Module implements Wrapper {

    private ModuleInfo info = this.getClass().getAnnotation(ModuleInfo.class);

    private String name;
    private Category category;
    private String description;
    private String tag;
    private int keyCode;

    private boolean state;
    private boolean array;

    private List<AbstractValue> values = new CopyOnWriteArrayList<>();

    public Module() {
        name = info.name();
        category = info.category();
        array = info.array();

        if (!info.description().isEmpty()) description = info.description();
        if (info.key() != 114514) keyCode = info.key();

    }

    public void addValues(AbstractValue... values) {
        getValues().addAll(Arrays.asList(values));
    }

    public void onEnable() { }
    public void onDisable() { }

    public synchronized void setState(boolean state) {
        this.state = state;
        if (state) {
            onEnable();
            Client.getInstance().getEventManager().register(this);
            Client.getInstance().getNotificationManager().post(name + " Enabled", NotificationType.SUCCESS);
        } else {
            onDisable();
            Client.getInstance().getEventManager().unregister(this);
            Client.getInstance().getNotificationManager().post(name + " Disabled", NotificationType.FAILED);
        }
    }

    public void toggle() {
        setState(!state);
    }

    public void setSilentState(boolean state) {
        this.state = state;
        if (state) {
            onEnable();
            Client.getInstance().getEventManager().register(this);
            Client.getInstance().getNotificationManager().post(name + " Enabled", NotificationType.SUCCESS);
        } else {
            onDisable();
            Client.getInstance().getEventManager().unregister(this);
            Client.getInstance().getNotificationManager().post(name + " Disabled", NotificationType.FAILED);
        }
    }

}

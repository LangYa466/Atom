package org.atom.client.element;

import lombok.Getter;
import org.atom.Client;
import org.atom.api.event.annotations.EventTarget;
import org.atom.api.shader.misc.EventShader;
import org.atom.client.element.impls.ClientLogo;
import org.atom.client.event.render.EventRender2D;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author LangYa466
 * @since 2024/2/23 18:32
 */

@Getter
public class ElementManager {
    List<Element> elements;

    public ElementManager() {
        elements = new CopyOnWriteArrayList<>();
        Client.getInstance().getEventManager().register(this);
        this.registers();
    }

    void registers() {
        // addElement(new ClientLogo(), "ClientLogo");
    }

    private void addElement(Element element, String name) {
        element.setName(name);
        elements.add(element);
    }

    private void addElement(Element element) {
        element.setName(element.getClass().getSimpleName());
        elements.add(element);
    }

    @EventTarget
    private void onRender2D(EventRender2D e) {
        for (Element element : elements) {
            element.update(false);
        }
    }

    @EventTarget
    private void onShader(EventShader e) {
        for (Element element : elements) {
            element.update(true);
        }
    }

}
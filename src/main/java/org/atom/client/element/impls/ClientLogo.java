package org.atom.client.element.impls;

import org.atom.Client;
import org.atom.api.font.FontManager;
import org.atom.client.element.Element;

/**
 * @author LangYa466
 * @since 2024/4/11 19:05
 */

public class ClientLogo extends Element {

    @Override
    public void draw() {
        setXY(5,5);
        setWH(FontManager.F40.getWidth(Client.name),FontManager.F40.getHeight());
        FontManager.F40.drawStringWithShadow(Client.name, x, y,-1);
    }


}
package org.atom.api.util;

import org.atom.Client;

import java.awt.*;

/**
 * @author LangYa
 * @since 2024/06/20/下午5:51
 */
public class ClientColors {
    public Color getFirst() {
        return Client.colorC;
    }
    public Color getSecond() {
        return Client.colorC.darker();
    }
}

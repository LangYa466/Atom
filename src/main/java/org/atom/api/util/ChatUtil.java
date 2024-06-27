package org.atom.api.util;

import org.atom.Client;
import org.atom.Wrapper;
import net.minecraft.util.ChatComponentText;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author LangYa
 * @since 2024/06/19/下午8:50
 */
public class ChatUtil implements Wrapper {

    public static final Logger logger = LogManager.getLogger(Client.name);

    public static void sendMessage(String message) {
        mc.thePlayer.sendChatMessage(message);
    }

    public static void addMessage(String message) {
        mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(message));
    }
}

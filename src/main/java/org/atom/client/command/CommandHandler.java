package org.atom.client.command;

import org.atom.Client;
import org.atom.api.event.annotations.EventTarget;
import org.atom.api.util.ChatUtil;
import org.atom.client.event.client.EventSendMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chimera
 */
public class CommandHandler {
    private final String prefix = ".";

    @EventTarget
    private void onSendMessage(EventSendMessage event) {
        String rawMessage = event.getMessage();
        if (!rawMessage.startsWith(this.prefix)) {
            return;
        } else {
            event.setCancelled(true);
        }

        boolean safe = rawMessage.length() > 1;
        if (safe) {
            String beheaded = rawMessage.substring(1);
            String[] args = beheaded.split(" ");
            Command command = Client.getInstance().getCommandManager().getCommand(args[0]);

            if (command != null) {
                if (!command.run(args)) {
                    ChatUtil.addMessage(command.usage());
                }
            } else {
                ChatUtil.addMessage("Try .help.");
            }
        } else {
            ChatUtil.addMessage("Try .help.");
        }
    }



}

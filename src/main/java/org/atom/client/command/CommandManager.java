package org.atom.client.command;

import lombok.Getter;
import org.atom.Client;
import org.atom.api.util.ChatUtil;
import org.atom.client.command.impl.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chimera
 */

public class CommandManager {
    @Getter
    private HashMap<String[], Command> commands = new HashMap();
    private final String prefix = ".";

    public CommandManager() {
        Client.getInstance().getEventManager().register(new CommandHandler());
        this.commands.put(new String[]{"help", "h"}, new HelpCommand());
        this.commands.put(new String[]{"bind", "b"}, new BindCommand());
        this.commands.put(new String[]{"toggle", "t"}, new ToggleCommand());
    }

    public boolean processCommand(String rawMessage) {
        boolean safe;
        if (!rawMessage.startsWith(this.prefix)) {
            return false;
        }
        boolean bl = safe = rawMessage.length() > 1;
        if (safe) {
            String beheaded = rawMessage.substring(1);
            String[] args = beheaded.split(" ");
            Command command = this.getCommand(args[0]);
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
        return true;
    }

    public Command getCommand(String name) {
        for (Map.Entry<String[], Command> entry : this.commands.entrySet()) {
            for (String s : entry.getKey()) {
                if (!s.equalsIgnoreCase(name)) continue;
                return entry.getValue();
            }
        }
        return null;
    }


}
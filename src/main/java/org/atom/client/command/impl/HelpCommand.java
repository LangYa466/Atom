package org.atom.client.command.impl;


import org.atom.Client;
import org.atom.api.util.ChatUtil;
import org.atom.client.command.Command;

/**
 * @author chimera
 */
public class HelpCommand implements Command {

    @Override
    public boolean run(String[] args) {
        ChatUtil.addMessage("Here are the list of commands:");
        for (Command c : Client.getInstance().getCommandManager().getCommands().values()) {
            ChatUtil.addMessage(c.usage());
        }
        return true;
    }

    @Override
    public String usage() {
        return "USAGE: .help";
    }
}
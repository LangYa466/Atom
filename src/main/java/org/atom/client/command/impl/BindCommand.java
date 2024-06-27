package org.atom.client.command.impl;

import org.atom.Client;
import org.atom.api.util.ChatUtil;
import org.atom.client.command.Command;
import org.atom.client.module.Module;
import org.lwjgl.input.Keyboard;

/**
 * @author chimera
 */
public class BindCommand implements Command {
    @Override
    public boolean run(String[] args) {
        if (args.length == 3) {
            Module m = Client.getInstance().getModuleManager().getModule(args[1]);
            if (m == null) {
                return false;
            }
            m.setKeyCode(Keyboard.getKeyIndex(args[2].toUpperCase()));
            ChatUtil.addMessage(m.getName() + " has been bound to " + args[2] + ".");
            return true;
        }
        return false;
    }

    @Override
    public String usage() {
        return "USAGE: .bind [module] [key]";
    }
}
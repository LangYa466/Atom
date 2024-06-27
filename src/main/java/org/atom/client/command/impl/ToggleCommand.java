package org.atom.client.command.impl;

import org.atom.Client;
import org.atom.api.util.ChatUtil;
import org.atom.client.command.Command;
import org.atom.client.module.Module;

/**
 * @author chimera
 */
public class ToggleCommand implements Command {
    @Override
    public boolean run(String[] args) {
        if (args.length == 2) {
            Module module = Client.getInstance().getModuleManager().getModule(args[1]);
            if (module == null) {
                ChatUtil.addMessage("The module with the name " + args[1] + " does not exist.");
                return true;
            }
            module.toggle();
            return true;
        }
        return false;
    }

    @Override
    public String usage() {
        return "USAGE: .toggle [module]";
    }
}
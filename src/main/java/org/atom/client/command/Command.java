package org.atom.client.command;

public interface Command {
    boolean run(String[] var1);

    String usage();
}
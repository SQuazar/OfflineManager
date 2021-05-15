package net.flawe.offlinemanager.commands;

import net.flawe.offlinemanager.api.command.ICommand;
import net.flawe.offlinemanager.api.command.ICommandManager;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements ICommandManager {

    private static final List<ICommand> subs = new ArrayList<>();

    @Override
    public List<ICommand> getSubCommands() {
        return subs;
    }

    @Override
    public void addSubCommand(ICommand command) {
        subs.add(command);
    }

    @Override
    public void removeSubCommand(ICommand command) {
        subs.remove(command);
    }

    @Override
    public void removeSubCommand(String name) {
        subs.removeIf(cmd -> cmd.getName().equals(name));
    }
}

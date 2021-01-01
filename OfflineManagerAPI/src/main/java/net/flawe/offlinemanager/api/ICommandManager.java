package net.flawe.offlinemanager.api;

import java.util.List;

public interface ICommandManager {
    List<ICommand> getSubCommands();
    void addSubCommand(ICommand command);
    void removeSubCommand(ICommand command);
    void removeSubCommand(String name);
}

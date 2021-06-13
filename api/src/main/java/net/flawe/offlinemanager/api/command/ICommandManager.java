package net.flawe.offlinemanager.api.command;

import java.util.List;

/**
 * Class for initialization commands
 *
 * @author flawe
 */
public interface ICommandManager {
    /**
     * Gets the sub command list
     * @return sub command list
     */
    List<ICommand> getSubCommands();

    /**
     * Adds the sub command to main plugin command
     * @param command new sub command
     */
    void addSubCommand(ICommand command);

    /**
     * Removes the sub command from sub command list
     * @param command sub command
     */
    void removeSubCommand(ICommand command);

    /**
     * Removes the sub command from sub command list
     * @param name sub command name
     */
    void removeSubCommand(String name);
}

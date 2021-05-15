package net.flawe.offlinemanager.api.command;

import net.flawe.offlinemanager.api.command.ICommand;

import java.util.List;

/**
 * Class for initialization commands
 *
 * @author flawe
 */
public interface ICommandManager {
    /**
     * Used for searching sub commands and tab complete
     *
     * @return Sub command list
     */
    List<ICommand> getSubCommands();

    /**
     * Add sub command to list in command manager
     *
     * @param command Sub command whose will added to list in command manager
     */
    void addSubCommand(ICommand command);

    /**
     * Remove sub command from list in command manager
     *
     * @param command Sub command whose will removed from list in command manager
     */
    void removeSubCommand(ICommand command);

    /**
     * Remove sub command from list in command manager by command name
     *
     * @param name Command name whose will removed from list in command manager
     */
    void removeSubCommand(String name);
}

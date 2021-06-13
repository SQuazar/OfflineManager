package net.flawe.offlinemanager.api.command;

import net.flawe.offlinemanager.api.IPlaceholder;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Interface for implementing all commands
 *
 * @author flawe
 */
public interface ICommand {
    /**
     * Gets the command name
     * @return command name
     */
    String getName();

    /**
     * Gets the command permission
     * @return command permission
     */
    String getPermission();

    /**
     * Gets the help for command
     * @return command help information
     */
    String getHelp();

    /**
     * Gets the command aliases
     * @return command aliases
     */
    String[] getAliases();

    /**
     * Executes the command
     * @param player the command executor
     * @param args   command arguments
     */
    void execute(Player player, String[] args);

    /**
     * Checks if player has permission on command
     * @param player command sender
     * @return true if player has permission on command
     */
    default boolean hasPermission(Player player) {
        return player.hasPermission(getPermission());
    }

    /**
     * Adds the placeholder to command
     * @param key   placeholder key
     * @param value placeholder value
     */
    @Deprecated
    default void addPlaceholder(String key, String value) { }

    /**
     * Adds the placeholder to command
     * @param placeholder placeholder
     */
    default void addPlaceholder(IPlaceholder placeholder) { }

    /**
     * Adds the placeholders to command
     * @param placeholders placeholders
     */
    default void addPlaceholders(IPlaceholder... placeholders) { }

    /**
     * Removes the placeholder from command
     * @param key placeholder key
     */
    default void removePlaceholder(String key) { }

    /**
     * Removes the placeholder from command
     * @param placeholder placeholder
     */
    default void removePlaceholder(IPlaceholder placeholder) { }

    /**
     * Gets the placeholders as map
     * @return placeholders as map
     */
    default Map<String, String> getPlaceholdersAsMap() {
        return new HashMap<>();
    }

    /**
     * Gets the placeholders set collection
     * @return placeholders set collection
     */
    default Set<IPlaceholder> getPlaceholders() {
        return new HashSet<>();
    }

}

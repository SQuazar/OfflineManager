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
     * Used for searching command and usage command
     *
     * @return Command name
     */
    String getName();

    /**
     * Used for check command sender permission
     *
     * @return Command permission
     */
    String getPermission();

    /**
     * Used for command list
     *
     * @return Command help information
     */
    String getHelp();

    /**
     * Used for searching and usage command
     *
     * @return Command aliases
     */
    String[] getAliases();

    /**
     * Method for executing command
     *
     * @param player Command executor
     * @param args   Command arguments
     */
    void execute(Player player, String[] args);

    /**
     * Check player permission
     *
     * @param player Player for check
     * @return True if player has permission to command
     */
    default boolean hasPermission(Player player) {
        return player.hasPermission(getPermission());
    }

    /**
     * Add placeholder to command response
     *
     * @param key   Placeholder key
     * @param value Placeholder value
     */
    default void addPlaceholder(String key, String value) { }

    default void addPlaceholder(IPlaceholder placeholder) { }

    default void addPlaceholders(IPlaceholder... placeholders) { }

    /**
     * Remove placeholder in command response by placeholder key
     *
     * @param key Placeholder key
     */
    default void removePlaceholder(String key) { }

    default void removePlaceholder(IPlaceholder placeholder) { }

    /**
     * Used for filling placeholders in command response
     *
     * @return Map with placeholders
     */
    default Map<String, String> getPlaceholdersMap() {
        return new HashMap<>();
    }

    default Set<IPlaceholder> getPlaceholders() {
        return new HashSet<>();
    }

}

package net.flawe.offlinemanager.api.command;

import org.bukkit.entity.Player;

import java.util.Map;

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
    boolean hasPermission(Player player);

    /**
     * Add placeholder to command response
     *
     * @param key   Placeholder key
     * @param value Placeholder value
     */
    void addPlaceholder(String key, String value);

    /**
     * Remove placeholder in command response by placeholder key
     *
     * @param key Placeholder key
     */
    void removePlaceholder(String key);

    /**
     * Used for filling placeholders in command response
     *
     * @return Map with placeholders
     */
    Map<String, String> getPlaceholders();
}

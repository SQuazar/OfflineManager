package net.flawe.offlinemanager.api.data;

import org.bukkit.entity.Player;

/**
 * Helper class for interactions with configuration
 *
 * @author flawe
 */
public interface IConfigManager {

    /**
     * Method for configuration update (Not working in current version)
     */
    void update();

    /**
     * Colorize and fill PAPI placeholders (if enabled) in message
     *
     * @param player Player for %player% placeholder
     * @param s      Message to be filled
     * @return Formatted message with color and PAPI placeholders
     */
    String getMessage(Player player, String s);

    /**
     * Reload the plugin configuration
     */
    void reloadConfig();

    /**
     * Fill message with placeholders
     * @param player Command sender / Placeholder owner
     * @param s Message for fill
     * @return Filled message
     */
    String fillMessage(Player player, String s);

    /**
     * Colorize message from parameter
     *
     * @param s Message to be formatting
     * @return Formatted message from parameter
     */
    @Deprecated
    String getMessage(String s);


}

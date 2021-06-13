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
     * Gets the colorized and filled PAPI placeholders (if enabled) in message
     *
     * @param player player for %player% placeholder
     * @param s      message to be filled
     * @return formatted message with color and PAPI placeholders
     */
    @Deprecated
    String getMessage(Player player, String s);

    /**
     * Reload the plugin configuration
     */
    void reloadConfig();

    /**
     * Gets the filled message with placeholders
     * @param player command sender / placeholder owner
     * @param s message for fill
     * @return filled message
     */
    String fillMessage(Player player, String s);

    /**
     * Gets the colorized message from parameter
     *
     * @param s message to be formatting
     * @return formatted message from parameter
     */
    @Deprecated
    String getMessage(String s);


}

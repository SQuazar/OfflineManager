package net.flawe.offlinemanager.api.configuration;

/**
 * Command configuration interface. Used for loading configuration.
 *
 * @author flawe
 */
public interface CommandConfiguration extends Configuration {
    /**
     * Command enabled option
     *
     * @return True if option in configuration is true
     */
    boolean enabled();

    /**
     * Interface for specific command
     */
    interface CommandKillConfiguration extends CommandConfiguration {
        /**
         * Clear items in inventory
         *
         * @return Result by specific path in configuration
         */
        boolean clearItems();

        /**
         * Drop items from player or not
         *
         * @return Result by specific path in configuration
         */
        boolean dropItems();
    }
}

package net.flawe.offlinemanager.api.configuration;

/**
 * Command configuration interface. Used for loading configuration.
 *
 * @author flawe
 */
public interface CommandConfiguration extends Configuration {
    /**
     * Checks if command is enabled
     * @return true if command is enabled
     */
    boolean enabled();

    /**
     * Interface for specific command
     */
    interface CommandKillConfiguration extends CommandConfiguration {
        /**
         * Checks if item cleanup is enabled
         * @return true if item cleanup is enabled
         */
        boolean clearItems();

        /**
         * Checks if drop items is enabled
         * @return true if drop items is enabled
         */
        boolean dropItems();
    }
}

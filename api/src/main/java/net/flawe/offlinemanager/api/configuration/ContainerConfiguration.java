package net.flawe.offlinemanager.api.configuration;

/**
 * Interface for containers configuration. Used for load configuration to class.
 *
 * @author flawe
 */
public interface ContainerConfiguration extends Configuration {
    /**
     * Checks if inventory is enabled
     * @return true if option in configuration is true
     */
    boolean enabled();

    /**
     * Checks if interact is enabled
     * @return true if interact is enabled
     */
    boolean interact();

    /**
     * Gets the inventory name
     * @return inventory name
     */
    String getName();
}

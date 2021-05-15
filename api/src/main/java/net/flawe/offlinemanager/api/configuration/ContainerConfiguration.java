package net.flawe.offlinemanager.api.configuration;

/**
 * Interface for containers configuration. Used for load configuration to class.
 *
 * @author flawe
 */
public interface ContainerConfiguration extends Configuration {
    /**
     * Inventory enabled check
     *
     * @return True if option in configuration is true
     */
    boolean enabled();

    /**
     * Can player interact with inventory or not.
     *
     * @return Result by specific path in configuration
     */
    boolean interact();

    /**
     * Inventory name
     *
     * @return Inventory name from configuration
     */
    String getName();
}

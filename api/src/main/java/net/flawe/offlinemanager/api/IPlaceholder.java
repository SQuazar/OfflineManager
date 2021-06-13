package net.flawe.offlinemanager.api;

/**
 * Using for plugin command placeholders
 * @author flawe
 */
public interface IPlaceholder {

    /**
     * Gets placeholder key
     * @return key value
     */
    String getKey();

    /**
     * Gets placeholder value
     * @return value
     */
    String getValue();

}

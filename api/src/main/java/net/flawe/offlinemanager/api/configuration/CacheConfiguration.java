package net.flawe.offlinemanager.api.configuration;

/**
 * The cache configuration
 * @author flawe
 */
public interface CacheConfiguration extends Configuration {

    /**
     * Gets the cache size
     * @return cache size
     */
    int getSize();

//    int getLifeTime();

}

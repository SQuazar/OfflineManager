package net.flawe.offlinemanager.api.configuration;

public interface CacheConfiguration extends Configuration {

    int getSize();

    int getLifeTime();

}

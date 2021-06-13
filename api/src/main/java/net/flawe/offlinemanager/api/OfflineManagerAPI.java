package net.flawe.offlinemanager.api;

import net.flawe.offlinemanager.api.command.ICommandManager;
import net.flawe.offlinemanager.api.data.IConfigManager;
import net.flawe.offlinemanager.api.data.INMSManager;
import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.entity.IUser;
import net.flawe.offlinemanager.api.memory.ISession;
import net.flawe.offlinemanager.api.memory.IStorage;

import java.util.UUID;

/**
 * API class for interact with plugin functions
 * @author flawe
 */
public interface OfflineManagerAPI {

    /**
     * Gets the offline user.
     * @deprecated use getPlayerData method
     * @param username target player username
     * @return offline player instance
     */
    @Deprecated
    IUser getUser(String username);

    /**
     * Gets the offline user.
     * @deprecated use getPlayerData method
     * @param uuid target player uuid
     * @return offline player instance
     */
    @Deprecated
    IUser getUser(UUID uuid);

    /**
     * Gets the offline player data
     * @param username player name
     * @return data of player
     */
    IPlayerData getPlayerData(String username);

    /**
     * Gets the offline player data
     * @param uuid player uuid
     * @return data of player
     */
    IPlayerData getPlayerData(UUID uuid);

    /**
     * Gets the plugin configuration manager
     * @return configuration manager
     */
    IConfigManager getConfigManager();

    /**
     * Gets class-helper for getting PlayerData and other classes whose work depend NMS
     * @return NMSManager based on server version
     */
    INMSManager getNMSManager();

    /**
     * Gets the plugin storage (nicknames, player data cache)
     * @return storage instance from plugin class
     */
    IStorage getStorage();

    /**
     * Gets the class-helper for control inventory access
     * @return storage instance from plugin class
     */
    ISession getSession();

    /**
     * Gets the plugin command manager. You can add your own commands
     * @return command manager
     */
    ICommandManager getCommandManager();

    /**
     * Gets the your server version
     * @return server version
     */
    String getVersion();

    /**
     * Checks for PlaceholderAPI in the plugins folder
     * @return checks result
     */
    boolean papi();

    /**
     * Reload the plugin
     */
    void reload();
}

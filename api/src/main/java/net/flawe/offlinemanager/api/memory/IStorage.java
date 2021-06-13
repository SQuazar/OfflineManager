package net.flawe.offlinemanager.api.memory;

import com.google.common.cache.Cache;
import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

/**
 * The player storage class
 *
 * @author flawe
 */
public interface IStorage {
    /**
     * Add all current players nickname from playerdata
     */
    void init();

    /**
     * Add new player nickname to list in storage
     *
     * @param s player nickname
     */
    void add(String s);

    /**
     * Remove player nickname from list in storage
     *
     * @param s player nickname
     */
    void remove(String s);

    /**
     * Adding a player to cache
     * @param playerData player data
     */
    void addPlayerDataToCache(@NotNull IPlayerData playerData);

    /**
     * Removing a player from cache
     * @param uuid player uuid
     */
    void removePlayerDataFromCache(@NotNull UUID uuid);

    /**
     * Getting data of player from cache
     * @param uuid player uuid
     * @return player data
     */
    @Nullable
    IPlayerData getPlayerDataFromCache(@NotNull UUID uuid);

    /**
     * Players data cache
     * @return the cache
     */
    Cache<UUID, IPlayerData> getPlayerDataCache();

    /**
     * Reinit storage
     */
    void reload();

    /**
     * Player contains in list
     * @param s player nickname
     * @return true, if player in list
     */
    boolean hasPlayer(String s);

    /**
     * List of player nicknames
     * @return list of player nicknames
     */
    List<String> getList();

    /**
     * Used to optimize tab completions
     * @param args command arguments
     * @return formatted player nickname list
     */
    List<String> getListForComplete(String[] args);
}

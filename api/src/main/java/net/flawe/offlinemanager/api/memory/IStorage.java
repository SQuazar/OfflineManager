package net.flawe.offlinemanager.api.memory;

import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

/**
 * Used for storing the nodes of players nicknames
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
     * @param s Player nickname
     */
    void add(String s);

    /**
     * Remove player nickname from list in storage
     *
     * @param s Player nickname
     */
    void remove(String s);

    void addPlayerDataToCache(@NotNull IPlayerData playerData);

    @Nullable
    IPlayerData getPlayerDataFromCache(UUID uuid);

    /**
     * Reinit storage
     */
    void reload();

    /**
     * Player contains in list
     * @param s Player nickname
     * @return True, if player in list
     */
    boolean hasPlayer(String s);

    /**
     * List of player nicknames
     * @return List of player nicknames
     */
    List<String> getList();

    /**
     * Used to optimize tab completions
     * @param args Command arguments
     * @return Formatted player nickname list
     */
    List<String> getListForComplete(String[] args);
}

/*
 * Copyright (c) 2021 squazar
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.quazar.offlinemanager.api.memory;

import com.google.common.annotations.Beta;
import com.google.common.cache.Cache;
import net.quazar.offlinemanager.api.data.entity.IPlayerData;
import net.quazar.offlinemanager.api.data.entity.PlayerProfile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

/**
 * The player storage class
 *
 * @author quazar
 */
public interface IStorage {
    /**
     * Add all current players profiles from playerdata folder to collection
     */
    void init();

    @Beta
    void initAsync();

    /**
     * Add new player profile to collection
     *
     * @param profile player profile
     */
    void add(PlayerProfile profile);

    /**
     * Remove player profile from collection
     *
     * @param profile player profile
     */
    void remove(PlayerProfile profile);

    /**
     * Gets player profile by name from collection
     * @param name player name
     * @return player profile
     */
    PlayerProfile getPlayerProfile(String name);

    /**
     * Gets player profile by UUID from collection
     * @param uuid player uuid
     * @return player profile
     */
    PlayerProfile getPlayerProfile(UUID uuid);

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
     * Checks player profile contains in collection
     * @param profile player profile
     * @return true, if player profile contains in collection
     */
    boolean hasPlayer(PlayerProfile profile);

    /**
     * Checks player profile contains in collection
     * @param name player name
     * @return true, if player profile contains in collection
     */
    boolean hasPlayer(String name);

    /**
     * Gets list of player nicknames
     * @return list of player nicknames
     */
    List<String> getList();

    /**
     * Gets list for tab complete
     * Used to optimize tab completions
     * @param nickname player nickname
     * @return formatted player nickname list
     */
    List<String> getListForComplete(String nickname);
}

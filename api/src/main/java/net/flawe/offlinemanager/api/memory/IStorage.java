/*
 * Copyright (c) 2021 flaweoff
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

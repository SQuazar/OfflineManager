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

package net.flawe.offlinemanager.data.memory;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import net.flawe.offlinemanager.OfflineManager;
import net.flawe.offlinemanager.api.configuration.CacheConfiguration;
import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.memory.IStorage;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Storage implements IStorage {

    private final List<String> players = new ArrayList<>();

    private final OfflineManager plugin;

    public Storage(OfflineManager plugin) {
        this.plugin = plugin;
        initCache();
    }

    private Cache<UUID, IPlayerData> playerDataCache;

    private void initCache() {
        if (playerDataCache != null) playerDataCache.asMap().clear();
        CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();
        CacheConfiguration cacheConfig = plugin.getSettings().getCacheConfiguration();
        if (cacheConfig.getSize() != 0) cacheBuilder.maximumSize(cacheConfig.getSize());
        this.playerDataCache = cacheBuilder.removalListener(notification -> {
            IPlayerData data = (IPlayerData) notification.getValue();
            data.save();
            if (plugin.getSettings().removeFromCacheNotify())
                plugin.getLogger().info(data.getName() + "(" + data.getUUID() + ") is successfully removed from cache and saved!");
        }).build();
    }

    @Override
    public void init() {
        OfflineManager.getApi().getNMSManager().getSeenPlayers().forEach(id -> {
            try {
                if (id.endsWith("_old"))
                    return;
                UUID uuid = UUID.fromString(id);
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
                if (offlinePlayer.hasPlayedBefore() && !offlinePlayer.isOnline()) {
                    if (offlinePlayer.getName() != null)
                        players.add(offlinePlayer.getName());
                }
            } catch (IllegalArgumentException exception) {
                plugin.err(id + " file is broken. Replace or remove it to resolve this error.");
            }
        });
        plugin.info("Players loaded successfully!");
    }

    @Override
    public void add(String s) {
        players.add(s);
    }

    @Override
    public void remove(String s) {
        players.remove(s);
    }

    @Override
    public void addPlayerDataToCache(@NotNull IPlayerData playerData) {
        this.playerDataCache.put(playerData.getUUID(), playerData);
    }

    @Override
    public void removePlayerDataFromCache(@NotNull UUID uuid) {
        this.playerDataCache.invalidate(uuid);
    }

    @Override
    public @Nullable IPlayerData getPlayerDataFromCache(@NotNull UUID uuid) {
        return playerDataCache.getIfPresent(uuid);
    }

    @Override
    public Cache<UUID, IPlayerData> getPlayerDataCache() {
        return playerDataCache;
    }

    @Override
    public void reload() {
        players.clear();
        init();
        initCache();
    }

    @Override
    public boolean hasPlayer(String s) {
        return players.contains(s);
    }

    @Override
    public List<String> getList() {
        return players;
    }

    @Override
    public List<String> getListForComplete(String[] args) {
        List<String> list = players;
        if (args[1].isEmpty())
            return list.subList(0, Math.min(list.size(), 50));
        list = list.parallelStream().filter(s -> s != null && s.toLowerCase().startsWith(args[1].toLowerCase())).collect(Collectors.toList());
        List<String> nList = new ArrayList<>();
        int size = 0;
        for (String s : list) {
            size += s.getBytes().length;
            if (size >= 2097152 - 116507)
                break;
            nList.add(s);
        }
        return nList;
    }
}

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

package net.quazar.offlinemanager.data.memory;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import net.quazar.offlinemanager.OfflineManager;
import net.quazar.offlinemanager.api.configuration.CacheConfiguration;
import net.quazar.offlinemanager.api.data.entity.IPlayerData;
import net.quazar.offlinemanager.api.data.entity.PlayerProfile;
import net.quazar.offlinemanager.api.memory.IStorage;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public class Storage implements IStorage {

    private final Set<PlayerProfile> players = new HashSet<>();

    private final OfflineManager plugin;
    private boolean locked = false;

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
                plugin.getLogger().info(String.format("%s (%s) is successfully removed from cache and saved!",
                        data.getPlayerProfile().getName(), data.getPlayerProfile().getUuid()));
        }).build();
    }

    @Override
    public void init() {
        if (locked) {
            plugin.getLogger().info("Cannot load players, because loading thread is locket at this time.");
            return;
        }
        locked = true;
        OfflineManager.getApi().getNMSManager().getSeenPlayers().forEach(id -> {
            try {
                if (id.endsWith("_old"))
                    return;
                UUID uuid = UUID.fromString(id);
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
                if (offlinePlayer.hasPlayedBefore() && !offlinePlayer.isOnline()) {
                    if (offlinePlayer.getName() != null)
                        players.add(PlayerProfile.of(uuid, offlinePlayer.getName()));
                }
            } catch (IllegalArgumentException exception) {
                if (plugin.getSettings().isFileErrorLog())
                    plugin.err(id + " file is broken. Replace or remove it to resolve this error.");
            }
        });
        locked = false;
        plugin.info("Players loaded successfully!");
    }

    @Override
    public void initAsync() {
        if (locked) {
            plugin.getLogger().info("Cannot load players, because loading thread is locket at this time.");
            return;
        }
        plugin.getLogger().info("Loading player profiles...");
        locked = true;
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                    OfflineManager.getApi().getNMSManager().getSeenPlayers().forEach(id -> {
                        try {
                            if (id.endsWith("_old"))
                                return;
                            UUID uuid = UUID.fromString(id);
                            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
                            if (offlinePlayer.hasPlayedBefore() && !offlinePlayer.isOnline()) {
                                if (offlinePlayer.getName() != null)
                                    players.add(PlayerProfile.of(uuid, offlinePlayer.getName()));
                            }
                        } catch (IllegalArgumentException exception) {
                            if (plugin.getSettings().isFileErrorLog())
                                plugin.err(id + " file is broken. Replace or remove it to resolve this error.");
                        }
                    });
                    locked = false;
                    players.addAll(addQueue);
                    plugin.info("Players loaded successfully!");
                }
        );
    }

    private final Set<PlayerProfile> addQueue = new HashSet<>();

    @Override
    public void add(PlayerProfile profile) {
        if (locked) {
            addQueue.add(profile);
            return;
        }
        players.add(profile);
    }

    @Override
    public void remove(PlayerProfile profile) {
        if (locked) {
            addQueue.remove(profile);
            return;
        }
        players.remove(profile);
    }

    @Override
    public PlayerProfile getPlayerProfile(String name) {
        if (locked) return null;
        for (PlayerProfile profile : players)
            if (profile.getName().equalsIgnoreCase(name))
                return profile;
        return null;
    }

    @Override
    public PlayerProfile getPlayerProfile(UUID uuid) {
        if (locked) return null;
        for (PlayerProfile profile : players)
            if (profile.getUuid().equals(uuid))
                return profile;
        return null;
    }

    @Override
    public void addPlayerDataToCache(@NotNull IPlayerData playerData) {
        this.playerDataCache.put(playerData.getPlayerProfile().getUuid(), playerData);
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
        if (!locked) {
            players.clear();
            initAsync();
        }
        initCache();
    }

    @Override
    public boolean hasPlayer(PlayerProfile profile) {
        if (locked) return false;
        return players.contains(profile);
    }

    @Override
    public boolean hasPlayer(String name) {
        if (locked) return false;
        return players.stream().anyMatch(profile -> profile.getName().equalsIgnoreCase(name));
    }

    @Override
    public List<String> getList() {
        if (!locked) return Collections.emptyList();
        return players.stream().map(PlayerProfile::getName).collect(Collectors.toList());
    }

    @Override
    public List<String> getListForComplete(String nickname) {
        if (!plugin.getSettings().isPlayerComplete()) return Collections.emptyList();
        if (locked) return Collections.emptyList();

        if (nickname.isEmpty())
            return players
                    .stream()
                    .limit(Math.min(players.size(), 50))
                    .map(PlayerProfile::getName)
                    .collect(Collectors.toList());

        return players
                .stream()
                .map(PlayerProfile::getName)
                .filter(s -> s.toLowerCase().startsWith(nickname.toLowerCase()))
                .limit(100)
                .collect(Collectors.toList());
    }
}

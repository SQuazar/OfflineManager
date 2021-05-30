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
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Storage implements IStorage {

    private final List<String> players = new ArrayList<>();
    private final Cache<UUID, IPlayerData> playerDataCache;

    private final OfflineManager plugin;

    public Storage(OfflineManager plugin) {
        this.plugin = plugin;
        CacheConfiguration cacheConfig = plugin.getSettings().getCacheConfiguration();
        this.playerDataCache = CacheBuilder.newBuilder()
                .maximumSize(cacheConfig.getSize() == 0 ? Long.MAX_VALUE : cacheConfig.getSize())
                .expireAfterWrite(cacheConfig.getLifeTime() == 0 ? Long.MAX_VALUE : cacheConfig.getLifeTime(), TimeUnit.MINUTES)
                .build();
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
                    players.add(offlinePlayer.getName());
                }
            } catch (IllegalArgumentException exception) {
                plugin.err(id + " file is broken. Replace or remove it to resolve this error.");
            }
        });
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
        System.out.println("Added new player data to cache. Now size is " + playerDataCache.size());
    }

    @Override
    public @Nullable IPlayerData getPlayerDataFromCache(UUID uuid) {
        return playerDataCache.getIfPresent(uuid);
    }

    @Override
    public void reload() {
        players.clear();
        init();
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
        list = list.parallelStream().filter(s -> s.toLowerCase().startsWith(args[1].toLowerCase())).collect(Collectors.toList());
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

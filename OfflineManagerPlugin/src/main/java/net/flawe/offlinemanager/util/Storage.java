package net.flawe.offlinemanager.util;

import net.flawe.offlinemanager.OfflineManager;
import net.flawe.offlinemanager.api.IStorage;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.*;
import java.util.stream.Collectors;

public class Storage implements IStorage {

    private static final List<String> players = new ArrayList<>();

    private final OfflineManager plugin;

    public Storage(OfflineManager plugin) {
        this.plugin = plugin;
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
        List<String> nlist = new ArrayList<>();
        int size = 0;
        for (String s : list) {
            size += s.getBytes().length;
            if (size >= 2097152 - 116507)
                break;
            nlist.add(s);
        }
        return nlist;
    }
}

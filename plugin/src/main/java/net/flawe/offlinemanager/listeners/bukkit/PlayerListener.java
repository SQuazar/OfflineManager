package net.flawe.offlinemanager.listeners.bukkit;

import net.flawe.offlinemanager.OfflineManager;
import net.flawe.offlinemanager.api.OfflineManagerAPI;
import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.memory.ISession;
import net.flawe.offlinemanager.api.memory.IStorage;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class PlayerListener implements Listener {

    private final IStorage storage = OfflineManager.getApi().getStorage();
    private final ISession session = OfflineManager.getApi().getSession();
    private final OfflineManagerAPI api = OfflineManager.getApi();

    @EventHandler
    public void onPreJoin(AsyncPlayerPreLoginEvent e) {
        IPlayerData data = storage.getPlayerDataFromCache(e.getUniqueId());
        if (data != null)
            Bukkit.getScheduler().runTask((Plugin) api, (Runnable) data::save);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (storage.hasPlayer(e.getPlayer().getName()))
            storage.remove(e.getPlayer().getName());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if (!storage.hasPlayer(e.getPlayer().getName()))
            storage.add(e.getPlayer().getName());
        UUID player = e.getPlayer().getUniqueId();
        IPlayerData data = storage.getPlayerDataFromCache(player);
        if (data != null)
            data.setOnline(false);
    }

}

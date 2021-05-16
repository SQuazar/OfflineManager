package net.flawe.offlinemanager.listeners.bukkit;

import net.flawe.offlinemanager.OfflineManager;
import net.flawe.offlinemanager.api.memory.ISession;
import net.flawe.offlinemanager.api.memory.IStorage;
import net.flawe.offlinemanager.api.enums.ActiveType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerListener implements Listener {

    private final IStorage storage = OfflineManager.getApi().getStorage();
    private final ISession session = OfflineManager.getApi().getSession();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (storage.hasPlayer(e.getPlayer().getName()))
            storage.remove(e.getPlayer().getName());
        if (session.containsValue(e.getPlayer().getUniqueId(), ActiveType.ALL)) {
            UUID uuid = session.getKeyByValue(e.getPlayer().getUniqueId(), ActiveType.ALL);
            if (uuid == null)
                return;
            Player player = Bukkit.getPlayer(uuid);
            if (player != null)
                player.closeInventory();
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if (!storage.hasPlayer(e.getPlayer().getName()))
            storage.add(e.getPlayer().getName());
        if (session.containsKey(e.getPlayer().getUniqueId(), ActiveType.ALL))
            session.removeByKey(e.getPlayer().getUniqueId(), ActiveType.ALL);
    }

}

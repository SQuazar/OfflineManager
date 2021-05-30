package net.flawe.offlinemanager.listeners.bukkit;

import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.events.inventory.CloseOfflineInventoryEvent;
import net.flawe.offlinemanager.api.events.inventory.OfflineInventoryClickEvent;
import net.flawe.offlinemanager.api.events.inventory.OfflineInventoryInteractEvent;
import net.flawe.offlinemanager.api.inventory.holder.IOfflineInvHolder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;

public class InventoryListener implements Listener {

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (e.getInventory().getHolder() == null)
            return;
        if (e.getInventory().getHolder() instanceof IOfflineInvHolder) {
            IOfflineInvHolder holder = (IOfflineInvHolder) e.getInventory().getHolder();
            IPlayerData playerData = holder.getPlayerData();
            CloseOfflineInventoryEvent event = new CloseOfflineInventoryEvent((Player) e.getPlayer(), playerData, e.getInventory(), holder.getInventoryType());
            Bukkit.getPluginManager().callEvent(event);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getClickedInventory() != null)
            if (e.getClickedInventory().getHolder() != null && e.getClickedInventory().getHolder() instanceof IOfflineInvHolder) {
                IOfflineInvHolder holder = (IOfflineInvHolder) e.getClickedInventory().getHolder();
                IPlayerData playerData = holder.getPlayerData();
                OfflineInventoryClickEvent event = new OfflineInventoryClickEvent((Player) e.getWhoClicked(), playerData,
                        holder.getInventoryType(), e.getInventory(), e.getSlot());
                Bukkit.getPluginManager().callEvent(event);
                if (event.isCancelled()) {
                    e.setCancelled(true);
                    return;
                }
            }
        onInteract(e);
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        onInteract(e);
    }

    private void onInteract(InventoryInteractEvent e) {
        if (e.getInventory().getHolder() == null)
            return;
        if (e.getInventory().getHolder() instanceof IOfflineInvHolder) {
            IOfflineInvHolder holder = (IOfflineInvHolder) e.getInventory().getHolder();
            OfflineInventoryInteractEvent event = new OfflineInventoryInteractEvent((Player) e.getWhoClicked(), holder.getPlayerData(),
                    holder.getInventoryType(), e.getInventory());
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled())
                e.setCancelled(true);
        }
    }

}

package net.flawe.offlinemanager.listeners.bukkit;

import net.flawe.offlinemanager.api.IOfflineInvHolder;
import net.flawe.offlinemanager.api.IUser;
import net.flawe.offlinemanager.api.enums.InventoryType;
import net.flawe.offlinemanager.events.CloseOfflineInventoryEvent;
import net.flawe.offlinemanager.events.OfflineInventoryClickEvent;
import net.flawe.offlinemanager.events.OfflineInventoryInteractEvent;
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
            IUser user = holder.getUser();
            CloseOfflineInventoryEvent event = new CloseOfflineInventoryEvent((Player) e.getPlayer(), user, e.getInventory(), holder.getInventoryType());
            Bukkit.getPluginManager().callEvent(event);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getInventory().getHolder() != null && e.getInventory().getHolder() instanceof IOfflineInvHolder) {
            IOfflineInvHolder holder = (IOfflineInvHolder) e.getInventory().getHolder();
            IUser user = holder.getUser();
            OfflineInventoryClickEvent event = new OfflineInventoryClickEvent((Player) e.getWhoClicked(), user,
                    holder.getInventoryType(), e.getInventory(), e.getSlot());
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                e.setCancelled(true);
                return;
            }
            onInteract(e);
        }
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
            OfflineInventoryInteractEvent event = new OfflineInventoryInteractEvent((Player) e.getWhoClicked(), holder.getUser(),
                    holder.getInventoryType(), e.getInventory());
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled())
                e.setCancelled(true);
/*            if (holder instanceof OfflineInventoryHolder) {
                if (!configManager.getInventoryConfig().interact()) {
                    e.setCancelled(true);
                }
            }*/
        }
    }

}

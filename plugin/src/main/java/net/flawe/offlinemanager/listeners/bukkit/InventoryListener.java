package net.flawe.offlinemanager.listeners.bukkit;

import net.flawe.offlinemanager.OfflineManager;
import net.flawe.offlinemanager.api.OfflineManagerAPI;
import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.enums.ActiveType;
import net.flawe.offlinemanager.api.event.inventory.CloseOfflineInventoryEvent;
import net.flawe.offlinemanager.api.event.inventory.OfflineInventoryClickEvent;
import net.flawe.offlinemanager.api.event.inventory.OfflineInventoryInteractEvent;
import net.flawe.offlinemanager.api.inventory.holder.IOfflineInvHolder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

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

    private final OfflineManagerAPI api = OfflineManager.getApi();

    private void onInteract(InventoryInteractEvent e) {
        Player player = (Player) e.getWhoClicked();
        Player viewer;
        if (api.getSession().containsValue(player.getUniqueId(), ActiveType.INVENTORY)) {
            viewer = Bukkit.getPlayer(api.getSession().getKeyByValue(player.getUniqueId(), ActiveType.INVENTORY));
            if (viewer != null) {
                Inventory inventory = viewer.getOpenInventory().getTopInventory();
                Bukkit.getScheduler().runTask((Plugin) api, () -> {
                    inventory.setContents(player.getInventory().getStorageContents());
                });
            }
        }
        if (api.getSession().containsValue(player.getUniqueId(), ActiveType.ENDER_CHEST)) {
            viewer = Bukkit.getPlayer(api.getSession().getKeyByValue(player.getUniqueId(), ActiveType.ENDER_CHEST));
            if (viewer != null) {
                Inventory inventory = viewer.getOpenInventory().getTopInventory();
                Bukkit.getScheduler().runTask((Plugin) api, () -> {
                   inventory.setContents(player.getEnderChest().getContents());
                });
            }
        }
        if (api.getSession().containsValue(player.getUniqueId(), ActiveType.ARMOR_INVENTORY)) {
            viewer = Bukkit.getPlayer(api.getSession().getKeyByValue(player.getUniqueId(), ActiveType.ARMOR_INVENTORY));
            if (viewer != null) {
                Inventory inventory = viewer.getOpenInventory().getTopInventory();
                Bukkit.getScheduler().runTask((Plugin) api, () -> {
                    for (int i = 0; i < 4; i++)
                        inventory.setItem(i, player.getInventory().getArmorContents()[i]);
                    inventory.setItem(4, player.getInventory().getItemInOffHand());
                });
            }
        }
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

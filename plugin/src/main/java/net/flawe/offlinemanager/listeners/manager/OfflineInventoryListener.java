package net.flawe.offlinemanager.listeners.manager;

import net.flawe.offlinemanager.OfflineManager;
import net.flawe.offlinemanager.api.OfflineManagerAPI;
import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.enums.ActiveType;
import net.flawe.offlinemanager.api.enums.InventoryType;
import net.flawe.offlinemanager.api.enums.SavePlayerType;
import net.flawe.offlinemanager.api.event.inventory.CloseOfflineInventoryEvent;
import net.flawe.offlinemanager.api.event.inventory.OfflineInventoryClickEvent;
import net.flawe.offlinemanager.api.event.inventory.OfflineInventoryInteractEvent;
import net.flawe.offlinemanager.api.event.inventory.OpenOfflineInventoryEvent;
import net.flawe.offlinemanager.configuration.Settings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class OfflineInventoryListener implements Listener {

    private final OfflineManagerAPI api = OfflineManager.getApi();
    private final Settings settings = ((OfflineManager) api).getSettings();

    @EventHandler
    public void onClose(CloseOfflineInventoryEvent e) {
        IPlayerData playerData = e.getPlayerData();
        switch (e.getInventoryType()) {
            case DEFAULT: {
                if (!settings.getInventoryConfiguration().enabled()) break;
                if (!settings.getInventoryConfiguration().interact()) {
                    if (api.getSession().containsKey(e.getPlayer().getUniqueId(), ActiveType.INVENTORY))
                        api.getSession().removeByKey(e.getPlayer().getUniqueId(), ActiveType.INVENTORY);
                    break;
                }
                ItemStack stack;
                for (int i = 0; i < 36; i++) {
                    stack = e.getInventory().getItem(i);
                    playerData.getInventory().setItem(i, stack);
                }
                playerData.save(SavePlayerType.INVENTORY);
                if (api.getSession().containsKey(e.getPlayer().getUniqueId(), ActiveType.INVENTORY))
                    api.getSession().removeByKey(e.getPlayer().getUniqueId(), ActiveType.INVENTORY);
            }
            break;
            case ARMOR: {
                if (!settings.getArmorInventoryConfiguration().enabled()) break;
                if (!settings.getArmorInventoryConfiguration().interact()) {
                    if (api.getSession().containsKey(e.getPlayer().getUniqueId(), ActiveType.ARMOR_INVENTORY))
                        api.getSession().removeByKey(e.getPlayer().getUniqueId(), ActiveType.ARMOR_INVENTORY);
                    break;
                }
                Inventory inv = e.getInventory();
                playerData.getInventory().setArmorContents(new ItemStack[]{inv.getItem(0), inv.getItem(1), inv.getItem(2), inv.getItem(3)});
                playerData.getInventory().setItemInOffHand(inv.getItem(4));
                playerData.save(SavePlayerType.ARMOR_INVENTORY);
                if (api.getSession().containsKey(e.getPlayer().getUniqueId(), ActiveType.ARMOR_INVENTORY))
                    api.getSession().removeByKey(e.getPlayer().getUniqueId(), ActiveType.ARMOR_INVENTORY);
            }
            break;
            case ENDER_CHEST: {
                if (!settings.getEnderChestConfiguration().enabled()) break;
                if (!settings.getEnderChestConfiguration().interact()) {
                    if (api.getSession().containsKey(e.getPlayer().getUniqueId(), ActiveType.ENDER_CHEST))
                        api.getSession().removeByKey(e.getPlayer().getUniqueId(), ActiveType.ENDER_CHEST);
                    break;
                }
                e.getPlayerData().getEnderChest().getEnderChest().setContents(e.getInventory().getContents());
                e.getPlayerData().save(SavePlayerType.ENDER_CHEST);
                if (api.getSession().containsKey(e.getPlayer().getUniqueId(), ActiveType.ENDER_CHEST))
                    api.getSession().removeByKey(e.getPlayer().getUniqueId(), ActiveType.ENDER_CHEST);
            }
            break;
        }
        if (Bukkit.getOfflinePlayer(playerData.getUUID()).isOnline()) {
            Player target = Bukkit.getPlayer(playerData.getUUID());
            if (target != null)
                target.getInventory().setContents(playerData.getInventory().getContents());
        }
    }

    @EventHandler
    public void onOpen(OpenOfflineInventoryEvent e) {

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClick(OfflineInventoryClickEvent e) {
        if (e.getInventoryType() == InventoryType.ARMOR) {
            if (e.getSlot() > 4)
                e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(OfflineInventoryInteractEvent e) {
        switch (e.getInventoryType()) {
            case DEFAULT:
                e.setCancelled(!settings.getInventoryConfiguration().interact());
                break;
            case ARMOR:
                e.setCancelled(!settings.getArmorInventoryConfiguration().interact());
                break;
            case ENDER_CHEST:
                e.setCancelled(!settings.getEnderChestConfiguration().interact());
                break;
        }
    }

}

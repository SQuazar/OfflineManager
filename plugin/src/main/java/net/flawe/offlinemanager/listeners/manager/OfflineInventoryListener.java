package net.flawe.offlinemanager.listeners.manager;

import net.flawe.offlinemanager.OfflineManager;
import net.flawe.offlinemanager.api.OfflineManagerAPI;
import net.flawe.offlinemanager.api.IUser;
import net.flawe.offlinemanager.api.enums.ActiveType;
import net.flawe.offlinemanager.api.enums.InventoryType;
import net.flawe.offlinemanager.api.enums.SavePlayerType;
import net.flawe.offlinemanager.configuration.Settings;
import net.flawe.offlinemanager.api.events.inventory.CloseOfflineInventoryEvent;
import net.flawe.offlinemanager.api.events.inventory.OfflineInventoryClickEvent;
import net.flawe.offlinemanager.api.events.inventory.OfflineInventoryInteractEvent;
import net.flawe.offlinemanager.api.events.inventory.OpenOfflineInventoryEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class OfflineInventoryListener implements Listener {

    private final OfflineManagerAPI api = OfflineManager.getApi();
    private final Settings settings = ((OfflineManager) api).getSettings();

    @EventHandler
    public void onClose(CloseOfflineInventoryEvent e) {
        IUser user = e.getUser();
        switch (e.getInventoryType()) {
            case DEFAULT: {
                if (!settings.getInventoryConfiguration().enabled()) break;
                if (!settings.getInventoryConfiguration().interact()) break;
                for (int i = 0; i < 36; i++) {
                    user.getPlayer().getInventory().setItem(i, e.getInventory().getItem(i));
                }
                user.save(SavePlayerType.INVENTORY);
                if (api.getSession().containsKey(e.getPlayer().getUniqueId(), ActiveType.INVENTORY))
                    api.getSession().removeByKey(e.getPlayer().getUniqueId(), ActiveType.INVENTORY);
            }
            break;
            case ARMOR: {
                if (!settings.getArmorInventoryConfiguration().enabled()) break;
                if (!settings.getArmorInventoryConfiguration().interact()) break;
                Inventory inv = e.getInventory();
                user.getPlayer().getInventory().setArmorContents(new ItemStack[]{inv.getItem(3), inv.getItem(2), inv.getItem(1), inv.getItem(0)});
                e.getUser().getPlayer().getInventory().setItemInOffHand(inv.getItem(4));
                user.save(SavePlayerType.ARMOR_INVENTORY);
                if (api.getSession().containsKey(e.getPlayer().getUniqueId(), ActiveType.ARMOR_INVENTORY))
                    api.getSession().removeByKey(e.getPlayer().getUniqueId(), ActiveType.ARMOR_INVENTORY);
            }
            break;
            case ENDER_CHEST: {
                if (!settings.getEnderChestConfiguration().enabled()) break;
                if (!settings.getEnderChestConfiguration().interact()) break;
                user.getPlayer().getEnderChest().setContents(e.getInventory().getContents());
                user.save(SavePlayerType.ENDER_CHEST);
                if (api.getSession().containsKey(e.getPlayer().getUniqueId(), ActiveType.ENDER_CHEST))
                    api.getSession().removeByKey(e.getPlayer().getUniqueId(), ActiveType.ENDER_CHEST);
            }
            break;
        }
        if (user.getPlayer().isOnline()) {
            Player target = Bukkit.getPlayer(user.getUUID());
            if (target != null)
                target.getInventory().setContents(user.getInventory().getInventory().getContents());
        }
    }

    @EventHandler
    public void onOpen(OpenOfflineInventoryEvent e) {

    }

    @EventHandler
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

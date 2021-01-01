package net.flawe.offlinemanager.listeners.manager;

import net.flawe.offlinemanager.OfflineManager;
import net.flawe.offlinemanager.OfflineManagerAPI;
import net.flawe.offlinemanager.api.IUser;
import net.flawe.offlinemanager.api.enums.ActiveType;
import net.flawe.offlinemanager.api.enums.InventoryType;
import net.flawe.offlinemanager.api.enums.SavePlayerType;
import net.flawe.offlinemanager.events.CloseOfflineInventoryEvent;
import net.flawe.offlinemanager.events.OfflineInventoryClickEvent;
import net.flawe.offlinemanager.events.OfflineInventoryInteractEvent;
import net.flawe.offlinemanager.events.OpenOfflineInventoryEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class OfflineInventoryListener implements Listener {

    private final OfflineManagerAPI api = OfflineManager.getApi();

    @EventHandler
    public void onClose(CloseOfflineInventoryEvent e) {
        IUser user = e.getUser();
        if (e.getInventoryType() == InventoryType.DEFAULT) {
            if (!api.getConfigManager().getInventoryConfig().enabled())
                return;
            if (!api.getConfigManager().getInventoryConfig().interact())
                return;
            for (int i = 0; i < 36; i++) {
                user.getPlayer().getInventory().setItem(i, e.getInventory().getItem(i));
            }
            user.save(SavePlayerType.INVENTORY);
            if (api.getSession().containsKey(e.getPlayer().getUniqueId(), ActiveType.INVENTORY))
                api.getSession().removeByKey(e.getPlayer().getUniqueId(), ActiveType.INVENTORY);
            return;
        }
        if (e.getInventoryType() == InventoryType.ARMOR) {
            if (!api.getConfigManager().getArmorInventoryConfig().enabled())
                return;
            if (!api.getConfigManager().getArmorInventoryConfig().interact())
                return;
            Inventory inv = e.getInventory();
            user.getPlayer().getInventory().setArmorContents(new ItemStack[]{inv.getItem(3), inv.getItem(2), inv.getItem(1), inv.getItem(0)});
            e.getUser().getPlayer().getInventory().setItemInOffHand(inv.getItem(4));
            user.save(SavePlayerType.ARMOR_INVENTORY);
            if (api.getSession().containsKey(e.getPlayer().getUniqueId(), ActiveType.ARMOR_INVENTORY))
                api.getSession().removeByKey(e.getPlayer().getUniqueId(), ActiveType.ARMOR_INVENTORY);
            return;
        }
        if (e.getInventoryType() == InventoryType.ENDER_CHEST) {
            if (!api.getConfigManager().getEnderChestConfig().enabled())
                return;
            if (!api.getConfigManager().getEnderChestConfig().interact())
                return;
            user.getPlayer().getEnderChest().setContents(e.getInventory().getContents());
            user.save(SavePlayerType.ENDER_CHEST);
            if (api.getSession().containsKey(e.getPlayer().getUniqueId(), ActiveType.ENDER_CHEST))
                api.getSession().removeByKey(e.getPlayer().getUniqueId(), ActiveType.ENDER_CHEST);
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
                e.setCancelled(!api.getConfigManager().getInventoryConfig().interact());
                break;
            case ARMOR:

                e.setCancelled(!api.getConfigManager().getArmorInventoryConfig().interact());
                break;
            case ENDER_CHEST:
                e.setCancelled(!api.getConfigManager().getEnderChestConfig().interact());
                break;
        }
    }

}

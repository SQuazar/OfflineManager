package net.flawe.offlinemanager.api.inventory;

import org.bukkit.inventory.Inventory;

/**
 * Offline inventory interface
 *
 * @author flawe
 */
public interface IInventory {
    /**
     * Gets the offline player inventory
     * @return inventory
     */
    Inventory getInventory();
}

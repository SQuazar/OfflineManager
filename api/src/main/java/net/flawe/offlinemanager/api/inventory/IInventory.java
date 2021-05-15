package net.flawe.offlinemanager.api.inventory;

import org.bukkit.inventory.Inventory;

/**
 * Offline inventory interface
 *
 * @author flawe
 */
public interface IInventory {
    /**
     * Inventory with parameters from configuration
     *
     * @return Inventory with parameters from configuration
     */
    Inventory getInventory();
}

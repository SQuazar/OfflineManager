package net.flawe.offlinemanager.api.inventory;

import org.bukkit.inventory.Inventory;

/**
 * The interface armor inventory.
 *
 * @author flawe
 */
public interface IArmorInventory {
    /**
     * Inventory with parameters from configuration
     *
     * @return Armor inventory
     */
    Inventory getInventory();
}

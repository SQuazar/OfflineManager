package net.flawe.offlinemanager.api.inventory;

import org.bukkit.inventory.Inventory;

/**
 * The interface armor inventory.
 *
 * @author flawe
 */
public interface IArmorInventory {
    /**
     * Gets the offline player armor inventory
     * @return armor inventory
     */
    Inventory getInventory();
}

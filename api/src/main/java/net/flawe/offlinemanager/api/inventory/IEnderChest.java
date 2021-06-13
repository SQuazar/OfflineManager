package net.flawe.offlinemanager.api.inventory;

import org.bukkit.inventory.Inventory;

/**
 * The ender chest interface
 *
 * @author flawe
 */
public interface IEnderChest {
    /**
     * Gets the offline player ender chest
     * @return ender chest inventory
     */
    Inventory getEnderChest();
}

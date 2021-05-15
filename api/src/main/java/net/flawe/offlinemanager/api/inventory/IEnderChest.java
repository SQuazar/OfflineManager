package net.flawe.offlinemanager.api.inventory;

import org.bukkit.inventory.Inventory;

/**
 * The ender chest interface
 *
 * @author flawe
 */
public interface IEnderChest {
    /**
     * Inventory with parameters from configuration
     *
     * @return Ender chest inventory with parameters from configuration
     */
    Inventory getEnderChest();
}

package net.flawe.offlinemanager.api.inventory.holder;

import net.flawe.offlinemanager.api.IUser;
import net.flawe.offlinemanager.api.enums.InventoryType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.Nullable;

/**
 * Inventory holder for offline containers
 *
 * @author flawe
 */
public interface IOfflineInvHolder extends InventoryHolder {
    /**
     * User player who owns this inventory
     * @return User player
     */
    Player getPlayer();

    /**
     * Inventory owner
     * @return Offline player who owns this inventory
     */
    IUser getUser();

    /**
     * Inventory name from configuration
     * @return Inventory name
     */
    String getInventoryName();

    /**
     * Current inventory type
     * @see InventoryType
     * @return Type of this inventory
     */
    InventoryType getInventoryType();

    /**
     * Player whose seen offline inventory
     * @return Player whose seen offline inventory
     */
    @Nullable
    Player getWhoSeen();

    /**
     * Inventory view status by player
     * @return True if this inventory viewed by player
     */
    boolean isViewed();
}

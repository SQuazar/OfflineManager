package net.flawe.offlinemanager.api.inventory.holder;

import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.entity.IUser;
import net.flawe.offlinemanager.api.enums.InventoryType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Inventory holder for offline containers
 *
 * @author flawe
 */
public interface IOfflineInvHolder extends InventoryHolder {
    /**
     * Inventory owner but player instance from user equal getUser().getPlayer()
     * @deprecated use getPlayerData
     * @return player instance from user
     */
    @Deprecated
    @NotNull
    Player getPlayer();

    /**
     * Inventory owner
     * @deprecated use getPlayerData
     * @return offline player who owns this inventory
     */
    @Deprecated
    @NotNull
    IUser getUser();

    /**
     * Inventory owner
     * @return data of inventory owner
     */
    @NotNull
    IPlayerData getPlayerData();

    /**
     * Inventory name from configuration
     * @return inventory name
     */
    @NotNull
    String getInventoryName();

    /**
     * Current inventory type
     * @see InventoryType
     * @return type of this inventory
     */
    @NotNull
    InventoryType getInventoryType();

    /**
     * Player whose seen offline inventory
     * @return player whose seen offline inventory
     */
    @Nullable
    Player getWhoSeen();

    /**
     * Inventory view status by player. Currently not used or working
     * @return true if this inventory viewed by player
     *
     */
    boolean isViewed();
}

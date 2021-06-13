package net.flawe.offlinemanager.api.event.inventory;

import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.entity.IUser;
import net.flawe.offlinemanager.api.enums.InventoryType;
import net.flawe.offlinemanager.api.event.OfflineManagerEvent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a player closes another player's offline inventory
 * @author flawe
 */
public class CloseOfflineInventoryEvent extends OfflineManagerEvent {

    private final IUser user;
    private final IPlayerData playerData;
    private final Player player;
    private final Inventory inventory;
    private final InventoryType inventoryType;

    /**
     * @param player who close offline inventory
     * @param user who's offline inventory was closed
     * @param inventory offline inventory that has been closed
     * @param inventoryType offline inventory type
     */
    @Deprecated
    public CloseOfflineInventoryEvent(@NotNull Player player, @NotNull IUser user, @NotNull Inventory inventory, @NotNull InventoryType inventoryType) {
        this.user = user;
        this.playerData = user.getPlayerData();
        this.player = player;
        this.inventory = inventory;
        this.inventoryType = inventoryType;
    }

    public CloseOfflineInventoryEvent(@NotNull Player player, IPlayerData playerData, @NotNull Inventory inventory, @NotNull InventoryType inventoryType) {
        this.player = player;
        this.playerData = playerData;
        this.user = playerData.getUser();
        this.inventory = inventory;
        this.inventoryType = inventoryType;
    }

    /**
     * Gets the player who close the offline inventory
     * @return player who close the offline inventory
     */
    @NotNull
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the inventory owner
     * @return offline inventory owner
     */
    @Deprecated
    @NotNull
    public IUser getUser() {
        return user;
    }

    /**
     * Gets the inventory owner
     * @return offline inventory owner
     */
    public IPlayerData getPlayerData() {
        return playerData;
    }

    /**
     * Gets the inventory owner of player equal to getUser().getPlayer()
     * @return offline inventory owner by player
     */
    @NotNull
    public Player getTarget() {
        return user.getPlayer();
    }

    /**
     * Gets the closed inventory
     * @return offline inventory that has been closed
     */
    @NotNull
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Gets the inventory type
     * @return offline inventory type
     */
    @NotNull
    public InventoryType getInventoryType() {
        return inventoryType;
    }

}

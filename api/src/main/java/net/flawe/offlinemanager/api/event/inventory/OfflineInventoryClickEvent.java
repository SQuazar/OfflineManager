package net.flawe.offlinemanager.api.event.inventory;

import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.entity.IUser;
import net.flawe.offlinemanager.api.enums.InventoryType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

/**
 * Called when player clicks on slot in offline inventory
 * @author flawe
 */
public class OfflineInventoryClickEvent extends OfflineInventoryInteractEvent {

    private final int slot;

    @Deprecated
    public OfflineInventoryClickEvent(@NotNull Player player, @NotNull IUser target,
                                      @NotNull InventoryType inventoryType, @NotNull Inventory inventory, int slot) {
        super(player, target, inventoryType, inventory);
        this.slot = slot;
    }

    public OfflineInventoryClickEvent(@NotNull Player player, @NotNull IPlayerData playerData,
                                      @NotNull InventoryType inventoryType, @NotNull Inventory inventory, int slot) {
        super(player, playerData, inventoryType, inventory);
        this.slot = slot;
    }

    /**
     * Gets the clicked slot
     * @return clicked slot
     */
    public int getSlot() {
        return slot;
    }

}

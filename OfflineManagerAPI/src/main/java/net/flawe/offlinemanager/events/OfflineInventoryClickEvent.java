package net.flawe.offlinemanager.events;

import net.flawe.offlinemanager.api.IUser;
import net.flawe.offlinemanager.api.enums.InventoryType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class OfflineInventoryClickEvent extends OfflineInventoryInteractEvent {

    private final int slot;

    public OfflineInventoryClickEvent(@NotNull Player player, @NotNull IUser target,
                                      @NotNull InventoryType inventoryType, @NotNull Inventory inventory, int slot) {
        super(player, target, inventoryType, inventory);
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }

}

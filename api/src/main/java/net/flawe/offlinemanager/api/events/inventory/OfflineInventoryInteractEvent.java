package net.flawe.offlinemanager.api.events.inventory;

import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.entity.IUser;
import net.flawe.offlinemanager.api.enums.InventoryType;
import net.flawe.offlinemanager.api.events.OfflineManagerEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class OfflineInventoryInteractEvent extends OfflineManagerEvent implements Cancellable {

    private final Player player;
    private final IUser target;
    private final IPlayerData playerData;
    private final InventoryType inventoryType;
    private final Inventory inventory;
    private boolean cancelled;

    @Deprecated
    public OfflineInventoryInteractEvent(@NotNull Player player, @NotNull IUser target,
                                         @NotNull InventoryType inventoryType, @NotNull Inventory inventory) {
        this.player = player;
        this.target = target;
        this.playerData = target.getPlayerData();
        this.inventoryType = inventoryType;
        this.inventory = inventory;
    }

    public OfflineInventoryInteractEvent(@NotNull Player player, @NotNull IPlayerData playerData,
                                         @NotNull InventoryType inventoryType, @NotNull Inventory inventory) {
        this.player = player;
        this.playerData = playerData;
        this.target = playerData.getUser();
        this.inventoryType = inventoryType;
        this.inventory = inventory;
    }

    @NotNull
    public Player getPlayer() {
        return player;
    }

    @Deprecated
    @NotNull
    public IUser getTarget() {
        return target;
    }

    @NotNull
    public IPlayerData getPlayerData() {
        return playerData;
    }

    @NotNull
    public InventoryType getInventoryType() {
        return inventoryType;
    }

    @NotNull
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }
}

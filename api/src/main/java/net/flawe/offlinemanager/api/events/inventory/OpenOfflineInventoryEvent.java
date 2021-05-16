package net.flawe.offlinemanager.api.events.inventory;

import net.flawe.offlinemanager.api.IUser;
import net.flawe.offlinemanager.api.enums.InventoryType;
import net.flawe.offlinemanager.api.events.OfflineManagerEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OpenOfflineInventoryEvent extends OfflineManagerEvent implements Cancellable {

    private final Player player;
    private final IUser target;
    private Inventory inventory;
    private boolean cancelled;
    private InventoryType inventoryType;

    public OpenOfflineInventoryEvent(Player player, IUser target) {
        this(player, target, InventoryType.DEFAULT);
    }

    public OpenOfflineInventoryEvent(Player player, IUser target, InventoryType type) {
        this(player, target, null, type);
    }

    public OpenOfflineInventoryEvent(Player player, IUser target, Inventory inventory, InventoryType type) {
        this.player = player;
        this.target = target;
        this.inventory = inventory;
        this.inventoryType = type;
    }

    @NotNull
    public Player getPlayer() {
        return player;
    }

    @NotNull
    public IUser getTarget() {
        return target;
    }

    @Nullable
    public Inventory getInventory() {
        return inventory;
    }

    public void setInventoryType(InventoryType inventoryType) {
        this.inventoryType = inventoryType;
    }

    @NotNull
    public InventoryType getInventoryType() {
        return inventoryType;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

}

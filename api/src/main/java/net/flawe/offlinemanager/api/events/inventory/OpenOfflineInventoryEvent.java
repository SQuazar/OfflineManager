package net.flawe.offlinemanager.api.events.inventory;

import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.entity.IUser;
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
    private final IPlayerData playerData;
    private final Inventory inventory;
    private boolean cancelled;
    private final InventoryType inventoryType;

    @Deprecated
    public OpenOfflineInventoryEvent(Player player, IUser target) {
        this(player, target, InventoryType.DEFAULT);
    }

    @Deprecated
    public OpenOfflineInventoryEvent(Player player, IUser target, InventoryType type) {
        this(player, target, null, type);
    }

    public OpenOfflineInventoryEvent(Player player, IPlayerData playerData) {
        this(player, playerData, InventoryType.DEFAULT);
    }

    @Deprecated
    public OpenOfflineInventoryEvent(Player player, IUser target, Inventory inventory, InventoryType type) {
        this.player = player;
        this.target = target;
        this.inventory = inventory;
        this.inventoryType = type;
        this.playerData = target.getPlayerData();
    }

    public OpenOfflineInventoryEvent(Player player, IPlayerData playerData, InventoryType type) {
        this(player, playerData, null, type);
    }

    public OpenOfflineInventoryEvent(Player player, IPlayerData playerData, Inventory inventory, InventoryType type) {
        this.player = player;
        this.playerData = playerData;
        this.inventoryType = type;
        this.inventory = inventory;
        this.target = playerData.getUser();
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

    @Nullable
    public Inventory getInventory() {
        return inventory;
    }

    @NotNull
    public InventoryType getInventoryType() {
        return inventoryType;
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

package net.flawe.offlinemanager.inventory.holders;

import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.entity.IUser;
import net.flawe.offlinemanager.api.enums.InventoryType;
import net.flawe.offlinemanager.api.inventory.holder.IOfflineInvHolder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OfflineInventoryHolder implements IOfflineInvHolder {

    private final IUser user;
    private final Player seen;
    private final String name;
    private final IPlayerData playerData;

    @Deprecated
    public OfflineInventoryHolder(@NotNull IUser user) {
        this(user, null, "Inventory");
    }

    @Deprecated
    public OfflineInventoryHolder(@NotNull IUser user, @Nullable Player seen) {
        this(user, seen, "Inventory");
    }

    @Deprecated
    public OfflineInventoryHolder(@NotNull IUser user, @Nullable Player seen, @NotNull String name) {
        this.user = user;
        this.seen = seen;
        this.name = name;
        this.playerData = user.getPlayerData();
    }

    public OfflineInventoryHolder(@NotNull IPlayerData playerData, @Nullable Player seen, @NotNull String name) {
        this.user = playerData.getUser();
        this.seen = seen;
        this.name = name;
        this.playerData = playerData;
    }

    @Override
    public @NotNull Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 36, name);
        inventory.setContents(playerData.getInventory().getStorageContents());
        return inventory;
    }

    @Override
    public @NotNull Player getPlayer() {
        return user.getPlayer();
    }

    public @NotNull IPlayerData getPlayerData() {
        return playerData;
    }

    @Override
    public @NotNull IUser getUser() {
        return user;
    }

    @Override
    public @NotNull String getInventoryName() {
        return name;
    }

    @Override
    public @NotNull InventoryType getInventoryType() {
        return InventoryType.DEFAULT;
    }

    public @Nullable Player getWhoSeen() {
        return seen;
    }

    @Override
    public boolean isViewed() {
        return seen != null;
    }
}

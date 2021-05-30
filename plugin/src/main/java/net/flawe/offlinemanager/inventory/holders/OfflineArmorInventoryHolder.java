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

public class OfflineArmorInventoryHolder implements IOfflineInvHolder {

    private final IUser user;
    private final Player seen;
    private final String name;
    private final IPlayerData playerData;

    @Deprecated
    public OfflineArmorInventoryHolder(@NotNull IUser user) {
        this(user, null, "ArmorInventory");
    }

    @Deprecated
    public OfflineArmorInventoryHolder(@NotNull IUser user, @Nullable Player seen) {
        this(user, seen, "ArmorInventory");
    }

    @Deprecated
    public OfflineArmorInventoryHolder(@NotNull IUser user, @Nullable Player seen, @NotNull String name) {
        this.user = user;
        this.seen = seen;
        this.name = name;
        this.playerData = user.getPlayerData();
    }

    public OfflineArmorInventoryHolder(@NotNull IPlayerData playerData, @Nullable Player seen, @NotNull String name) {
        this.playerData = playerData;
        this.seen = seen;
        this.name = name;
        this.user = playerData.getUser();
    }

    @Override
    public @NotNull Player getPlayer() {
        return user.getPlayer();
    }

    @Deprecated
    @Override
    public  @NotNull IUser getUser() {
        return user;
    }

    public @NotNull IPlayerData getPlayerData() {
        return playerData;
    }

    @Override
    public @NotNull String getInventoryName() {
        return name;
    }

    @Override
    public @NotNull InventoryType getInventoryType() {
        return InventoryType.ARMOR;
    }

    @Override
    public @Nullable Player getWhoSeen() {
        return seen;
    }

    @Override
    public boolean isViewed() {
        return seen != null;
    }

    @Override
    public @NotNull Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 9, name);
        inventory.setContents(playerData.getArmorInventory().getInventory().getContents());
        return inventory;
    }
}

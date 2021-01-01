package net.flawe.offlinemanager.holders;

import net.flawe.offlinemanager.api.IOfflineInvHolder;
import net.flawe.offlinemanager.api.IUser;
import net.flawe.offlinemanager.api.enums.InventoryType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OfflineEnderChestHolder implements IOfflineInvHolder {

    private final IUser user;
    private final Player seen;
    private final String name;

    public OfflineEnderChestHolder(IUser user) {
        this(user, null, "EnderChest");
    }

    public OfflineEnderChestHolder(IUser user, Player seen) {
        this(user, seen, "EnderChest");
    }

    public OfflineEnderChestHolder(IUser user, Player seen, String name) {
        this.user = user;
        this.seen = seen;
        this.name = name;
    }

    @Override
    public Player getPlayer() {
        return user.getPlayer();
    }

    @Override
    public IUser getUser() {
        return user;
    }

    @Override
    public String getInventoryName() {
        return name;
    }

    @Override
    public InventoryType getInventoryType() {
        return InventoryType.ENDER_CHEST;
    }

    @Override
    public @Nullable Player getWhoSeen() {
        return seen;
    }

    @Override
    public boolean isViewed() {
        return seen != null;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 27, name);
        inventory.setContents(user.getEnderChest().getEnderChest().getContents());
        return inventory;
    }
}

package net.flawe.offlinemanager.holders;

import net.flawe.offlinemanager.api.IOfflineInvHolder;
import net.flawe.offlinemanager.api.IUser;
import net.flawe.offlinemanager.api.enums.InventoryType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OfflineInventoryHolder implements IOfflineInvHolder {

    private final IUser user;
    private final Player seen;
    private final String name;

    public OfflineInventoryHolder(IUser user) {
        this(user, null, "Inventory");
    }

    public OfflineInventoryHolder(IUser user, Player seen) {
        this(user, seen, "Inventory");
    }

    public OfflineInventoryHolder(IUser user, Player seen, String name) {
        this.user = user;
        this.seen = seen;
        this.name = name;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 36, name);
        inventory.setContents(user.getInventory().getInventory().getContents());
        return inventory;
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
        return InventoryType.DEFAULT;
    }

    @Nullable
    public Player getWhoSeen() {
        return seen;
    }

    @Override
    public boolean isViewed() {
        return seen != null;
    }
}

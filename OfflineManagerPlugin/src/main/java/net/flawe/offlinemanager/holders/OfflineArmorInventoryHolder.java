package net.flawe.offlinemanager.holders;

import net.flawe.offlinemanager.api.IOfflineInvHolder;
import net.flawe.offlinemanager.api.IUser;
import net.flawe.offlinemanager.api.enums.InventoryType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OfflineArmorInventoryHolder implements IOfflineInvHolder {

    private final IUser user;
    private final Player seen;
    private final String name;

    public OfflineArmorInventoryHolder(IUser user) {
        this(user, null, "ArmorInventory");
    }

    public OfflineArmorInventoryHolder(IUser user, Player seen) {
        this(user, seen, "ArmorInventory");
    }

    public OfflineArmorInventoryHolder(IUser user, Player seen, String name) {
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

    @NotNull
    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 9, name);
        inventory.setContents(user.getArmorInventory().getInventory().getContents());
        return inventory;
    }
}

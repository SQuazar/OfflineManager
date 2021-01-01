package net.flawe.offlinemanager.api;

import net.flawe.offlinemanager.api.enums.InventoryType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.Nullable;

public interface IOfflineInvHolder extends InventoryHolder {
    Player getPlayer();

    IUser getUser();

    String getInventoryName();

    InventoryType getInventoryType();

    @Nullable
    Player getWhoSeen();

    boolean isViewed();
}

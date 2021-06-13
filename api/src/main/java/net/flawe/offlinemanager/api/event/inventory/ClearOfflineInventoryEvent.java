package net.flawe.offlinemanager.api.event.inventory;

import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.entity.IUser;
import net.flawe.offlinemanager.api.event.OfflineManagerEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;

/**
 * Called when the player clears the offline player's inventory
 * @author flawe
 */
public class ClearOfflineInventoryEvent extends OfflineManagerEvent implements Cancellable {

    private final Player player;
    private final IUser user;
    private final IPlayerData playerData;
    private boolean cancelled;

    @Deprecated
    public ClearOfflineInventoryEvent(@NotNull Player player, @NotNull IUser user) {
        this.player = player;
        this.user = user;
        this.playerData = user.getPlayerData();
    }

    public ClearOfflineInventoryEvent(@NotNull Player player, @NotNull IPlayerData playerData) {
        this.player = player;
        this.playerData = playerData;
        this.user = playerData.getUser();
    }

    /**
     * Gets the player who cleared the inventory
     * @return the player who cleared the inventory
     */
    @NotNull
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the inventory owner
     * @deprecated use getPlayerData
     * @return inventory owner
     */
    @Deprecated
    @NotNull
    public IUser getUser() {
        return user;
    }

    /**
     * Gets the owner of inventory
     * @return inventory owner
     */
    @NotNull
    public IPlayerData getPlayerData() {
        return playerData;
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

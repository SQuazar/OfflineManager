package net.flawe.offlinemanager.api.events.inventory;

import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.entity.IUser;
import net.flawe.offlinemanager.api.events.OfflineManagerEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;

/**
 * Called when the player clears the offline player's inventory
 * @author flaweoff
 */
public class ClearOfflineInventoryEvent extends OfflineManagerEvent implements Cancellable {

    private final Player player;
    private final IUser user;
    private final IPlayerData playerData;
    private boolean cancelled;

    /**
     *
     * @param player who clear inventory
     * @param user who's inventory was cleared
     */
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
     *
     * @return The player who cleared the inventory
     */
    @NotNull
    public Player getPlayer() {
        return player;
    }

    /**
     *
     * @return Player whose inventory was deducted
     */
    @Deprecated
    @NotNull
    public IUser getUser() {
        return user;
    }

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

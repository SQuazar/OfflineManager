package net.flawe.offlinemanager.api.events.inventory;

import net.flawe.offlinemanager.api.IUser;
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
    private boolean cancelled;

    /**
     *
     * @param player who clear inventory
     * @param user who's inventory was cleared
     */
    public ClearOfflineInventoryEvent(@NotNull Player player, @NotNull IUser user) {
        this.player = player;
        this.user = user;
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
    @NotNull
    public IUser getUser() {
        return user;
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

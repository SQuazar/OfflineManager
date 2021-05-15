package net.flawe.offlinemanager.api.events.entity.player;

import net.flawe.offlinemanager.api.IUser;
import net.flawe.offlinemanager.api.events.OfflineManagerEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a player heal the offline player
 * @author flaweoff
 */
public class HealOfflinePlayerEvent extends OfflineManagerEvent implements Cancellable {

    private final Player player;
    private final IUser target;
    private boolean cancelled;

    /**
     *
     * @param player the player who healing offline player
     * @param target the player who was healed
     */
    public HealOfflinePlayerEvent(@NotNull Player player, @NotNull IUser target) {
        this.player = player;
        this.target = target;
    }

    /**
     *
     * @return The player who healing offline player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     *
     * @return The player who was healed
     */
    public IUser getTarget() {
        return target;
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

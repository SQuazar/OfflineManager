package net.flawe.offlinemanager.events;

import net.flawe.offlinemanager.api.IUser;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a player kills an offline player
 * @author flaweoff
 */
public class KillOfflinePlayerEvent extends OfflineManagerEvent implements Cancellable {

    private final Player player;
    private final IUser target;
    private boolean cancelled;

    /**
     *
     * @param player the player who killed offline player
     * @param target the offline player who's killed
     */
    public KillOfflinePlayerEvent(@NotNull Player player, @NotNull IUser target) {
        this.player = player;
        this.target = target;
    }

    /**
     *
     * @return The player who killed offline player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     *
     * @return The offline player who was killed
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

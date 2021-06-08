package net.flawe.offlinemanager.api.events.entity.player;

import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.entity.IUser;
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
    private final IPlayerData playerData;
    private boolean cancelled;

    /**
     *
     * @param player the player who healing offline player
     * @param target the player who was healed
     */
    @Deprecated
    public HealOfflinePlayerEvent(@NotNull Player player, @NotNull IUser target) {
        this.player = player;
        this.target = target;
        this.playerData = target.getPlayerData();
    }

    public HealOfflinePlayerEvent(@NotNull Player player, @NotNull IPlayerData playerData) {
        this.player = player;
        this.playerData = playerData;
        this.target = playerData.getUser();
    }

    /**
     *
     * @return The player who healing offline player
     */
    @NotNull
    public Player getPlayer() {
        return player;
    }

    /**
     *
     * @return The player who was healed
     */
    @Deprecated
    @NotNull
    public IUser getTarget() {
        return target;
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

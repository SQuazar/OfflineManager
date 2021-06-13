package net.flawe.offlinemanager.api.event.entity.player;

import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.entity.IUser;
import net.flawe.offlinemanager.api.event.OfflineManagerEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;

/**
 * Called when an offline player has been fed by another player
 *
 * @author flawe
 */
public class FeedOfflinePlayerEvent extends OfflineManagerEvent implements Cancellable {

    private final Player player;
    private final IUser target;
    private final IPlayerData playerData;
    private boolean cancelled;

    /**
     * @param player the player who restored the hunger of the offline player
     * @param target the player who was fed
     */
    @Deprecated
    public FeedOfflinePlayerEvent(@NotNull Player player, @NotNull IUser target) {
        this.player = player;
        this.target = target;
        this.playerData = target.getPlayerData();
    }

    public FeedOfflinePlayerEvent(@NotNull Player player, @NotNull IPlayerData playerData) {
        this.player = player;
        this.playerData = playerData;
        this.target = playerData.getUser();
    }

    /**
     * Gets the player who restored the hunger of the offline player
     * @return The player who restored the hunger of the offline player
     */
    @NotNull
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the player who was fed
     * @deprecated getPlayerData
     * @return The player who was fed
     */
    @NotNull
    @Deprecated
    public IUser getTarget() {
        return target;
    }

    /**
     * Gets the player who was fed
     * @return The player who was fed
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

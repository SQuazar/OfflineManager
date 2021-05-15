package net.flawe.offlinemanager.api.events.entity.player;

import net.flawe.offlinemanager.api.IUser;
import net.flawe.offlinemanager.api.events.OfflineManagerEvent;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;

/**
 * Called when player was changed the offline player's game mode
 */
public class GameModeChangeEvent extends OfflineManagerEvent implements Cancellable {

    private final IUser target;
    private final Player player;
    private final GameMode gameMode;
    private boolean cancelled;

    /**
     *
     * @param target
     * @param player
     * @param gameMode
     */
    public GameModeChangeEvent(@NotNull IUser target, @NotNull Player player, @NotNull GameMode gameMode) {
        this.target = target;
        this.player = player;
        this.gameMode = gameMode;
    }

    public IUser getTarget() {
        return target;
    }

    public Player getPlayer() {
        return player;
    }

    public GameMode getGameMode() {
        return gameMode;
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

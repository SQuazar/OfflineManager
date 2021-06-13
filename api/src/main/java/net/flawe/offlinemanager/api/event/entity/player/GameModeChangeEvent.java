package net.flawe.offlinemanager.api.event.entity.player;

import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.entity.IUser;
import net.flawe.offlinemanager.api.event.OfflineManagerEvent;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;

/**
 * Called when player was changed the offline player's game mode
 * @author flawe
 */
public class GameModeChangeEvent extends OfflineManagerEvent implements Cancellable {

    private final IUser target;
    private final IPlayerData playerData;
    private final Player player;
    private final GameMode gameMode;
    private boolean cancelled;

    @Deprecated
    public GameModeChangeEvent(@NotNull IUser target, @NotNull Player player, @NotNull GameMode gameMode) {
        this.target = target;
        this.playerData = target.getPlayerData();
        this.player = player;
        this.gameMode = gameMode;
    }

    public GameModeChangeEvent(@NotNull IPlayerData playerData, @NotNull Player player, @NotNull GameMode gameMode) {
        this.playerData = playerData;
        this.target = playerData.getUser();
        this.player = player;
        this.gameMode = gameMode;
    }

    /**
     * Gets a player whose game mode is changed
     * @deprecated use getPlayerData
     * @return player whose game mode is changed
     */
    @Deprecated
    @NotNull
    public IUser getTarget() {
        return target;
    }

    /**
     * Gets a player whose game mode is changed
     * @return player whose game mode is changed
     */
    @NotNull
    public IPlayerData getPlayerData() {
        return playerData;
    }

    /**
     * Gets a player who change game mode for offline player
     * @return player who change game mode for offline player
     */
    @NotNull
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the new game mode
     * @return new game mode
     */
    @NotNull
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

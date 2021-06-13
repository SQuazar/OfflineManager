package net.flawe.offlinemanager.api.event.data;

import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.event.OfflineManagerEvent;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Called when the offline player is loaded
 * @author flawe
 */
public class LoadPlayerEvent extends OfflineManagerEvent {

    private final Player player;
    private final IPlayerData playerData;

    @Deprecated
    public LoadPlayerEvent(@NotNull Player player) {
        super(false);
        this.player = player;
        this.playerData = null;
    }

    public LoadPlayerEvent(@NotNull IPlayerData playerData) {
        this.player = null;
        this.playerData = playerData;
    }

    /**
     * Gets the player data that has been loaded
     * @return player data than been loaded
     */
    @Nullable
    public IPlayerData getPlayerData() {
        return playerData;
    }

    /**
     * Gets the player that has been loaded
     * @return player that has been loaded
     */
    @Deprecated
    public @Nullable Player getPlayer() {
        return player;
    }

}

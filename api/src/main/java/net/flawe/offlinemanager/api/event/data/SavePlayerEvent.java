package net.flawe.offlinemanager.api.event.data;

import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.enums.SavePlayerType;
import net.flawe.offlinemanager.api.event.OfflineManagerEvent;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Called when player data is saving
 * @author flawe
 */
public class SavePlayerEvent extends OfflineManagerEvent {

    private final Player player;
    private final IPlayerData playerData;
    private final SavePlayerType saveType;

    @Deprecated
    public SavePlayerEvent(@NotNull Player player, @NotNull SavePlayerType type) {
        super(false);
        this.player = player;
        this.saveType = type;
        this.playerData = null;
    }

    public SavePlayerEvent(@NotNull IPlayerData playerData, @NotNull SavePlayerType type) {
        this.playerData = playerData;
        this.player = null;
        this.saveType = type;
    }

    /**
     * Gets the save type
     * @return save type
     */
    @NotNull
    public SavePlayerType getSaveType() {
        return saveType;
    }

    /**
     * Gets the player who was saved
     * @deprecated use getPlayerData
     * @return player who was saved
     */
    @Deprecated
    public @Nullable Player getPlayer() {
        return player;
    }

    /**
     * Gets the player data that has been saved
     * @return player data that has been saved
     */
    @Nullable
    public IPlayerData getPlayerData() {
        return playerData;
    }
}

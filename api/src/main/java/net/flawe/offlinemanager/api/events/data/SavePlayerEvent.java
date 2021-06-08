package net.flawe.offlinemanager.api.events.data;

import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.enums.SavePlayerType;
import net.flawe.offlinemanager.api.events.OfflineManagerEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SavePlayerEvent extends OfflineManagerEvent implements Cancellable {

    private final Player player;
    private final IPlayerData playerData;
    private boolean cancelled;
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

    @NotNull
    public SavePlayerType getSaveType() {
        return saveType;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Deprecated
    public @Nullable Player getPlayer() {
        return player;
    }

    @Nullable
    public IPlayerData getPlayerData() {
        return playerData;
    }
}

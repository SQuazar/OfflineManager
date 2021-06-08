package net.flawe.offlinemanager.api.events.data;

import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.events.OfflineManagerEvent;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

    @Nullable
    public IPlayerData getPlayerData() {
        return playerData;
    }

    @Deprecated
    public @Nullable Player getPlayer() {
        return player;
    }

}

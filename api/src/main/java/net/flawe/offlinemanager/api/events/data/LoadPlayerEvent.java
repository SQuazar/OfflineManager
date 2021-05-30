package net.flawe.offlinemanager.api.events.data;

import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.events.OfflineManagerEvent;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class LoadPlayerEvent extends OfflineManagerEvent {

    private final Player player;
    private final IPlayerData playerData;

    @Deprecated
    public LoadPlayerEvent(Player player) {
        super(false);
        this.player = player;
        this.playerData = null;
    }

    public LoadPlayerEvent(IPlayerData playerData) {
        this.player = playerData.getUser().getPlayer();
        this.playerData = playerData;
    }

    private boolean canceled;

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean b) {
        this.canceled = b;
    }

    @Nullable
    public IPlayerData getPlayerData() {
        return playerData;
    }

    @Deprecated
    public Player getPlayer() {
        return player;
    }

}

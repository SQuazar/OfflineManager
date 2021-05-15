package net.flawe.offlinemanager.api.events.data;

import net.flawe.offlinemanager.api.events.OfflineManagerEvent;
import org.bukkit.entity.Player;

public class LoadPlayerEvent extends OfflineManagerEvent {

    private final Player player;

    public LoadPlayerEvent(Player player) {
        super(false);
        this.player = player;
    }

    private boolean canceled;

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean b) {
        this.canceled = b;
    }

    public Player getPlayer() {
        return player;
    }

}

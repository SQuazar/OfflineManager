package net.flawe.offlinemanager.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

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

package net.flawe.offlinemanager.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

public class LoadPlayerEvent extends OfflineManagerEvent {

    private final Player player;

    public LoadPlayerEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

}

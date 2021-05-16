package net.flawe.offlinemanager.api.events.data;

import net.flawe.offlinemanager.api.enums.SavePlayerType;
import net.flawe.offlinemanager.api.events.OfflineManagerEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

public class SavePlayerEvent extends OfflineManagerEvent implements Cancellable {

    private final Player player;
    private boolean cancelled;
    private final SavePlayerType saveType;

    public SavePlayerEvent(Player player, SavePlayerType type) {
        super(false);
        this.player = player;
        this.saveType = type;
    }

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

    public Player getPlayer() {
        return player;
    }
}

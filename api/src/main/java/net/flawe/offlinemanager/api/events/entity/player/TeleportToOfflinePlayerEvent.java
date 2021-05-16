package net.flawe.offlinemanager.api.events.entity.player;

import net.flawe.offlinemanager.api.IUser;
import net.flawe.offlinemanager.api.events.OfflineManagerEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

public class TeleportToOfflinePlayerEvent extends OfflineManagerEvent implements Cancellable {

    private final Player player;
    private final IUser target;
    private final Location from;
    private final Location to;
    private boolean cancelled;

    public TeleportToOfflinePlayerEvent(Player player, IUser target, Location from, Location to) {
        this.player = player;
        this.target = target;
        this.from = from;
        this.to = to;
    }

    public Player getPlayer() {
        return player;
    }

    public IUser getTarget() {
        return target;
    }

    public Location getFrom() {
        return from;
    }

    public Location getTo() {
        return to;
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

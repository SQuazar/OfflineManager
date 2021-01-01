package net.flawe.offlinemanager.events;

import net.flawe.offlinemanager.api.IUser;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

public class OfflinePlayerTeleportEvent extends OfflineManagerEvent implements Cancellable {

    private final Player player;
    private final IUser target;
    private final Location to;
    private final Location from;
    private boolean cancelled;

    public OfflinePlayerTeleportEvent(Player player, IUser target, Location to, Location from) {
        this.player = player;
        this.target = target;
        this.to = to;
        this.from = from;
    }

    public Player getPlayer() {
        return player;
    }

    public IUser getTarget() {
        return target;
    }

    public Location getTo() {
        return to;
    }

    public Location getFrom() {
        return from;
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

package net.flawe.offlinemanager.api.events.entity.player;

import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.entity.IUser;
import net.flawe.offlinemanager.api.events.OfflineManagerEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

public class OfflinePlayerTeleportEvent extends OfflineManagerEvent implements Cancellable {

    private final Player player;
    private final IUser target;
    private final IPlayerData playerData;
    private final Location to;
    private final Location from;
    private boolean cancelled;

    @Deprecated
    public OfflinePlayerTeleportEvent(Player player, IUser target, Location to, Location from) {
        this.player = player;
        this.target = target;
        this.playerData = target.getPlayerData();
        this.to = to;
        this.from = from;
    }

    public OfflinePlayerTeleportEvent(Player player, IPlayerData playerData, Location to, Location from) {
        this.player = player;
        this.playerData = playerData;
        this.target = playerData.getUser();
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

    public IPlayerData getPlayerData() {
        return playerData;
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

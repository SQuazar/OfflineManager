package net.flawe.offlinemanager.api.event.entity.player;

import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.entity.IUser;
import net.flawe.offlinemanager.api.event.OfflineManagerEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

/**
 * Called when offline player teleported to other player
 * @author flawe
 */
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

    /**
     * Gets the player to which the offline player is teleported
     * @return player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the offline player
     * @deprecated use getPlayerData
     * @return offline player
     */
    public IUser getTarget() {
        return target;
    }

    /**
     * Gets target location
     * @return location to
     */
    public Location getTo() {
        return to;
    }

    /**
     * Gets location from
     * @return location from
     */
    public Location getFrom() {
        return from;
    }

    /**
     * Gets the offline player data
     * @return data of offline player
     */
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

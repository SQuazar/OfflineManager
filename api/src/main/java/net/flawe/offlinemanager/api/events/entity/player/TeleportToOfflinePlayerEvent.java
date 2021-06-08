package net.flawe.offlinemanager.api.events.entity.player;

import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.entity.IUser;
import net.flawe.offlinemanager.api.events.OfflineManagerEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;

public class TeleportToOfflinePlayerEvent extends OfflineManagerEvent implements Cancellable {

    private final Player player;
    private final IUser target;
    private final IPlayerData playerData;
    private final Location from;
    private final Location to;
    private boolean cancelled;

    @Deprecated
    public TeleportToOfflinePlayerEvent(@NotNull Player player, @NotNull IUser target, @NotNull Location from, @NotNull Location to) {
        this.player = player;
        this.target = target;
        this.playerData = target.getPlayerData();
        this.from = from;
        this.to = to;
    }

    public TeleportToOfflinePlayerEvent(@NotNull Player player, @NotNull IPlayerData playerData, @NotNull Location from, @NotNull Location to) {
        this.player = player;
        this.playerData = playerData;
        this.target = playerData.getUser();
        this.from = from;
        this.to = to;
    }

    @NotNull
    public Player getPlayer() {
        return player;
    }

    @Deprecated
    @NotNull
    public IUser getTarget() {
        return target;
    }

    @NotNull
    public IPlayerData getPlayerData() {
        return playerData;
    }

    @NotNull
    public Location getFrom() {
        return from;
    }

    @NotNull
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

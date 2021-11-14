/*
 * Copyright (c) 2021 flaweoff
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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

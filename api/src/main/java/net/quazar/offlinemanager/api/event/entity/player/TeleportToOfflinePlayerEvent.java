/*
 * Copyright (c) 2021 squazar
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

package net.quazar.offlinemanager.api.event.entity.player;

import lombok.Getter;
import lombok.Setter;
import net.quazar.offlinemanager.api.data.entity.IPlayerData;
import net.quazar.offlinemanager.api.event.OfflineManagerEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;

/**
 * Called when player was teleported to offline player
 * @author quazar
 */
public class TeleportToOfflinePlayerEvent extends OfflineManagerEvent implements Cancellable {
    private final Player player;
    private final IPlayerData playerData;
    private final Location from;
    private final Location to;
    @Getter @Setter
    private boolean cancelled;

    public TeleportToOfflinePlayerEvent(@NotNull Player player, @NotNull IPlayerData playerData, @NotNull Location from, @NotNull Location to) {
        this.player = player;
        this.playerData = playerData;
        this.from = from;
        this.to = to;
    }

    /**
     * Gets the player who teleported
     * @return player who teleported
     */
    @NotNull
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the player to whom teleport
     * @return target player
     */
    @NotNull
    public IPlayerData getPlayerData() {
        return playerData;
    }

    /**
     * Gets the location from
     * @return location from
     */
    @NotNull
    public Location getFrom() {
        return from;
    }

    /**
     * Gets the target player location
     * @return target player location
     */
    @NotNull
    public Location getTo() {
        return to;
    }
}

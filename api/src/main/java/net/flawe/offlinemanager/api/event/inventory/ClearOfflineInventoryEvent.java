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

package net.flawe.offlinemanager.api.event.inventory;

import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.entity.IUser;
import net.flawe.offlinemanager.api.event.OfflineManagerEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;

/**
 * Called when the player clears the offline player's inventory
 * @author flawe
 */
public class ClearOfflineInventoryEvent extends OfflineManagerEvent implements Cancellable {

    private final Player player;
    private final IUser user;
    private final IPlayerData playerData;
    private boolean cancelled;

    @Deprecated
    public ClearOfflineInventoryEvent(@NotNull Player player, @NotNull IUser user) {
        this.player = player;
        this.user = user;
        this.playerData = user.getPlayerData();
    }

    public ClearOfflineInventoryEvent(@NotNull Player player, @NotNull IPlayerData playerData) {
        this.player = player;
        this.playerData = playerData;
        this.user = playerData.getUser();
    }

    /**
     * Gets the player who cleared the inventory
     * @return the player who cleared the inventory
     */
    @NotNull
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the inventory owner
     * @deprecated use getPlayerData
     * @return inventory owner
     */
    @Deprecated
    @NotNull
    public IUser getUser() {
        return user;
    }

    /**
     * Gets the owner of inventory
     * @return inventory owner
     */
    @NotNull
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

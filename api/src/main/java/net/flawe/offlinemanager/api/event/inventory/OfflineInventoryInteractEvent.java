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

import lombok.Getter;
import lombok.Setter;
import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.entity.IUser;
import net.flawe.offlinemanager.api.enums.InventoryType;
import net.flawe.offlinemanager.api.event.OfflineManagerEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

/**
 * Called when player interacts with offline inventory
 * @author flawe
 */
public class OfflineInventoryInteractEvent extends OfflineManagerEvent implements Cancellable {

    private final Player player;
    private final IUser target;
    private final IPlayerData playerData;
    private final InventoryType inventoryType;
    private final Inventory inventory;
    @Getter @Setter
    private boolean cancelled;

    @Deprecated
    public OfflineInventoryInteractEvent(@NotNull Player player, @NotNull IUser target,
                                         @NotNull InventoryType inventoryType, @NotNull Inventory inventory) {
        this.player = player;
        this.target = target;
        this.playerData = target.getPlayerData();
        this.inventoryType = inventoryType;
        this.inventory = inventory;
    }

    public OfflineInventoryInteractEvent(@NotNull Player player, @NotNull IPlayerData playerData,
                                         @NotNull InventoryType inventoryType, @NotNull Inventory inventory) {
        this.player = player;
        this.playerData = playerData;
        this.target = playerData.getUser();
        this.inventoryType = inventoryType;
        this.inventory = inventory;
    }

    /**
     * Gets the player whose interacts with offline inventory
     * @return interacting player
     */
    @NotNull
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the inventory owner
     * @deprecated use getPlayerData
     * @return owner of inventory
     */
    @Deprecated
    @NotNull
    public IUser getTarget() {
        return target;
    }

    /**
     * Gets the inventory owner
     * @return owner of inventory
     */
    @NotNull
    public IPlayerData getPlayerData() {
        return playerData;
    }

    /**
     * Gets the inventory type
     * @return type of inventory
     */
    @NotNull
    public InventoryType getInventoryType() {
        return inventoryType;
    }

    /**
     * Gets the inventory the player interacted with
     * @return inventory
     */
    @NotNull
    public Inventory getInventory() {
        return inventory;
    }
}

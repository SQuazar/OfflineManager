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

package net.quazar.offlinemanager.api.event.inventory;

import lombok.Getter;
import lombok.Setter;
import net.quazar.offlinemanager.api.data.entity.IPlayerData;
import net.quazar.offlinemanager.api.enums.InventoryType;
import net.quazar.offlinemanager.api.event.OfflineManagerEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

/**
 * Called when player opens offline player inventory
 * @author quazar
 */
public class OpenOfflineInventoryEvent extends OfflineManagerEvent implements Cancellable {
    private final Player player;
    private final IPlayerData playerData;
    private final Inventory inventory;
    private final InventoryType inventoryType;
    @Getter @Setter
    private boolean cancelled;

    public OpenOfflineInventoryEvent(@NotNull Player player, @NotNull IPlayerData playerData, @NotNull Inventory inventory, @NotNull InventoryType type) {
        this.player = player;
        this.playerData = playerData;
        this.inventoryType = type;
        this.inventory = inventory;
    }

    /**
     * Gets the player who open the inventory
     * @return player whose open the inventory
     */
    @NotNull
    public Player getPlayer() {
        return player;
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
     * Gets the inventory who's gonna be open
     * @return inventory based of target player data
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Gets the inventory type
     * @return type of inventory
     */
    @NotNull
    public InventoryType getInventoryType() {
        return inventoryType;
    }
}

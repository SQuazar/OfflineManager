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

package net.flawe.offlinemanager.api.inventory.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;

/**
 * Test class
 * Not used in current version
 */
@AllArgsConstructor
public class OfflineInventoryView extends InventoryView {

    @Getter private final IPlayerData playerData;
    private final Player player;

    @NotNull
    @Override
    public Inventory getTopInventory() {
        return playerData.getInventory();
    }

    @NotNull
    @Override
    public Inventory getBottomInventory() {
        return player.getInventory();
    }

    @NotNull
    @Override
    public HumanEntity getPlayer() {
        return player;
    }

    @NotNull
    @Override
    public InventoryType getType() {
        return InventoryType.PLAYER;
    }

    @NotNull
    @Override
    public String getTitle() {
        return "Title";
    }
}

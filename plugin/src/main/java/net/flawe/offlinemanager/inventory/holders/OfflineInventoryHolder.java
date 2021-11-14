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

package net.flawe.offlinemanager.inventory.holders;

import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.entity.IUser;
import net.flawe.offlinemanager.api.enums.InventoryType;
import net.flawe.offlinemanager.api.inventory.holder.IOfflineInvHolder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OfflineInventoryHolder implements IOfflineInvHolder {

    private final IUser user;
    private final Player seen;
    private final String name;
    private final IPlayerData playerData;

    @Deprecated
    public OfflineInventoryHolder(@NotNull IUser user) {
        this(user, null, "Inventory");
    }

    @Deprecated
    public OfflineInventoryHolder(@NotNull IUser user, @Nullable Player seen) {
        this(user, seen, "Inventory");
    }

    @Deprecated
    public OfflineInventoryHolder(@NotNull IUser user, @Nullable Player seen, @NotNull String name) {
        this.user = user;
        this.seen = seen;
        this.name = name;
        this.playerData = user.getPlayerData();
    }

    public OfflineInventoryHolder(@NotNull IPlayerData playerData, @Nullable Player seen, @NotNull String name) {
        this.user = playerData.getUser();
        this.seen = seen;
        this.name = name;
        this.playerData = playerData;
    }

    @Override
    public @NotNull Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 36, name);
        inventory.setContents(playerData.getInventory().getStorageContents());
        return inventory;
    }

    @Override
    public @NotNull Player getPlayer() {
        return user.getPlayer();
    }

    public @NotNull IPlayerData getPlayerData() {
        return playerData;
    }

    @Override
    public @NotNull IUser getUser() {
        return user;
    }

    @Override
    public @NotNull String getInventoryName() {
        return name;
    }

    @Override
    public @NotNull InventoryType getInventoryType() {
        return InventoryType.DEFAULT;
    }

    public @Nullable Player getWhoSeen() {
        return seen;
    }

    @Override
    public boolean isViewed() {
        return seen != null;
    }
}

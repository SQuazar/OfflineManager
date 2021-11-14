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

package net.flawe.offlinemanager.api.entity;

import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.enums.SavePlayerType;
import net.flawe.offlinemanager.api.inventory.IArmorInventory;
import net.flawe.offlinemanager.api.inventory.IEnderChest;
import net.flawe.offlinemanager.api.inventory.IInventory;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Offline user interface for multi-version usage
 *
 * @author flawe
 */
@Deprecated
public interface IUser {

    /**
     * Gets the bukkit player from offline player
     * @return bukkit player
     */
    Player getPlayer();

    /**
     * Gets the bukkit offline player
     * @return bukkit offline player
     */
    OfflinePlayer getOfflinePlayer();

    /**
     * Gets the offline player uuid
     * @return uuid
     */
    UUID getUUID();

    /**
     * Gets the offline player location
     * @return offline player location
     */
    Location getLocation();

    /**
     * Gets the offline player inventory
     * @return offline inventory
     */
    IInventory getInventory();

    /**
     * Gets the player ender chers
     * @return ender chest
     */
    IEnderChest getEnderChest();

    /**
     * Gets the offline player armor inventory
     * @return armor inventory
     */
    IArmorInventory getArmorInventory();

    /**
     * Gets the offline player game mode
     * @return game mode
     */
    GameMode getGameMode();

    /**
     * Kill offline user
     */
    void kill();

    /**
     * Change game mode for offline player
     * @param gameMode new game mode
     */
    void setGameMode(GameMode gameMode);

    /**
     * Change offline player location
     * @param location new location
     */
    void teleport(Location location);

    /**
     * Change offline player location
     * @param player target player
     */
    void teleport(Player player);

    /**
     * Save the offline player
     */
    void save();

    /**
     * Save the offline player with save type
     * @param type save type
     * @see SavePlayerType
     */
    void save(SavePlayerType type);

    /**
     * Gets the player data
     * @deprecated it is recommended to receive via INMSManager
     * @return Offline player data
     */
    @Deprecated
    IPlayerData getPlayerData();
}

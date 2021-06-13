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

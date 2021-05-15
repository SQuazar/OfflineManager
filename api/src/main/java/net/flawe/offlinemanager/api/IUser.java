package net.flawe.offlinemanager.api;

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
public interface IUser {
    /**
     * Player from loaded user
     *
     * @return Player from user
     */
    Player getPlayer();

    /**
     * User offline player
     *
     * @return User offline player
     */
    OfflinePlayer getOfflinePlayer();

    /**
     * User UUID
     *
     * @return User UUID
     */
    UUID getUUID();

    /**
     * User offline location
     *
     * @return User location
     */
    Location getLocation();

    /**
     * User offline inventory
     *
     * @return User inventory
     */
    IInventory getInventory();

    /**
     * User offline enderchest
     *
     * @return User enderchest
     */
    IEnderChest getEnderChest();

    /**
     * User offline armor inventory
     *
     * @return User armor inventory
     */
    IArmorInventory getArmorInventory();

    /**
     * User offline gamemode
     *
     * @return User gamemode
     */
    GameMode getGameMode();

    /**
     * Kill offline user
     */
    void kill();

    /**
     * Set user new gamemode
     *
     * @param gameMode New gamemode
     */
    void setGameMode(GameMode gameMode);

    /**
     * Change offline user location to new location
     *
     * @param location New user location
     */
    void teleport(Location location);

    /**
     * Change offline user location to player location
     *
     * @param player Target player
     */
    void teleport(Player player);

    /**
     * Save offline user
     */
    void save();

    /**
     * Save offline user with save type
     *
     * @param type Save type
     * @see SavePlayerType
     */
    void save(SavePlayerType type);
}

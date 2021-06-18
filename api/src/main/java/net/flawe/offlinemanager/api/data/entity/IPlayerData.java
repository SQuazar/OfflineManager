package net.flawe.offlinemanager.api.data.entity;

import net.flawe.offlinemanager.api.entity.IUser;
import net.flawe.offlinemanager.api.enums.SavePlayerType;
import net.flawe.offlinemanager.api.inventory.AbstractPlayerInventory;
import net.flawe.offlinemanager.api.inventory.IArmorInventory;
import net.flawe.offlinemanager.api.inventory.IEnderChest;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;

import java.util.UUID;

/**
 * Class for working with player data
 *
 * @author flawe
 * @since 3.0.0
 */
public interface IPlayerData {

    /**
     * Gets the uuid
     * @return uuid
     */
    UUID getUUID();

    /**
     * Gets the name
     * @return name
     */
    String getName();

    /**
     * Gets the offline player inventory
     * @return inventory
     */
    AbstractPlayerInventory getInventory();

    /**
     * Gets the offline player armor inventory
     * @return armor inventory
     */
    IArmorInventory getArmorInventory();

    /**
     * Gets the offline player ender chest
     * @return ender chest
     */
    IEnderChest getEnderChest();

    /**
     * Gets fall distance
     * @return fall distance
     */
    float getFallDistance();

    /**
     * Sets the fall distance
     * @param distance new fall distance value
     */
    void setFallDistance(float distance);

    /**
     * Gets the fire ticks
     * @return fire ticks
     */
    short getFireTicks();

    /**
     * Sets the fire ticks
     * @param ticks new fire ticks value
     */
    void setFireTicks(short ticks);

    /**
     * Gets the exhaustion
     * @return exhaustion
     */
    float getExhaustion();

    /**
     * Sets the exhaustion
     * @param exhaustion new exhaustion value
     */
    void setExhaustion(float exhaustion);

    /**
     * Gets the food level
     * @return food level
     */
    int getFoodLevel();

    /**
     * Sets the food level
     * @param level new food level
     */
    void setFoodLevel(int level);

    /**
     * Gets the saturation
     * @return saturation
     */
    float getSaturation();

    /**
     * Sets the saturation
     * @param saturation new saturation
     */
    void setSaturation(float saturation);

    /**
     * Gets the health
     * @return health
     */
    float getHealth();

    /**
     * Sets the health
     * @param health new health
     */
    void setHealth(float health);

    /**
     * Checks if invulnerable is enabled
     * @return true if invulnerable enabled
     */
    boolean isInvulnerable();

    /**
     * Sets the invulnerable status
     * @param b new invulnerable status
     */
    void setInvulnerable(boolean b);

    /**
     * Checks on ground status
     * @return true if the player on ground
     */
    boolean isOnGround();

    /**
     * Gets the game mode
     * @return game mode
     */
    GameMode getGameMode();

    /**
     * Sets the game mode
     * @param gameMode new game mode value
     */
    void setGameMode(GameMode gameMode);

    /**
     * Gets the portal cooldown
     * @return portal cooldown
     */
    int getPortalCooldown();

    /**
     * Sets the portal cooldown
     * @param cooldown new portal cooldown value
     */
    void setPortalCooldown(int cooldown);

    /**
     * Gets the selected slot
     * @return selected slot
     */
    int getSelectedSlot();

    /**
     * Sets the selected slot
     * @param slot new selected slot value
     */
    void setSelectedSlot(int slot);

    /**
     * Gets the world
     * @return world
     */
    World getWorld();

    /**
     * Gets the location
     * @return location
     */
    Location getLocation();

    /**
     * Sets the location
     * @param location new location value
     */
    void setLocation(Location location);

    /**
     * Sets the location by entity
     * @param entity new location source
     */
    void setLocation(Entity entity);

    /**
     * Gets the exp level
     * @return exp level
     */
    int getExpLevel();

    /**
     * Sets the exp level
     * @param level new exp level
     */
    void setExpLevel(int level);

    /**
     * Gets the exp
     * @return exp
     */
    float getExp();

    /**
     * Sets the exp
     * @param exp new exp
     */
    void setExp(float exp);

    /**
     * Gets the exp total
     * @return exp total
     */
    int getExpTotal();

    /**
     * Sets the exp total
     * @param exp new exp total
     */
    void setExpTotal(int exp);

    /**
     * Changes the data online status
     * Set true if you would like load data to player
     * Set false if you would like load player to data
     * @param b online status
     */
    void setOnline(boolean b);

    /**
     * Save player data
     */
    void save();

    /**
     * Save player data with type
     * @param type save player type
     */
    void save(SavePlayerType type);

    /**
     * Gets the user. Not recommended.
     * @return user
     */
    @Deprecated
    IUser getUser();
}

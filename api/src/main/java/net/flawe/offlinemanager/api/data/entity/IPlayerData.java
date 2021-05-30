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

import java.util.UUID;

public interface IPlayerData {

    UUID getUUID();

    String getName();

    AbstractPlayerInventory getInventory();

    IArmorInventory getArmorInventory();

    IEnderChest getEnderChest();

    float getFallDistance();

    void setFallDistance(float distance);

    short getFireTicks();

    void setFireTicks(short ticks);

    float getExhaustion();

    void setExhaustion(float exhaustion);

    int getFoodLevel();

    void setFoodLevel(int level);

    float getSaturation();

    void setSaturation(float saturation);

    float getHealth();

    void setHealth(float health);

    boolean isInvulnerable();

    void setInvulnerable(boolean b);

    boolean isOnGround();

    GameMode getGameMode();

    void setGameMode(GameMode gameMode);

    int getPortalCooldown();

    void setPortalCooldown(int cooldown);

    int getSelectedSlot();

    void setSelectedSlot(int slot);

    World getWorld();

    Location getLocation();

    void setLocation(Location location);

    void setLocation(Entity entity);

    int getExpLevel();

    void setExpLevel(int level);

    float getExp();

    void setExp(float exp);

    int getExpTotal();

    void setExpTotal(int exp);

    void save();

    void save(SavePlayerType type);

    @Deprecated
    IUser getUser();
}

package net.flawe.offlinemanager.api;

import net.flawe.offlinemanager.api.enums.SavePlayerType;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface IUser {
    Player getPlayer();

    OfflinePlayer getOfflinePlayer();

    UUID getUUID();

    Location getLocation();

    IInventory getInventory();

    IEnderChest getEnderChest();

    IArmorInventory getArmorInventory();

    GameMode getGameMode();

    void kill();

    void setGameMode(GameMode gameMode);

    void teleport(Location location);

    void teleport(Player player);

    void save();

    void save(SavePlayerType type);
}

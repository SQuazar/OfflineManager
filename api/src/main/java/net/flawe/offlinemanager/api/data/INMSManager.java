package net.flawe.offlinemanager.api.data;

import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.entity.IUser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

/**
 * Helper class for NMS interaction
 *
 * @author flawe
 */
public interface INMSManager {
    /**
     * Used for storage offline players and tab complete
     *
     * @return All players from playerdata folder
     */
    List<String> getSeenPlayers();

    /**
     * Used to get an offline player by username
     * @param s Player's username
     * @return Offline player with username from parameter
     */
    @Deprecated
    IUser getUser(String s);

    /**
     * Used to get an offline player by UUID
     * @param uuid Player's uuid
     * @return Offline player with uuid from parameter
     */
    @Deprecated
    IUser getUser(UUID uuid);

    /**
     * Used to get player data
     * @param uuid The UUID of the player you want to get
     * @return The player's data that matches the entered parameters
     */
    @Nullable
    IPlayerData getPlayerData(@NotNull UUID uuid);

    /**
     * Used to get player data
     * @param name The name of the player you want to get
     * @return The player's data that matches the entered parameters
     */
    @Nullable
    IPlayerData getPlayerData(@NotNull String name);

}

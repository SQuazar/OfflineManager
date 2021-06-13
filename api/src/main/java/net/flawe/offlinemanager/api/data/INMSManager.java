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
     * Gets the offline players nicknames
     * @return all players from playerdata folder
     */
    List<String> getSeenPlayers();

    /**
     * Gets the offline player by name
     * @deprecated use getPlayerData
     * @param s player's username
     * @return offline player with username from parameter
     */
    @Deprecated
    IUser getUser(String s);

    /**
     * Gets the offline player by uuid
     * @deprecated use getPlayerData
     * @param uuid player's uuid
     * @return offline player with uuid from parameter
     */
    @Deprecated
    IUser getUser(UUID uuid);

    /**
     * Gets the offline player data
     * @param uuid player uuid
     * @return the player's data that matches the entered parameters
     */
    IPlayerData getPlayerData(@NotNull UUID uuid);

    /**
     * Gets the offline player data
     * @param name player name
     * @return the player's data that matches the entered parameters
     */
    IPlayerData getPlayerData(@NotNull String name);

}

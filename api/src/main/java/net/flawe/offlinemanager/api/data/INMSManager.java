package net.flawe.offlinemanager.api.data;

import net.flawe.offlinemanager.api.IUser;

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
    IUser getUser(String s);

    /**
     * Used to get an offline player by UUID
     * @param uuid Player's uuid
     * @return Offline player with uuid from parameter
     */
    IUser getUser(UUID uuid);
}

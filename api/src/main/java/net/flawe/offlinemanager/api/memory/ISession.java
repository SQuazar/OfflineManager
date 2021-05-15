package net.flawe.offlinemanager.api.memory;

import net.flawe.offlinemanager.api.enums.ActiveType;

import java.util.Map;
import java.util.UUID;

/**
 * Used for storage players session in offline inventories
 *
 * @author flawe
 */
public interface ISession {
    /**
     * Used for get active players by ActiveType
     *
     * @param type Active type
     * @return Map by active type in parameter
     */
    Map<UUID, UUID> getActive(ActiveType type);

    /**
     * Check if active is empty by ActiveType
     *
     * @param type Active type
     * @return True if active by ActiveType is empty
     */
    boolean isEmpty(ActiveType type);

    /**
     * Used for clear all actives by ActiveType
     *
     * @param type ActiveType by which the active map will be cleared
     */
    void clear(ActiveType type);

    /**
     * Remove node by key in active by ActiveType
     *
     * @param uuid Key in active
     * @param type Active type
     */
    void removeByKey(UUID uuid, ActiveType type);

    /**
     * Remove node by value in active by ActiveType
     *
     * @param uuid Value in active
     * @param type Active type
     */
    void removeByValue(UUID uuid, ActiveType type);

    /**
     * Add active by ActiveType
     *
     * @param uuid  Active key
     * @param uuid2 Active value
     * @param type  Active type
     */
    void add(UUID uuid, UUID uuid2, ActiveType type);

    /**
     * Check to contains value in active by type
     *
     * @param uuid Value in active
     * @param type Active type
     * @return True, if active contains the value from parameter
     */
    boolean containsValue(UUID uuid, ActiveType type);

    /**
     * Check to contains key in active by active type
     *
     * @param uuid Key in active
     * @param type Active type
     * @return True, if active contains the key from parameter
     */
    boolean containsKey(UUID uuid, ActiveType type);

    /**
     * Usage for getting key by value using value and active type
     *
     * @param uuid Value in active
     * @param type Active type
     * @return Search result by value and active type
     */
    UUID getKeyByValue(UUID uuid, ActiveType type);
}

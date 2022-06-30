/*
 * Copyright (c) 2021 squazar
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

package net.quazar.offlinemanager.api.memory;

import net.quazar.offlinemanager.api.enums.ActiveType;

import java.util.Map;
import java.util.UUID;

/**
 * Used for storage players session in offline inventories
 *
 * @author quazar
 */
public interface ISession {
    /**
     * Used for get active players by ActiveType
     *
     * @param type active type
     * @return map by active type in parameter
     */
    Map<UUID, UUID> getActive(ActiveType type);

    /**
     * Check if active is empty by ActiveType
     *
     * @param type active type
     * @return true if active by ActiveType is empty
     */
    boolean isEmpty(ActiveType type);

    /**
     * used for clear all actives by ActiveType
     *
     * @param type ActiveType by which the active map will be cleared
     */
    void clear(ActiveType type);

    /**
     * Remove node by key in active by ActiveType
     *
     * @param uuid key in active
     * @param type active type
     */
    void removeByKey(UUID uuid, ActiveType type);

    /**
     * Remove node by value in active by ActiveType
     *
     * @param uuid value in active
     * @param type active type
     */
    void removeByValue(UUID uuid, ActiveType type);

    /**
     * Add active by ActiveType
     *
     * @param uuid  active key
     * @param uuid2 active value
     * @param type  active type
     */
    void add(UUID uuid, UUID uuid2, ActiveType type);

    /**
     * Check to contains value in active by type
     *
     * @param uuid value in active
     * @param type active type
     * @return true, if active contains the value from parameter
     */
    boolean containsValue(UUID uuid, ActiveType type);

    /**
     * Check to contains key in active by active type
     *
     * @param uuid key in active
     * @param type active type
     * @return true, if active contains the key from parameter
     */
    boolean containsKey(UUID uuid, ActiveType type);

    /**
     * Usage for getting key by value using value and active type
     *
     * @param uuid value in active
     * @param type active type
     * @return search result by value and active type
     */
    UUID getKeyByValue(UUID uuid, ActiveType type);
}

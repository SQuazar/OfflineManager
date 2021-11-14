/*
 * Copyright (c) 2021 flaweoff
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

package net.flawe.offlinemanager.data.memory;

import net.flawe.offlinemanager.api.enums.ActiveType;
import net.flawe.offlinemanager.api.memory.ISession;

import java.util.*;

public class Session implements ISession {

    private final Map<UUID, UUID> inventoryActive = new HashMap<>();
    private final Map<UUID, UUID> armorInventoryActive = new HashMap<>();
    private final Map<UUID, UUID> enderchestActive = new HashMap<>();

    @Override
    public Map<UUID, UUID> getActive(ActiveType type) {
        switch (type) {
            case ARMOR_INVENTORY:
                return armorInventoryActive;
            case ENDER_CHEST:
                return enderchestActive;
            case INVENTORY:
                return inventoryActive;
            default:
                return Collections.emptyMap();
        }
    }

    @Override
    public boolean isEmpty(ActiveType type) {
        return getActive(type).isEmpty();
    }

    @Override
    public void clear(ActiveType type) {
        if (type == ActiveType.ALL) {
            for (ActiveType active : ActiveType.values()) {
                if (active == ActiveType.ALL)
                    continue;
                getActive(active).clear();
            }
            return;
        }
        getActive(type).clear();
    }

    @Override
    public void removeByKey(UUID uuid, ActiveType type) {
        if (type == ActiveType.ALL) {
            for (ActiveType active : ActiveType.values()) {
                if (active == ActiveType.ALL)
                    continue;
                getActive(active).remove(uuid);
            }
            return;
        }
        getActive(type).remove(uuid);
    }

    @Override
    public void removeByValue(UUID uuid, ActiveType type) {
        if (type == ActiveType.ALL) {
            for (ActiveType active : ActiveType.values()) {
                if (active == ActiveType.ALL)
                    continue;
                getActive(active).values().removeIf(value -> value.equals(uuid));
            }
            return;
        }
        getActive(type).values().removeIf(value -> value.equals(uuid));
    }

    @Override
    public void add(UUID uuid, UUID uuid2, ActiveType type) {
        if (type == ActiveType.ALL) {
            for (ActiveType active : ActiveType.values()) {
                if (active == ActiveType.ALL)
                    continue;
                getActive(active).put(uuid, uuid2);
            }
            return;
        }
        getActive(type).put(uuid, uuid2);
    }

    @Override
    public boolean containsValue(UUID uuid, ActiveType type) {
        if (type == ActiveType.ALL) {
            for (ActiveType active : ActiveType.values()) {
                if (active == ActiveType.ALL)
                    continue;
                if (getActive(active).containsValue(uuid))
                    return true;
            }
            return false;
        }
        return getActive(type).containsValue(uuid);
    }

    @Override
    public boolean containsKey(UUID uuid, ActiveType type) {
        if (type == ActiveType.ALL) {
            for (ActiveType active : ActiveType.values()) {
                if (active == ActiveType.ALL)
                    continue;
                if (getActive(active).containsKey(uuid))
                    return true;
            }
            return false;
        }
        return getActive(type).containsKey(uuid);
    }

    @Override
    public UUID getKeyByValue(UUID uuid, ActiveType type) {
        UUID key = null;
        if (type == ActiveType.ALL) {
            for (ActiveType active : ActiveType.values()) {
                Map<UUID, UUID> map = getActive(active);
                if (map.containsValue(uuid)) {
                    Optional<UUID> optional = map.keySet().stream().filter(k -> map.get(k).equals(uuid)).findFirst();
                    if (!optional.isPresent())
                        continue;
                    key = optional.get();
                }
            }
        }
        Map<UUID, UUID> map = getActive(type);
        if (map.containsValue(uuid)) {
            Optional<UUID> optional = map.keySet().stream().filter(k -> map.get(k).equals(uuid)).findFirst();
            if (optional.isPresent())
                key = optional.get();
        }
        return key;
    }
}

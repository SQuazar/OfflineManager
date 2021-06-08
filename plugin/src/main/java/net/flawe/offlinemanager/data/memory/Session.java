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
                getActive(active).values().removeIf(value -> value == uuid);
            }
            return;
        }
        getActive(type).values().removeIf(value -> value == uuid);
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
                    Optional<UUID> optional = map.keySet().stream().filter(k -> map.get(k) == uuid).findFirst();
                    if (!optional.isPresent())
                        continue;
                    key = optional.get();
                }
            }
        }
        Map<UUID, UUID> map = getActive(type);
        if (map.containsValue(uuid)) {
            Optional<UUID> optional = map.keySet().stream().filter(k -> map.get(k) == uuid).findFirst();
            if (optional.isPresent())
                key = optional.get();
        }
        return key;
    }
}

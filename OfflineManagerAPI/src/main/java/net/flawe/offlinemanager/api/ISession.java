package net.flawe.offlinemanager.api;

import net.flawe.offlinemanager.api.enums.ActiveType;

import java.util.Map;
import java.util.UUID;

public interface ISession {
    Map<UUID, UUID> getActive(ActiveType type);

    boolean isEmpty(ActiveType type);

    void clear(ActiveType type);

    void removeByKey(UUID uuid, ActiveType type);

    void removeByValue(UUID uuid, ActiveType type);

    void add(UUID uuid, UUID uuid2, ActiveType type);

    boolean containsValue(UUID uuid, ActiveType type);

    boolean containsKey(UUID uuid, ActiveType type);

    UUID getKeyByValue(UUID uuid, ActiveType type);
}

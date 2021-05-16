package net.flawe.offlinemanager.api;

import net.flawe.offlinemanager.api.*;
import net.flawe.offlinemanager.api.command.ICommandManager;
import net.flawe.offlinemanager.api.data.IConfigManager;
import net.flawe.offlinemanager.api.data.INMSManager;
import net.flawe.offlinemanager.api.memory.ISession;
import net.flawe.offlinemanager.api.memory.IStorage;

import java.util.UUID;

public interface OfflineManagerAPI {
    IUser getUser(String username);
    IUser getUser(UUID uuid);
    IConfigManager getConfigManager();
    INMSManager getNMSManager();
    IStorage getStorage();
    ISession getSession();
    ICommandManager getCommandManager();
    String getVersion();
    boolean papi();
    void reload();
}

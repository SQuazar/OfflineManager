package net.flawe.offlinemanager;

import net.flawe.offlinemanager.api.*;

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
}

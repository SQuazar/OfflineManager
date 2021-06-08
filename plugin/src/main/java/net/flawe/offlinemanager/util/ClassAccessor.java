package net.flawe.offlinemanager.util;

import net.flawe.offlinemanager.api.OfflineManagerAPI;
import net.flawe.offlinemanager.api.data.INMSManager;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.InvocationTargetException;

import static net.flawe.offlinemanager.OfflineManager.getServerVersion;

public class ClassAccessor {

    private static final String version = getServerVersion();
    private final OfflineManagerAPI api;

    public ClassAccessor(OfflineManagerAPI api) {
        this.api = api;
    }

    public INMSManager getNMSManager() {
        try {
            Class<?> target = Class.forName("net.flawe.offlinemanager.api.util." + version + ".NMSManager");
            if (!INMSManager.class.isAssignableFrom(target)) {
                return null;
            }
            return (INMSManager) target.getConstructor(OfflineManagerAPI.class).newInstance(api);
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}

package net.flawe.offlinemanager.util;

import net.flawe.offlinemanager.OfflineManager;
import net.flawe.offlinemanager.api.data.INMSManager;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.InvocationTargetException;

import static net.flawe.offlinemanager.OfflineManager.getServerVersion;

public class ClassAccessor {

    private static final String version = getServerVersion();
    private final OfflineManager plugin;

    public ClassAccessor(OfflineManager plugin) {
        this.plugin = plugin;
    }

    public INMSManager getNMSManager() {
        try {
            Class<?> target = Class.forName("net.flawe.offlinemanager.api.util." + version + ".NMSManager");
            if (!INMSManager.class.isAssignableFrom(target)) {
                return null;
            }
            return (INMSManager) target.getConstructor(Plugin.class).newInstance(plugin);
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}

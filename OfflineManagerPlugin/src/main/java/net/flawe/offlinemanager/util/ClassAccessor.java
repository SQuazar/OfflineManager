package net.flawe.offlinemanager.util;

import net.flawe.offlinemanager.OfflineManager;
import net.flawe.offlinemanager.api.INMSManager;
import net.flawe.offlinemanager.api.IUser;
import org.bukkit.Bukkit;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import static net.flawe.offlinemanager.OfflineManager.*;

public class ClassAccessor {

    private static final String version = getServerVersion();
    private static OfflineManager plugin;

    public ClassAccessor(OfflineManager plugin) {
        ClassAccessor.plugin = plugin;
    }

    public IUser getUser(String name) {
        return getObject(IUser.class, "OfflineUser", new Object[]{plugin, name});
    }

    public IUser getUser(UUID uuid) {
        return getObject(IUser.class, "OfflineUser", new Object[]{plugin, uuid});
    }

    public INMSManager getNMSManager() {
        return getObject(INMSManager.class, "NMSManager", new Object[0]);
    }

    private <T> T getObject(Class<? extends T> clazz, String className, Object[] arg) {
        try {
            Class<?> target = Class.forName("net.flawe.offlinemanager.api.util." + version + "." + className);
            if (!clazz.isAssignableFrom(target)) {
                String err = "Class " + target.getName() + " cannot cast to " + clazz.getName();
                plugin.err(err);
                throw new ClassCastException(err);
            }
            if (arg.length == 0)
                return clazz.cast(target.getConstructor().newInstance());
            for (Constructor<?> constructor : target.getConstructors()) {
                Class<?>[] required = constructor.getParameterTypes();
                if (required.length == arg.length) {
                    int i = 0;
                    while (true) {
                        if (i < arg.length) {
                            if (!required[i].isAssignableFrom(arg[i].getClass()))
                                break;
                            i++;
                            continue;
                        }
                        return clazz.cast(constructor.newInstance(arg));
                    }
                }
            }
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

}

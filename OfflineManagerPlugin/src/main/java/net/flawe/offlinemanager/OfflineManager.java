package net.flawe.offlinemanager;

import net.flawe.offlinemanager.api.*;
import net.flawe.offlinemanager.commands.CommandManager;
import net.flawe.offlinemanager.commands.ManagerCommand;
import net.flawe.offlinemanager.expansion.OfflineManagerExpansion;
import net.flawe.offlinemanager.listeners.bukkit.InventoryListener;
import net.flawe.offlinemanager.listeners.bukkit.PlayerListener;
import net.flawe.offlinemanager.listeners.manager.OfflineInventoryListener;
import net.flawe.offlinemanager.listeners.manager.OfflinePlayerListener;
import net.flawe.offlinemanager.util.*;
import net.flawe.offlinemanager.util.configuration.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class OfflineManager extends JavaPlugin implements OfflineManagerAPI {

    private static OfflineManagerAPI api;
    private IConfigManager configManager;
    private ISession session;
    private IStorage storage;
    private ClassAccessor accessor;
    private ICommandManager commandManager;

    @Override
    public void onEnable() {
        OfflineManager.api = this;
        configManager = new ConfigManager(this);
        storage = new Storage(this);
        session = new Session();
        accessor = new ClassAccessor(this);
        commandManager = new CommandManager();
        initFiles();
        storage.init();
        initCommands();
        initEvents();
        if (papi() && configManager.placeholders())
            new OfflineManagerExpansion(this).register();
        info("Enabled!");
    }

    @Override
    public void onDisable() {
        info("Disabled!");
    }

    private void initFiles() {
        configManager.createConfig();
        configManager.createMessages();
    }

    private void initEvents() {
        Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new OfflineInventoryListener(), this);
        Bukkit.getPluginManager().registerEvents(new OfflinePlayerListener(), this);
    }

    private void initCommands() {
        PluginCommand command = Bukkit.getPluginCommand("offlinemanager");
        if (command != null)
            command.setExecutor(new ManagerCommand());
    }

    public static OfflineManagerAPI getApi() {
        return api;
    }

    public static String getServerVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().substring(Bukkit.getServer().getClass().getPackage().getName().lastIndexOf(".") + 1);
    }

    @Override
    public IUser getUser(String username) {
        return accessor.getUser(username);
    }

    @Override
    public IUser getUser(UUID uuid) {
        return accessor.getUser(uuid);
    }

    @Override
    public IConfigManager getConfigManager() {
        return configManager;
    }

    @Override
    public INMSManager getNMSManager() {
        return accessor.getNMSManager();
    }

    @Override
    public IStorage getStorage() {
        return storage;
    }

    @Override
    public ISession getSession() {
        return session;
    }

    @Override
    public ICommandManager getCommandManager() {
        return commandManager;
    }

    @Override
    public boolean papi() {
        return Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
    }

    public void info(String s) {
        getLogger().info(s);
    }

    public void warn(String s) {
        getLogger().warning(s);
    }

    public void err(String s) {
        getLogger().severe(s);
    }

}

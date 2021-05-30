package net.flawe.offlinemanager;

import net.flawe.annotation.ConfigurationLoader;
import net.flawe.offlinemanager.api.OfflineManagerAPI;
import net.flawe.offlinemanager.api.command.ICommandManager;
import net.flawe.offlinemanager.api.data.IConfigManager;
import net.flawe.offlinemanager.api.data.INMSManager;
import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.entity.IUser;
import net.flawe.offlinemanager.api.memory.ISession;
import net.flawe.offlinemanager.api.memory.IStorage;
import net.flawe.offlinemanager.commands.CommandManager;
import net.flawe.offlinemanager.commands.ManagerCommand;
import net.flawe.offlinemanager.configuration.Messages;
import net.flawe.offlinemanager.configuration.Settings;
import net.flawe.offlinemanager.data.memory.Session;
import net.flawe.offlinemanager.data.memory.Storage;
import net.flawe.offlinemanager.expansion.OfflineManagerExpansion;
import net.flawe.offlinemanager.expansion.PAPIHelper;
import net.flawe.offlinemanager.listeners.bukkit.InventoryListener;
import net.flawe.offlinemanager.listeners.bukkit.PlayerListener;
import net.flawe.offlinemanager.listeners.manager.OfflineInventoryListener;
import net.flawe.offlinemanager.listeners.manager.OfflinePlayerListener;
import net.flawe.offlinemanager.util.ClassAccessor;
import net.flawe.offlinemanager.util.configuration.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class OfflineManager extends JavaPlugin implements OfflineManagerAPI {

    private static OfflineManagerAPI api;
    private IConfigManager configManager;
    private ISession session;
    private IStorage storage;
    private ClassAccessor accessor;
    private ICommandManager commandManager;
    private INMSManager nmsManager;
    private PAPIHelper papiHelper;

    private Settings settings;
    private Messages messages;

    @Override
    public void onEnable() {
        preInit();
        init();
        postInit();
        info("Enabled!");
    }

    private void preInit() {
        try {
            Class.forName("net.flawe.offlinemanager.api.util." + getServerVersion() + ".NMSManager");
        } catch (ClassNotFoundException e) {
            err("This version isn't supported!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        OfflineManager.api = this;
        configManager = new ConfigManager(this);
        if (getDataFolder().mkdirs()) {
            info("Plugin directory success created!");
        }
        settings = new Settings();
        messages = new Messages(this);
        ConfigurationLoader.loadConfiguration(settings, getConfig());
        ConfigurationLoader.loadConfiguration(messages, YamlConfiguration.loadConfiguration(new File(getDataFolder(), "messages.yml")));
        try {
            ConfigurationLoader.saveConfiguration(settings, new File(getDataFolder(), "config.yml"));
            ConfigurationLoader.saveConfiguration(messages, new File(getDataFolder(), "messages.yml"));
        } catch (IllegalAccessException | IOException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        storage = new Storage(this);
        session = new Session();
        accessor = new ClassAccessor(this);
        nmsManager = accessor.getNMSManager();
        storage.init();
    }

    private void postInit() {
        commandManager = new CommandManager();
        if (!settings.isOnlyApi()) {
            initCommands();
            initEvents();
        }
        if (papi())
            new OfflineManagerExpansion(this).register();
        papiHelper = new PAPIHelper(this);
    }

    @Override
    public void reload() {
        ConfigurationLoader.loadConfiguration(settings, getConfig());
        ConfigurationLoader.loadConfiguration(messages, YamlConfiguration.loadConfiguration(new File(getDataFolder(), "messages.yml")));
    }

    @Override
    public void onDisable() {
        info("Disabled!");
    }

    public PAPIHelper getPapiHelper() {
        return papiHelper;
    }

    public Settings getSettings() {
        return settings;
    }

    public Messages getMessages() {
        return messages;
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
        return nmsManager.getUser(username);
    }

    @Override
    public IUser getUser(UUID uuid) {
        return nmsManager.getUser(uuid);
    }

    @Override
    public IPlayerData getPlayerData(String username) {
        IPlayerData playerData = storage.getPlayerDataFromCache(Bukkit.getOfflinePlayer(username).getUniqueId());
        if (playerData == null) {
            playerData = nmsManager.getPlayerData(username);
            if (playerData != null)
                storage.addPlayerDataToCache(playerData);
        }
        return playerData;
    }

    @Override
    public IPlayerData getPlayerData(UUID uuid) {
        IPlayerData playerData = storage.getPlayerDataFromCache(uuid);
        if (playerData == null) {
            playerData = nmsManager.getPlayerData(uuid);
            if (playerData != null)
                storage.addPlayerDataToCache(playerData);
        }
        return playerData;
    }

    @Override
    public IConfigManager getConfigManager() {
        return configManager;
    }

    @Override
    public INMSManager getNMSManager() {
        return nmsManager;
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
    public String getVersion() {
        return this.getDescription().getVersion();
    }

    @Override
    public boolean papi() {
        return Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null && settings.placeholders();
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

/*
 * Copyright (c) 2021 squazar
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

package net.quazar.offlinemanager;

import net.quazar.annotation.ConfigurationLoader;
import net.quazar.offlinemanager.api.OfflineManagerAPI;
import net.quazar.offlinemanager.api.addon.Addon;
import net.quazar.offlinemanager.api.addon.AddonType;
import net.quazar.offlinemanager.api.command.ICommandManager;
import net.quazar.offlinemanager.api.data.IConfigManager;
import net.quazar.offlinemanager.api.data.INMSManager;
import net.quazar.offlinemanager.api.data.entity.IPlayerData;
import net.quazar.offlinemanager.api.data.entity.PlayerProfile;
import net.quazar.offlinemanager.api.memory.ISession;
import net.quazar.offlinemanager.api.memory.IStorage;
import net.quazar.offlinemanager.commands.CommandManager;
import net.quazar.offlinemanager.commands.ManagerCommand;
import net.quazar.offlinemanager.configuration.Messages;
import net.quazar.offlinemanager.configuration.Settings;
import net.quazar.offlinemanager.data.memory.Session;
import net.quazar.offlinemanager.data.memory.Storage;
import net.quazar.offlinemanager.expansion.OfflineManagerExpansion;
import net.quazar.offlinemanager.expansion.PAPIHelper;
import net.quazar.offlinemanager.listeners.bukkit.InventoryListener;
import net.quazar.offlinemanager.listeners.bukkit.PlayerListener;
import net.quazar.offlinemanager.listeners.manager.OfflineInventoryListener;
import net.quazar.offlinemanager.listeners.manager.OfflinePlayerListener;
import net.quazar.offlinemanager.util.ClassAccessor;
import net.quazar.offlinemanager.util.configuration.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class OfflineManager extends JavaPlugin implements OfflineManagerAPI {

    private static OfflineManagerAPI api;
    private IConfigManager configManager;
    private ISession session;
    private IStorage storage;
    private ICommandManager commandManager;
    private INMSManager nmsManager;
    private PAPIHelper papiHelper;

    private Settings settings;
    private Messages messages;

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private final Set<Addon> addons = new HashSet<>();

    @Override
    public void onEnable() {
        preInit();
        init();
        postInit();
        info("Enabled!");
    }

    private void preInit() {
        try {
            Class.forName("net.quazar.offlinemanager.api.util." + getServerVersion() + ".NMSManager");
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
        ClassAccessor accessor = new ClassAccessor(this);
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
/*        Menus menus = new Menus(this);
        if (menus.getConfiguration().enabled()) {
            menus.load();
            addons.add(menus);
        }*/
    }

    @Override
    public void reload() {
        ConfigurationLoader.loadConfiguration(settings, getConfig());
        ConfigurationLoader.loadConfiguration(messages, YamlConfiguration.loadConfiguration(new File(getDataFolder(), "messages.yml")));
    }

    @Override
    public void onDisable() {
        storage.getPlayerDataCache().asMap().clear();
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
    public synchronized IPlayerData getPlayerData(String username) {
        PlayerProfile profile = Objects.requireNonNull(storage.getPlayerProfile(username),
                String.format("Cannot load player profile with name %s.", username));
        IPlayerData playerData = storage.getPlayerDataFromCache(profile.getUuid());
        if (playerData == null) {
            playerData = nmsManager.getPlayerData(PlayerProfile.of(profile.getUuid(), username));
            if (playerData != null)
                storage.addPlayerDataToCache(playerData);
        }
        return playerData;
    }

    @Override
    public synchronized IPlayerData getPlayerData(UUID uuid) {
        PlayerProfile profile = Objects.requireNonNull(storage.getPlayerProfile(uuid),
                String.format("Cannot load player profile with uuid %s", uuid.toString()));
        IPlayerData playerData = storage.getPlayerDataFromCache(uuid);
        if (playerData == null) {
            playerData = nmsManager.getPlayerData(profile);
            if (playerData != null)
                storage.addPlayerDataToCache(playerData);
        }
        return playerData;
    }

    @Override
    public synchronized IPlayerData getPlayerData(PlayerProfile profile) {
        IPlayerData playerData = storage.getPlayerDataFromCache(profile.getUuid());
        if (playerData == null) {
            playerData = nmsManager.getPlayerData(profile);
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
    public Addon getAddon(AddonType type) {
        for (Addon addon : addons)
            if (addon.getType() == type)
                return addon;
        return null;
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

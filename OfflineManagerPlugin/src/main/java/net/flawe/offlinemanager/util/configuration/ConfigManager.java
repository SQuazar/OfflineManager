package net.flawe.offlinemanager.util.configuration;

import me.clip.placeholderapi.PlaceholderAPI;
import net.flawe.offlinemanager.OfflineManager;
import net.flawe.offlinemanager.api.IConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class ConfigManager implements IConfigManager {

    private final FileConfiguration config;
    private final File configFile;
    private final File messagesFile;
    private FileConfiguration messages;

    private boolean onlyApi;
    private boolean placeholders;
    private boolean playerComplete;
    private boolean commandComplete;
    private boolean loaded;

    private IInventoryConfig inventoryConfig;
    private IArmorInventoryConfig armorInventoryConfig;
    private IEnderChestConfig enderChestConfig;
    private ICommandGameModeConfig gameModeConfig;
    private ICommandTeleportConfig teleportConfig;
    private ICommandChangeLocationConfig changeLocationConfig;
    private ICommandKillConfig killConfig;
    private ICommandClearInventoryConfig clearInventoryConfig;
    private ICommandHealConfig healConfig;
    private ICommandFeedConfig feedConfig;

    private final OfflineManager plugin;

    public ConfigManager(OfflineManager plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
        this.configFile = new File(plugin.getDataFolder(), "config.yml");
        this.messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        if (!messagesFile.exists())
            return;
        messages = YamlConfiguration.loadConfiguration(messagesFile);
        if (configFile.exists())
            initConfigurations();
    }

    private void initConfigurations() {
        inventoryConfig = new InventoryConfig(plugin);
        armorInventoryConfig = new ArmorInventoryConfig(plugin);
        enderChestConfig = new EnderChestConfig(plugin);

        gameModeConfig = new CommandConfig.ChangeGamemode(plugin);
        teleportConfig = new CommandConfig.Teleport(plugin);
        changeLocationConfig = new CommandConfig.ChangeLocation(plugin);
        killConfig = new CommandConfig.Kill(plugin);
        clearInventoryConfig = new CommandConfig.ClearInventory(plugin);
        healConfig = new CommandConfig.HealPlayer(plugin);
        feedConfig = new CommandConfig.FeedPlayer(plugin);

        inventoryConfig.load();
        armorInventoryConfig.load();
        enderChestConfig.load();

        gameModeConfig.load();
        teleportConfig.load();
        changeLocationConfig.load();
        killConfig.load();
        clearInventoryConfig.load();
        healConfig.load();
        feedConfig.load();
    }

    @Override
    public FileConfiguration getConfig() {
        return config;
    }

    @Override
    public FileConfiguration getMessages() {
        return messages;
    }

    @Override
    public IInventoryConfig getInventoryConfig() {
        return inventoryConfig;
    }

    @Override
    public IArmorInventoryConfig getArmorInventoryConfig() {
        return armorInventoryConfig;
    }

    @Override
    public IEnderChestConfig getEnderChestConfig() {
        return enderChestConfig;
    }

    @Override
    public ICommandGameModeConfig getCommandGameModeConfig() {
        return gameModeConfig;
    }

    @Override
    public ICommandTeleportConfig getCommandTeleportConfig() {
        return teleportConfig;
    }

    @Override
    public ICommandChangeLocationConfig getCommandChangeLocationConfig() {
        return changeLocationConfig;
    }

    @Override
    public ICommandKillConfig getCommandKillConfig() {
        return killConfig;
    }

    @Override
    public ICommandClearInventoryConfig getCommandClearInventoryConfig() {
        return clearInventoryConfig;
    }

    @Override
    public ICommandHealConfig getCommandHealConfig() {
        return healConfig;
    }

    @Override
    public ICommandFeedConfig getCommandFeedConfig() {
        return feedConfig;
    }

    @Override
    public void createConfig() {
        if (configFile.exists())
            return;
        plugin.info("Configuration file not found! Creating new file...");
        config.options().copyDefaults(true);
        plugin.saveDefaultConfig();
        plugin.info("Configuration file success created!");
    }

    @Override
    public void createMessages() {
        if (messagesFile.exists())
            return;
        plugin.info("Messages file not found! Creating new file...");
        messages = YamlConfiguration.loadConfiguration(messagesFile);
        messages.options().copyDefaults(true);
        plugin.saveResource("messages.yml", true);
        plugin.info("Messages file success created!");
    }

    @Override
    public String getMessageString(String s) {
        String msg = messages.getString(s);
        if (msg == null)
            throw new NullPointerException("The message " + s + " not found in messages configuration file!");
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    @Override
    public String getMessageString(Player player, String s) {
        String msg = getMessageString(s);
        if (plugin.papi())
            msg = PlaceholderAPI.setPlaceholders(player, msg);
        return msg;
    }


    @Override
    public void setMessageString(String s, String s2) {
        messages.set(s, s2);
    }

    @Override
    public void reloadConfig() {
        plugin.reloadConfig();
        plugin.info("Configuration file success reloaded!");
        loaded = false;
        load();
        initConfigurations();
    }

    @Override
    public void reloadMessages() {
        messages = YamlConfiguration.loadConfiguration(messagesFile);
        plugin.info("Messages file success reloaded!");
    }

    @Override
    public void load() {
        onlyApi = onlyApi();
        placeholders = placeholders();
        playerComplete = playerComplete();
        commandComplete = commandComplete();
        loaded = true;
    }

    /**
     * Not supported in beta-0.1
     */
    @Override
    public void update() {
        throw new UnsupportedOperationException("This method not supported in beta-0.1!");
    }

    @Override
    public boolean onlyApi() {
        if (loaded)
            return onlyApi;
        return config.getBoolean("only-api");
    }

    @Override
    public boolean placeholders() {
        if (loaded)
            return placeholders;
        return config.getBoolean("placeholders");
    }

    @Override
    public boolean playerComplete() {
        if (loaded)
            return playerComplete;
        return config.getBoolean("player-complete");
    }

    @Override
    public boolean commandComplete() {
        if (loaded)
            return commandComplete;
        return config.getBoolean("command-complete");
    }

    public static class InventoryConfig implements IInventoryConfig {

        private final ConfigurationSection section;

        private boolean enabled;
        private boolean interact;
        private boolean loaded;
        private String name;

        private InventoryConfig(OfflineManager plugin) {
            section = plugin.getConfig().getConfigurationSection("offline-inventory");
        }

        @Override
        public boolean enabled() {
            if (loaded)
                return enabled;
            return section != null && section.getBoolean("enable");
        }

        @Override
        public boolean interact() {
            if (loaded)
                return interact;
            return section != null && section.getBoolean("interact");
        }

        @Override
        public void load() {
            enabled = enabled();
            interact = interact();
            name = getName();
            loaded = true;
        }

        @Override
        public String getName() {
            if (loaded)
                return name;
            return section == null ? "OfflineInventory" : section.getString("name");
        }
    }

    public static class ArmorInventoryConfig implements IArmorInventoryConfig {

        private final ConfigurationSection section;
        private boolean enabled;
        private boolean interact;
        private boolean loaded;
        private String name;

        private ArmorInventoryConfig(OfflineManager plugin) {
            section = plugin.getConfig().getConfigurationSection("offline-armor");
        }

        @Override
        public boolean enabled() {
            if (loaded)
                return enabled;
            return section != null && section.getBoolean("enable");
        }

        @Override
        public boolean interact() {
            if (loaded)
                return interact;
            return section != null && section.getBoolean("interact");
        }

        @Override
        public void load() {
            enabled = enabled();
            interact = interact();
            name = getName();
            loaded = true;
        }

        @Override
        public String getName() {
            if (loaded)
                return name;
            return section == null ? "ArmorInventory" : section.getString("name");
        }

    }

    public static class EnderChestConfig implements IEnderChestConfig {

        private final ConfigurationSection section;
        private boolean enabled;
        private boolean interact;
        private boolean loaded;
        private String name;

        private EnderChestConfig(OfflineManager plugin) {
            section = plugin.getConfig().getConfigurationSection("offline-enderchest");
        }

        @Override
        public boolean enabled() {
            if (loaded)
                return enabled;
            return section != null && section.getBoolean("enable");
        }

        @Override
        public boolean interact() {
            if (loaded)
                return interact;
            return section != null && section.getBoolean("interact");
        }

        @Override
        public void load() {
            enabled = enabled();
            interact = interact();
            name = getName();
            loaded = true;
        }

        @Override
        public String getName() {
            if (loaded)
                return name;
            return section == null ? "EnderChest" : section.getString("name");
        }

    }

    public static class CommandConfig {
        public static class ChangeGamemode implements ICommandGameModeConfig {

            private final ConfigurationSection section;
            private boolean enabled;
            private boolean loaded;

            private ChangeGamemode(OfflineManager plugin) {
                section = plugin.getConfig().getConfigurationSection("change-gamemode");
            }

            @Override
            public void load() {
                enabled = enabled();
                loaded = true;
            }

            @Override
            public boolean enabled() {
                if (loaded)
                    return enabled;
                return section != null && section.getBoolean("enable");
            }

        }

        public static class Teleport implements ICommandTeleportConfig {

            private final ConfigurationSection section;
            private boolean enabled;
            private boolean loaded;

            private Teleport(OfflineManager plugin) {
                section = plugin.getConfig().getConfigurationSection("teleportation");
            }

            @Override
            public void load() {
                enabled = enabled();
                loaded = true;
            }

            @Override
            public boolean enabled() {
                if (loaded)
                    return enabled;
                return section != null && section.getBoolean("enable");
            }

        }

        public static class ChangeLocation implements ICommandChangeLocationConfig {

            private final ConfigurationSection section;
            private boolean enabled;
            private boolean loaded;

            private ChangeLocation(OfflineManager plugin) {
                section = plugin.getConfig().getConfigurationSection("edit-location");
            }

            @Override
            public void load() {
                enabled = enabled();
                loaded = true;
            }

            @Override
            public boolean enabled() {
                if (loaded)
                    return enabled;
                return section != null && section.getBoolean("enable");
            }

        }

        public static class Kill implements ICommandKillConfig {

            private final ConfigurationSection section;
            private boolean enabled;
            private boolean clear;
            private boolean drop;
            private boolean loaded;

            private Kill(OfflineManager plugin) {
                section = plugin.getConfig().getConfigurationSection("kill-player");
            }

            @Override
            public void load() {
                enabled = enabled();
                clear = clearItems();
                drop = dropItems();
                loaded = true;
            }

            @Override
            public boolean enabled() {
                if (loaded)
                    return enabled;
                return section != null && section.getBoolean("enable");
            }

            @Override
            public boolean clearItems() {
                if (loaded)
                    return clear;
                return section != null && section.getBoolean("clear-items");
            }

            @Override
            public boolean dropItems() {
                if (loaded)
                    return drop;
                return section != null && section.getBoolean("drop-items");
            }
        }

        public static class ClearInventory implements ICommandClearInventoryConfig {

            private final ConfigurationSection section;
            private boolean enabled;
            private boolean loaded;

            private ClearInventory(OfflineManager plugin) {
                section = plugin.getConfig().getConfigurationSection("clear-inventory");
            }

            @Override
            public void load() {
                enabled = enabled();
                loaded = true;
            }

            @Override
            public boolean enabled() {
                if (loaded)
                    return enabled;
                return section != null && section.getBoolean("enable");
            }

        }

        public static class HealPlayer implements ICommandHealConfig {

            private final ConfigurationSection section;
            private boolean enabled;
            private boolean loaded;

            private HealPlayer(OfflineManager plugin) {
                section = plugin.getConfig().getConfigurationSection("heal-player");
            }

            @Override
            public void load() {
                enabled = enabled();
                loaded = true;
            }

            @Override
            public boolean enabled() {
                if (loaded)
                    return enabled;
                return section != null && section.getBoolean("enable");
            }

        }

        public static class FeedPlayer implements ICommandFeedConfig {

            private final ConfigurationSection section;
            private boolean enabled;
            private boolean loaded;

            private FeedPlayer(OfflineManager plugin) {
                section = plugin.getConfig().getConfigurationSection("feed-player");
            }

            @Override
            public void load() {
                enabled = enabled();
                loaded = true;
            }

            @Override
            public boolean enabled() {
                if (loaded)
                    return enabled;
                return section != null && section.getBoolean("enable");
            }

        }
    }
}

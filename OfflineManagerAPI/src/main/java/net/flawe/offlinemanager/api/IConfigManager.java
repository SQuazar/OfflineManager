package net.flawe.offlinemanager.api;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public interface IConfigManager {

    FileConfiguration getConfig();

    FileConfiguration getMessages();

    IInventoryConfig getInventoryConfig();

    IArmorInventoryConfig getArmorInventoryConfig();

    IEnderChestConfig getEnderChestConfig();

    ICommandGameModeConfig getCommandGameModeConfig();

    ICommandTeleportConfig getCommandTeleportConfig();

    ICommandChangeLocationConfig getCommandChangeLocationConfig();

    ICommandKillConfig getCommandKillConfig();

    ICommandClearInventoryConfig getCommandClearInventoryConfig();

    ICommandHealConfig getCommandHealConfig();

    ICommandFeedConfig getCommandFeedConfig();

    void createConfig();

    void createMessages();

    void setMessageString(String s, String s2);

    void reloadConfig();

    void reloadMessages();

    void load();

    void update();

    boolean onlyApi();

    boolean placeholders();

    boolean playerComplete();

    boolean commandComplete();

    String getMessageString(String s);

    String getMessageString(Player player, String s);

    interface IContainerConfig {
        boolean enabled();

        boolean interact();

        void load();

        String getName();
    }

    interface IInventoryConfig extends IContainerConfig {
    }

    interface IArmorInventoryConfig extends IContainerConfig {
    }

    interface IEnderChestConfig extends IContainerConfig {
    }

    interface ICommandConfig {
        void load();

        boolean enabled();
    }

    interface ICommandGameModeConfig extends ICommandConfig {
    }

    interface ICommandTeleportConfig extends ICommandConfig {
    }

    interface ICommandChangeLocationConfig extends ICommandConfig {
    }

    interface ICommandKillConfig extends ICommandConfig {
        boolean clearItems();

        boolean dropItems();
    }

    interface ICommandClearInventoryConfig extends ICommandConfig {
    }

    interface ICommandHealConfig extends ICommandConfig {
    }

    interface ICommandFeedConfig extends ICommandConfig {
    }

}

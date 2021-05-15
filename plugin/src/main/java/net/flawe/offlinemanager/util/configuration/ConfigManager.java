package net.flawe.offlinemanager.util.configuration;

import me.clip.placeholderapi.PlaceholderAPI;
import net.flawe.offlinemanager.OfflineManager;
import net.flawe.offlinemanager.api.data.IConfigManager;
import net.flawe.offlinemanager.util.ColorUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ConfigManager implements IConfigManager {

    private OfflineManager plugin;

    public ConfigManager(OfflineManager plugin) {
        this.plugin = plugin;
    }

    @Deprecated
    @Override
    public String getMessage(Player player, String s) {
        String msg = ChatColor.translateAlternateColorCodes('&', s);
        if (plugin.papi())
            msg = PlaceholderAPI.setPlaceholders(player, msg);
        return msg;
    }

    @Override
    public void reloadConfig() {
        plugin.reloadConfig();
        plugin.reload();
    }

    @Override
    public String fillMessage(Player player, String s) {
        return ColorUtils.getFormattedString(plugin.getPapiHelper().fillMessage(player, s));
    }

    @Deprecated
    @Override
    public String getMessage(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    /**
     * Not supported
     */
    @Override
    public void update() {
        throw new UnsupportedOperationException("This method not supported in this version!");
    }

}

package net.flawe.offlinemanager.util;

import org.bukkit.ChatColor;

public class ColorUtils {

    public static String getFormattedString(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

}

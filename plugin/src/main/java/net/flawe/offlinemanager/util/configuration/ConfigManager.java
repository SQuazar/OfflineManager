/*
 * Copyright (c) 2021 flaweoff
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

package net.flawe.offlinemanager.util.configuration;

import me.clip.placeholderapi.PlaceholderAPI;
import net.flawe.offlinemanager.OfflineManager;
import net.flawe.offlinemanager.api.data.IConfigManager;
import net.flawe.offlinemanager.util.ColorUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ConfigManager implements IConfigManager {

    private final OfflineManager plugin;

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

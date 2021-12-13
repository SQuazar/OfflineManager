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

package net.flawe.offlinemanager.addon.menus.configuration;

import net.flawe.offlinemanager.addon.menus.Menus;
import net.flawe.offlinemanager.addon.menus.menu.MenuItem;
import net.flawe.offlinemanager.api.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuConfiguration implements Configuration {

    private final FileConfiguration fileConfiguration;
    private final Map<Integer, MenuItem> items = new HashMap<>();
    private Inventory inventory;
    private int size;
    private String title;
    private String permission;
    private String name;
    private List<String> openCommand;
    private final Menus menus;

    public MenuConfiguration(Menus menus, FileConfiguration fileConfiguration, String name) {
        this.menus = menus;
        this.fileConfiguration = fileConfiguration;
        this.name = name;
    }

    public void load() {
        size = fileConfiguration.getInt("size");
        title = fileConfiguration.getString("title");
        permission = fileConfiguration.getString("permission");
        openCommand = fileConfiguration.getStringList("open-command");
    }

    public String getName() {
        return name;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getSize() {
        return size;
    }

    public String getTitle() {
        return title;
    }

    public String getPermission() {
        return permission;
    }

    public List<String> getOpenCommand() {
        return openCommand;
    }

    public Map<Integer, MenuItem> getItems() {
        return items;
    }
}

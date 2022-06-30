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

package net.quazar.offlinemanager.addon.menus;

import net.quazar.offlinemanager.addon.menus.configuration.MenuConfiguration;
import net.quazar.offlinemanager.addon.menus.configuration.MenusConfiguration;
import net.quazar.offlinemanager.addon.menus.menu.Menu;
import net.quazar.offlinemanager.api.OfflineManagerAPI;
import net.quazar.offlinemanager.api.addon.Addon;
import net.quazar.offlinemanager.api.addon.AddonType;
import net.quazar.offlinemanager.api.configuration.AddonConfiguration;
import net.quazar.offlinemanager.api.data.entity.IPlayerData;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class Menus implements Addon {

    private final OfflineManagerAPI api;
    private final Set<MenuConfiguration> menuConfigurations = new HashSet<>();

    private AddonConfiguration configuration;

    public Menus(OfflineManagerAPI api) {
        this.api = api;
    }

    @Override
    public AddonConfiguration getConfiguration() {
        if (configuration == null)
            return configuration = new MenusConfiguration((Plugin) api);
        return configuration;
    }

    public Menu createMenu(String menu, IPlayerData playerData) {
        for (MenuConfiguration configuration : menuConfigurations)
            if (configuration.getName().equals(menu)) return new Menu(configuration, playerData);
        return null;
    }

    public MenuConfiguration getMenuConfiguration(String name) {
        for (MenuConfiguration configuration : menuConfigurations)
            if (configuration.getName().equals(name)) return configuration;
        return null;
    }

    @Override
    public AddonType getType() {
        return AddonType.MENUS;
    }

    @Override
    public void load() {
        ((Plugin) api).saveResource("menus/config.yml", false);
        ((Plugin) api).saveResource("menus/menu/example.yml", false);
        File menuDir = new File(((Plugin) api).getDataFolder(), "menus/menu");
        try (Stream<Path> stream = Files.list(menuDir.toPath())) {
            stream.forEach(path -> {
                MenuConfiguration configuration = new MenuConfiguration(this, YamlConfiguration.loadConfiguration(path.toFile()), path.toFile().getName());
                configuration.load();
                menuConfigurations.add(configuration);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reload() {
        menuConfigurations.clear();
        load();
    }
}

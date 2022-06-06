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

package net.flawe.offlinemanager.api;

import net.flawe.offlinemanager.api.addon.Addon;
import net.flawe.offlinemanager.api.addon.AddonType;
import net.flawe.offlinemanager.api.command.ICommandManager;
import net.flawe.offlinemanager.api.data.IConfigManager;
import net.flawe.offlinemanager.api.data.INMSManager;
import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.data.entity.PlayerProfile;
import net.flawe.offlinemanager.api.memory.ISession;
import net.flawe.offlinemanager.api.memory.IStorage;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * API class for interact with plugin functions
 * @author flawe
 */
public interface OfflineManagerAPI {

    /**
     * Gets the offline player data
     * @param username player name
     * @return data of player
     */
    IPlayerData getPlayerData(String username);

    /**
     * Gets the offline player data
     * @param uuid player uuid
     * @return data of player
     */
    IPlayerData getPlayerData(UUID uuid);

    /**
     * Gets the offline player data
     * @param profile player profile
     * @return data of player
     */
    IPlayerData getPlayerData(PlayerProfile profile);

    /**
     * Gets the plugin configuration manager
     * @return configuration manager
     */
    IConfigManager getConfigManager();

    /**
     * Gets class-helper for getting PlayerData and other classes whose work depend NMS
     * @return NMSManager based on server version
     */
    INMSManager getNMSManager();

    /**
     * Gets the plugin storage (nicknames, player data cache)
     * @return storage instance from plugin class
     */
    IStorage getStorage();

    /**
     * Gets the class-helper for control inventory access
     * @return storage instance from plugin class
     */
    ISession getSession();

    /**
     * Gets the plugin command manager. You can add your own commands
     * @return command manager
     */
    ICommandManager getCommandManager();

    /**
     * Gets the your server version
     * @return server version
     */
    String getVersion();

    /**
     * Test method
     * Not used in current version
     * @param type addon type
     * @return registered addon with current type
     */
    @Nullable
    Addon getAddon(AddonType type);

    /**
     * Checks for PlaceholderAPI in the plugins folder
     * @return checks result
     */
    boolean papi();

    /**
     * Reload the plugin
     */
    void reload();
}

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

package net.flawe.offlinemanager.listeners.bukkit;

import net.flawe.offlinemanager.OfflineManager;
import net.flawe.offlinemanager.api.OfflineManagerAPI;
import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.memory.ISession;
import net.flawe.offlinemanager.api.memory.IStorage;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class PlayerListener implements Listener {

    private final IStorage storage = OfflineManager.getApi().getStorage();
    private final ISession session = OfflineManager.getApi().getSession();
    private final OfflineManagerAPI api = OfflineManager.getApi();

    @EventHandler
    public void onPreJoin(AsyncPlayerPreLoginEvent e) {
        IPlayerData data = storage.getPlayerDataFromCache(e.getUniqueId());
        if (data != null)
            Bukkit.getScheduler().runTask((Plugin) api, (Runnable) data::save);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (storage.hasPlayer(e.getPlayer().getName()))
            storage.remove(e.getPlayer().getName());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if (!storage.hasPlayer(e.getPlayer().getName()))
            storage.add(e.getPlayer().getName());
        UUID player = e.getPlayer().getUniqueId();
        IPlayerData data = storage.getPlayerDataFromCache(player);
        if (data != null)
            data.setOnline(false);
    }

}

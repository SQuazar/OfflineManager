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

package net.flawe.offlinemanager.listeners.manager;

import net.flawe.offlinemanager.OfflineManager;
import net.flawe.offlinemanager.api.OfflineManagerAPI;
import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.enums.ActiveType;
import net.flawe.offlinemanager.api.enums.InventoryType;
import net.flawe.offlinemanager.api.enums.SavePlayerType;
import net.flawe.offlinemanager.api.event.inventory.CloseOfflineInventoryEvent;
import net.flawe.offlinemanager.api.event.inventory.OfflineInventoryClickEvent;
import net.flawe.offlinemanager.api.event.inventory.OfflineInventoryInteractEvent;
import net.flawe.offlinemanager.api.event.inventory.OpenOfflineInventoryEvent;
import net.flawe.offlinemanager.api.inventory.AbstractPlayerInventory;
import net.flawe.offlinemanager.configuration.Settings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class OfflineInventoryListener implements Listener {

    private final OfflineManagerAPI api = OfflineManager.getApi();
    private final Settings settings = ((OfflineManager) api).getSettings();

    @EventHandler
    public void onClose(CloseOfflineInventoryEvent e) {
        IPlayerData playerData = e.getPlayerData();
        switch (e.getInventoryType()) {
            case ARMOR:
                api.getSession().removeByValue(playerData.getPlayerProfile().getUuid(), ActiveType.ARMOR_INVENTORY);
                break;
            case ENDER_CHEST:
                api.getSession().removeByValue(playerData.getPlayerProfile().getUuid(), ActiveType.ENDER_CHEST);
                break;
            case DEFAULT:
                api.getSession().removeByValue(playerData.getPlayerProfile().getUuid(), ActiveType.INVENTORY);
                break;
        }
        Player player = Bukkit.getPlayer(e.getPlayerData().getPlayerProfile().getUuid());
        if (player == null)
            e.getPlayerData().save(SavePlayerType.DEFAULT);
    }

    @EventHandler
    public void onOpen(OpenOfflineInventoryEvent e) {

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClick(OfflineInventoryClickEvent e) {
        if (e.getInventoryType() == InventoryType.ARMOR) {
            if (e.getSlot() > 4)
                e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(OfflineInventoryInteractEvent e) {
        Player player;
        switch (e.getInventoryType()) {
            case DEFAULT:
                if (!settings.getInventoryConfiguration().interact()) {
                    e.setCancelled(true);
                    break;
                }
                player = Bukkit.getPlayer(e.getPlayerData().getPlayerProfile().getUuid());
                if (player == null) {
                    Bukkit.getScheduler().runTask((Plugin) api, () -> e.getPlayerData().getInventory().setStorageContents(e.getInventory().getContents()));
                    break;
                }
                Bukkit.getScheduler().runTask((Plugin) api, () -> player.getInventory().setStorageContents(e.getInventory().getContents()));
                break;
            case ARMOR:
                if (!settings.getArmorInventoryConfiguration().interact()) {
                    e.setCancelled(true);
                    break;
                }
                player = Bukkit.getPlayer(e.getPlayerData().getPlayerProfile().getUuid());
                if (player == null) {
                    AbstractPlayerInventory inventory = e.getPlayerData().getInventory();
                    Bukkit.getScheduler().runTask((Plugin) api, () -> {
                        inventory.setBoots(e.getInventory().getItem(0));
                        inventory.setLeggings(e.getInventory().getItem(1));
                        inventory.setChestplate(e.getInventory().getItem(2));
                        inventory.setHelmet(e.getInventory().getItem(3));
                        inventory.setItemInOffHand(e.getInventory().getItem(4));
                    });
                    break;
                }
                Bukkit.getScheduler().runTask((Plugin) api, () -> {
                    player.getInventory().setBoots(e.getInventory().getItem(0));
                    player.getInventory().setLeggings(e.getInventory().getItem(1));
                    player.getInventory().setChestplate(e.getInventory().getItem(2));
                    player.getInventory().setHelmet(e.getInventory().getItem(3));
                    player.getInventory().setItemInOffHand(e.getInventory().getItem(4));
                });
                break;
            case ENDER_CHEST:
                if (!settings.getEnderChestConfiguration().interact()) {
                    e.setCancelled(true);
                    break;
                }
                player = Bukkit.getPlayer(e.getPlayerData().getPlayerProfile().getUuid());
                if (player == null) {
                    Bukkit.getScheduler().runTask((Plugin) api, () -> e.getPlayerData().getEnderChest().getEnderChest().setContents(e.getInventory().getContents()));
                    break;
                }
                Bukkit.getScheduler().runTask((Plugin) api, () -> player.getEnderChest().setContents(e.getInventory().getContents()));
                break;
        }
    }

}

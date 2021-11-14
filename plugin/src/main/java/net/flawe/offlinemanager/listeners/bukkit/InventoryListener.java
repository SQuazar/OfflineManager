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
import net.flawe.offlinemanager.api.enums.ActiveType;
import net.flawe.offlinemanager.api.enums.InventoryType;
import net.flawe.offlinemanager.api.event.inventory.CloseOfflineInventoryEvent;
import net.flawe.offlinemanager.api.event.inventory.OfflineInventoryClickEvent;
import net.flawe.offlinemanager.api.event.inventory.OfflineInventoryInteractEvent;
import net.flawe.offlinemanager.api.inventory.holder.IOfflineInvHolder;
import net.flawe.offlinemanager.api.inventory.view.OfflineInventoryView;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

public class InventoryListener implements Listener {

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
/*        if (e.getPlayer().getOpenInventory() instanceof OfflineInventoryView) {
            OfflineInventoryView view = (OfflineInventoryView) e.getPlayer().getOpenInventory();
            IPlayerData playerData = view.getPlayerData();
            CloseOfflineInventoryEvent event = new CloseOfflineInventoryEvent((Player) e.getPlayer(), playerData, e.getInventory(), InventoryType.DEFAULT);
            Bukkit.getPluginManager().callEvent(event);
            return;
        }*/
        if (e.getInventory().getHolder() == null)
            return;
        if (e.getInventory().getHolder() instanceof IOfflineInvHolder) {
            IOfflineInvHolder holder = (IOfflineInvHolder) e.getInventory().getHolder();
            IPlayerData playerData = holder.getPlayerData();
            CloseOfflineInventoryEvent event = new CloseOfflineInventoryEvent((Player) e.getPlayer(), playerData, e.getInventory(), holder.getInventoryType());
            Bukkit.getPluginManager().callEvent(event);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getClickedInventory() != null) {
/*            if (e.getWhoClicked().getOpenInventory() instanceof OfflineInventoryView) {
                if (e.getClickedInventory().getHolder() instanceof Player) return;
                OfflineInventoryView view = (OfflineInventoryView) e.getWhoClicked().getOpenInventory();
                IPlayerData playerData = view.getPlayerData();
                OfflineInventoryClickEvent event = new OfflineInventoryClickEvent((Player) e.getWhoClicked(), playerData,
                        InventoryType.DEFAULT, e.getInventory(), e.getSlot());
                Bukkit.getPluginManager().callEvent(event);
                if (event.isCancelled()) {
                    e.setCancelled(true);
                    return;
                }
                onInteract(e);
                return;
            }*/
            if (e.getClickedInventory().getHolder() != null && e.getClickedInventory().getHolder() instanceof IOfflineInvHolder) {
                IOfflineInvHolder holder = (IOfflineInvHolder) e.getClickedInventory().getHolder();
                IPlayerData playerData = holder.getPlayerData();
                OfflineInventoryClickEvent event = new OfflineInventoryClickEvent((Player) e.getWhoClicked(), playerData,
                        holder.getInventoryType(), e.getInventory(), e.getSlot());
                Bukkit.getPluginManager().callEvent(event);
                if (event.isCancelled()) {
                    e.setCancelled(true);
                    return;
                }
            }
        }
        onInteract(e);
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        onInteract(e);
    }

    private final OfflineManagerAPI api = OfflineManager.getApi();

    private void onInteract(InventoryInteractEvent e) {
        Player player = (Player) e.getWhoClicked();
        Player viewer;
        if (api.getSession().containsValue(player.getUniqueId(), ActiveType.INVENTORY)) {
            viewer = Bukkit.getPlayer(api.getSession().getKeyByValue(player.getUniqueId(), ActiveType.INVENTORY));
            if (viewer != null) {
                Inventory inventory = viewer.getOpenInventory().getTopInventory();
                Bukkit.getScheduler().runTask((Plugin) api, () -> inventory.setContents(player.getInventory().getStorageContents()));
            }
        }
        if (api.getSession().containsValue(player.getUniqueId(), ActiveType.ENDER_CHEST)) {
            viewer = Bukkit.getPlayer(api.getSession().getKeyByValue(player.getUniqueId(), ActiveType.ENDER_CHEST));
            if (viewer != null) {
                Inventory inventory = viewer.getOpenInventory().getTopInventory();
                Bukkit.getScheduler().runTask((Plugin) api, () -> inventory.setContents(player.getEnderChest().getContents()));
            }
        }
        if (api.getSession().containsValue(player.getUniqueId(), ActiveType.ARMOR_INVENTORY)) {
            viewer = Bukkit.getPlayer(api.getSession().getKeyByValue(player.getUniqueId(), ActiveType.ARMOR_INVENTORY));
            if (viewer != null) {
                Inventory inventory = viewer.getOpenInventory().getTopInventory();
                Bukkit.getScheduler().runTask((Plugin) api, () -> {
                    for (int i = 0; i < 4; i++)
                        inventory.setItem(i, player.getInventory().getArmorContents()[i]);
                    inventory.setItem(4, player.getInventory().getItemInOffHand());
                });
            }
        }
/*        if (e.getWhoClicked().getOpenInventory() instanceof OfflineInventoryView) {
            if (e.getInventory().getHolder() != null && e.getInventory().getHolder() instanceof Player) return;
            OfflineInventoryView view = (OfflineInventoryView) e.getWhoClicked().getOpenInventory();
            OfflineInventoryInteractEvent event = new OfflineInventoryInteractEvent((Player) e.getWhoClicked(), view.getPlayerData(),
                    InventoryType.DEFAULT, e.getInventory());
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled())
                e.setCancelled(true);
            return;
        }*/
        if (e.getInventory().getHolder() == null)
            return;
        if (e.getInventory().getHolder() instanceof IOfflineInvHolder) {
            IOfflineInvHolder holder = (IOfflineInvHolder) e.getInventory().getHolder();
            OfflineInventoryInteractEvent event = new OfflineInventoryInteractEvent((Player) e.getWhoClicked(), holder.getPlayerData(),
                    holder.getInventoryType(), e.getInventory());
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled())
                e.setCancelled(true);
        }
    }

}

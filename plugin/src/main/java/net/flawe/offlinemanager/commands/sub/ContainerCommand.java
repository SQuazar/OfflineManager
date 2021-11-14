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

package net.flawe.offlinemanager.commands.sub;

import net.flawe.offlinemanager.api.configuration.ContainerConfiguration;
import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.enums.ActiveType;
import net.flawe.offlinemanager.api.enums.InventoryType;
import net.flawe.offlinemanager.api.event.inventory.OpenOfflineInventoryEvent;
import net.flawe.offlinemanager.api.inventory.holder.IOfflineInvHolder;
import net.flawe.offlinemanager.commands.OMCommand;
import net.flawe.offlinemanager.inventory.holders.OfflineArmorInventoryHolder;
import net.flawe.offlinemanager.inventory.holders.OfflineEnderChestHolder;
import net.flawe.offlinemanager.inventory.holders.OfflineInventoryHolder;
import net.flawe.offlinemanager.placeholders.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ContainerCommand extends OMCommand {

    private final ContainerConfiguration config;
    private final InventoryType inventoryType;
    private final ActiveType activeType;

    public ContainerCommand(String name, String help, String permission, InventoryType inventoryType, ContainerConfiguration config) {
        this(name, help, permission, new String[]{}, inventoryType, config);
    }

    public ContainerCommand(String name, String help, String permission, String[] aliases, InventoryType inventoryType, ContainerConfiguration config) {
        super(name, help, permission, aliases);
        this.config = config;
        this.inventoryType = inventoryType;
        addPlaceholders
                (
                        new Placeholder("%function%", Character.toUpperCase(name.charAt(0)) + name.substring(1).toLowerCase()),
                        new Placeholder("%permission%", permission)
                );
        switch (inventoryType) {
            case ARMOR:
                activeType = ActiveType.ARMOR_INVENTORY;
                break;
            case ENDER_CHEST:
                activeType = ActiveType.ENDER_CHEST;
                break;
            default:
                activeType = ActiveType.INVENTORY;
        }
    }

    @Override
    public void execute(Player player, String[] args) {
        addPlaceholder(new Placeholder("%player%", player.getName()));
        if (!config.enabled()) {
            sendPlayerMessage(player, messages.getFunctionDisabled());
            return;
        }
        if (!hasPermission(player)) {
            sendPlayerMessage(player, messages.getPermissionDeny());
            return;
        }
        if (args.length == 1) {
            sendPlayerMessage(player, messages.getEnterNickname());
            return;
        }
        String playerName = args[1];
        addPlaceholder(new Placeholder("%target%", playerName));
        Player target = Bukkit.getPlayerExact(playerName);
        if (target != null && target.isOnline()) {
            sendPlayerMessage(player, messages.getPlayerIsOnline());
            return;
        }
        if (!api.getStorage().hasPlayer(playerName)) {
            sendPlayerMessage(player, messages.getPlayerNotFound());
            return;
        }
        IPlayerData playerData = api.getPlayerData(playerName);
        if (api.getSession().containsValue(playerData.getUUID(), activeType)) {
            sendPlayerMessage(player, messages.getAlreadyBeingEdited());
            return;
        }
        IOfflineInvHolder holder = null;
        Inventory inventory = null;
        switch (inventoryType) {
            case DEFAULT:
                holder = new OfflineInventoryHolder(playerData, player, config.getName());
                inventory = holder.getInventory();
                break;
            case ARMOR:
                holder = new OfflineArmorInventoryHolder(playerData, player, config.getName());
                inventory = holder.getInventory();
                break;
            case ENDER_CHEST:
                holder = new OfflineEnderChestHolder(playerData, player, config.getName());
                inventory = holder.getInventory();
                break;
        }
        OpenOfflineInventoryEvent event = new OpenOfflineInventoryEvent(player, playerData, inventory, inventoryType);
        if (holder == null) {
            sendPlayerMessage(player, messages.getErrorMessage());
            return;
        }
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return;
        player.openInventory(inventory);
        api.getSession().add(player.getUniqueId(), playerData.getUUID(), activeType);
    }
}

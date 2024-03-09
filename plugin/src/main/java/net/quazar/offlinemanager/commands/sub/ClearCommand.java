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

package net.quazar.offlinemanager.commands.sub;

import net.quazar.offlinemanager.api.data.entity.IPlayerData;
import net.quazar.offlinemanager.api.enums.SavePlayerType;
import net.quazar.offlinemanager.api.event.inventory.ClearOfflineEnderchestEvent;
import net.quazar.offlinemanager.api.event.inventory.ClearOfflineInventoryEvent;
import net.quazar.offlinemanager.commands.OMCommand;
import net.quazar.offlinemanager.placeholders.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ClearCommand extends OMCommand {

    public ClearCommand(String name, String help, String permission) {
        super(name, help, permission);
        addPlaceholders
                (
                        new Placeholder("%function%", "Clear"),
                        new Placeholder("%permission%", permission)
                );
    }

    @Override
    public void execute(Player player, String[] args) {
        addPlaceholder(new Placeholder("%player%", player.getName()));
        if (!settings.getClearConfiguration().enabled()) {
            sendPlayerMessage(player, messages.getFunctionDisabled());
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
        if (args.length == 2) {
            ClearOfflineInventoryEvent event = new ClearOfflineInventoryEvent(playerData);
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled())
                return;
            playerData.getInventory().clear();
            playerData.save(SavePlayerType.INVENTORY);
            sendPlayerMessage(player, messages.getClearInventory());
        } else if (args[2].equalsIgnoreCase("ec")) {
            ClearOfflineEnderchestEvent event = new ClearOfflineEnderchestEvent(playerData);
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled())
                return;
            playerData.getEnderChest().getEnderChest().clear();
            playerData.save(SavePlayerType.ENDER_CHEST);
            sendPlayerMessage(player, messages.getClearEnderChest());
        }
    }
}

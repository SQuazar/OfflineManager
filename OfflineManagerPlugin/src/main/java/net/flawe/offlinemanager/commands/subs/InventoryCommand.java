package net.flawe.offlinemanager.commands.subs;

import net.flawe.offlinemanager.api.IUser;
import net.flawe.offlinemanager.api.enums.ActiveType;
import net.flawe.offlinemanager.commands.OMCommand;
import net.flawe.offlinemanager.events.OpenOfflineInventoryEvent;
import net.flawe.offlinemanager.holders.OfflineArmorInventoryHolder;
import net.flawe.offlinemanager.holders.OfflineInventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static net.flawe.offlinemanager.util.Messages.*;
import static net.flawe.offlinemanager.util.Permissions.*;

public class InventoryCommand extends OMCommand {

    public InventoryCommand(String name, String help, String permission) {
        super(name, help, permission);
    }

    @Override
    public void execute(Player player, String[] args) {
        if (!api.getConfigManager().getInventoryConfig().interact()) {
            player.sendMessage(api.getConfigManager().getMessageString(player, functionDisabled)
                    .replace("%function%", "Offline Inventory")
                    .replace("%player%", player.getName()));
            return;
        }
        if (!hasPermission(player)) {
            player.sendMessage(api.getConfigManager().getMessageString(player, permissionDeny)
                    .replace("%player%", player.getName())
                    .replace("%permission%", getPermission()));
            return;
        }
        if (args.length == 1) {
            player.sendMessage(api.getConfigManager().getMessageString(player, enterNickname)
                    .replace("%player%", player.getName()));
            return;
        }
        String playerName = args[1];
        Player t = Bukkit.getPlayerExact(playerName);
        if (t != null && t.isOnline()) {
            player.sendMessage(api.getConfigManager().getMessageString(player, playerIsOnline)
                    .replace("%player%", player.getName())
                    .replace("%target%", playerName));
            return;
        }
        if (!api.getStorage().hasPlayer(playerName)) {
            player.sendMessage(api.getConfigManager().getMessageString(player, playerNotFound)
                    .replace("%player%", player.getName())
                    .replace("%target%", playerName));
            return;
        }
        IUser user = api.getUser(playerName);
        OpenOfflineInventoryEvent event = new OpenOfflineInventoryEvent(player, user);
        if (args.length == 2) {
            if (api.getSession().containsValue(user.getUUID(), ActiveType.INVENTORY)) {
                player.sendMessage(api.getConfigManager().getMessageString(player, alreadyBeingEdited)
                        .replace("%target%", playerName)
                        .replace("%player%", player.getName()));
                return;
            }
            OfflineInventoryHolder holder = new OfflineInventoryHolder(user, player, api.getConfigManager().getInventoryConfig().getName());
            event.setInventory(holder.getInventory());
            event.setInventoryType(holder.getInventoryType());
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled())
                return;
            player.openInventory(holder.getInventory());
            api.getSession().add(player.getUniqueId(), user.getUUID(), ActiveType.INVENTORY);
            return;
        }
        if (args[2].equalsIgnoreCase("armor")) {
            if (!player.hasPermission(invsee_armor)) {
                player.sendMessage(api.getConfigManager().getMessageString(player, permissionDeny)
                        .replace("%player%", player.getName())
                        .replace("%permission%", getPermission()));
                return;
            }
            if (api.getSession().containsValue(user.getUUID(), ActiveType.ARMOR_INVENTORY)) {
                player.sendMessage(api.getConfigManager().getMessageString(player, alreadyBeingEdited)
                        .replace("%target%", playerName)
                        .replace("%player%", player.getName()));
                return;
            }
            OfflineArmorInventoryHolder armorInventoryHolder = new OfflineArmorInventoryHolder(user, player, api.getConfigManager().getArmorInventoryConfig().getName());
            event.setInventory(armorInventoryHolder.getInventory());
            event.setInventoryType(armorInventoryHolder.getInventoryType());
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled())
                return;
            player.openInventory(armorInventoryHolder.getInventory());
            api.getSession().add(player.getUniqueId(), user.getUUID(), ActiveType.ARMOR_INVENTORY);
        }
    }
}

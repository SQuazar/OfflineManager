package net.flawe.offlinemanager.commands.subs;

import net.flawe.offlinemanager.api.IUser;
import net.flawe.offlinemanager.api.enums.ActiveType;
import net.flawe.offlinemanager.commands.OMCommand;
import net.flawe.offlinemanager.events.OpenOfflineInventoryEvent;
import net.flawe.offlinemanager.holders.OfflineArmorInventoryHolder;
import net.flawe.offlinemanager.holders.OfflineInventoryHolder;
import net.flawe.offlinemanager.util.configuration.PlaceholderUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static net.flawe.offlinemanager.util.constants.Messages.*;
import static net.flawe.offlinemanager.util.constants.Permissions.*;

public class InventoryCommand extends OMCommand {

    public InventoryCommand(String name, String help, String permission) {
        super(name, help, permission);
    }

    @Override
    public void execute(Player player, String[] args) {
        addPlaceholder("%player%", player.getName());
        addPlaceholder("%permission%", getPermission());
        addPlaceholder("%function%", "Offline Inventory");
        String msg;
        if (!api.getConfigManager().getInventoryConfig().interact()) {
            msg = api.getConfigManager().getMessageString(player, functionDisabled);
            player.sendMessage(PlaceholderUtil.fillPlaceholders(msg, getPlaceholders()));
            return;
        }
        if (!hasPermission(player)) {
            msg = api.getConfigManager().getMessageString(player, permissionDeny);
            player.sendMessage(PlaceholderUtil.fillPlaceholders(msg, getPlaceholders()));
            return;
        }
        if (args.length == 1) {
            msg = api.getConfigManager().getMessageString(player, enterNickname);
            player.sendMessage(PlaceholderUtil.fillPlaceholders(msg, getPlaceholders()));
            return;
        }
        String playerName = args[1];
        addPlaceholder("%target%", playerName);
        Player t = Bukkit.getPlayerExact(playerName);
        if (t != null && t.isOnline()) {
            msg = api.getConfigManager().getMessageString(player, playerIsOnline);
            player.sendMessage(PlaceholderUtil.fillPlaceholders(msg, getPlaceholders()));
            return;
        }
        if (!api.getStorage().hasPlayer(playerName)) {
            msg = api.getConfigManager().getMessageString(player, playerNotFound);
            player.sendMessage(PlaceholderUtil.fillPlaceholders(msg, getPlaceholders()));
            return;
        }
        IUser user = api.getUser(playerName);
        OpenOfflineInventoryEvent event = new OpenOfflineInventoryEvent(player, user);
        if (args.length == 2) {
            if (api.getSession().containsValue(user.getUUID(), ActiveType.INVENTORY)) {
                msg = api.getConfigManager().getMessageString(player, alreadyBeingEdited);
                player.sendMessage(PlaceholderUtil.fillPlaceholders(msg, getPlaceholders()));
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
                msg = api.getConfigManager().getMessageString(player, permissionDeny);
                addPlaceholder("%permission%", invsee_armor);
                player.sendMessage(PlaceholderUtil.fillPlaceholders(msg, getPlaceholders()));
                return;
            }
            if (api.getSession().containsValue(user.getUUID(), ActiveType.ARMOR_INVENTORY)) {
                msg = api.getConfigManager().getMessageString(player, alreadyBeingEdited);
                player.sendMessage(PlaceholderUtil.fillPlaceholders(msg, getPlaceholders()));
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

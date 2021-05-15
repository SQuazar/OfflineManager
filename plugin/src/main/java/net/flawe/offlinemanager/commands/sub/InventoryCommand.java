package net.flawe.offlinemanager.commands.sub;

import net.flawe.offlinemanager.api.IUser;
import net.flawe.offlinemanager.api.enums.ActiveType;
import net.flawe.offlinemanager.commands.OMCommand;
import net.flawe.offlinemanager.api.events.inventory.OpenOfflineInventoryEvent;
import net.flawe.offlinemanager.inventory.holders.OfflineArmorInventoryHolder;
import net.flawe.offlinemanager.inventory.holders.OfflineInventoryHolder;
import net.flawe.offlinemanager.util.configuration.PlaceholderUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


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
        if (!settings.getInventoryConfiguration().interact()) {
            msg = api.getConfigManager().fillMessage(player, messages.getFunctionDisabled());
            player.sendMessage(PlaceholderUtil.fillPlaceholders(msg, getPlaceholders()));
            return;
        }
        if (!hasPermission(player)) {
            msg = api.getConfigManager().fillMessage(player, messages.getPermissionDeny());
            player.sendMessage(PlaceholderUtil.fillPlaceholders(msg, getPlaceholders()));
            return;
        }
        if (args.length == 1) {
            msg = api.getConfigManager().fillMessage(player, messages.getEnterNickname());
            player.sendMessage(PlaceholderUtil.fillPlaceholders(msg, getPlaceholders()));
            return;
        }
        String playerName = args[1];
        addPlaceholder("%target%", playerName);
        Player t = Bukkit.getPlayerExact(playerName);
        if (t != null && t.isOnline()) {
            msg = api.getConfigManager().fillMessage(player, messages.getPlayerIsOnline());
            player.sendMessage(PlaceholderUtil.fillPlaceholders(msg, getPlaceholders()));
            return;
        }
        if (!api.getStorage().hasPlayer(playerName)) {
            msg = api.getConfigManager().fillMessage(player, messages.getPlayerNotFound());
            player.sendMessage(PlaceholderUtil.fillPlaceholders(msg, getPlaceholders()));
            return;
        }
        IUser user = api.getUser(playerName);
        OpenOfflineInventoryEvent event = new OpenOfflineInventoryEvent(player, user);
        if (args.length == 2) {
            if (api.getSession().containsValue(user.getUUID(), ActiveType.INVENTORY)) {
                msg = api.getConfigManager().fillMessage(player, messages.getAlreadyBeingEdited());
                player.sendMessage(PlaceholderUtil.fillPlaceholders(msg, getPlaceholders()));
                return;
            }
            OfflineInventoryHolder holder = new OfflineInventoryHolder(user, player, settings.getInventoryConfiguration().getName());
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
            if (!player.hasPermission(OFFLINEMANAGER_INVSEE_ARMOR)) {
                msg = api.getConfigManager().fillMessage(player, messages.getPermissionDeny());
                addPlaceholder("%permission%", OFFLINEMANAGER_INVSEE_ARMOR);
                player.sendMessage(PlaceholderUtil.fillPlaceholders(msg, getPlaceholders()));
                return;
            }
            if (api.getSession().containsValue(user.getUUID(), ActiveType.ARMOR_INVENTORY)) {
                msg = api.getConfigManager().fillMessage(player, messages.getAlreadyBeingEdited());
                player.sendMessage(PlaceholderUtil.fillPlaceholders(msg, getPlaceholders()));
                return;
            }
            OfflineArmorInventoryHolder armorInventoryHolder = new OfflineArmorInventoryHolder(user, player, settings.getArmorInventoryConfiguration().getName());
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

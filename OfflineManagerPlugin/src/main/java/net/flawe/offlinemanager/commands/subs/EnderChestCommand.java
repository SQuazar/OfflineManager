package net.flawe.offlinemanager.commands.subs;

import net.flawe.offlinemanager.api.IUser;
import net.flawe.offlinemanager.api.enums.ActiveType;
import net.flawe.offlinemanager.api.enums.InventoryType;
import net.flawe.offlinemanager.commands.OMCommand;
import net.flawe.offlinemanager.events.OpenOfflineInventoryEvent;
import net.flawe.offlinemanager.holders.OfflineEnderChestHolder;
import net.flawe.offlinemanager.util.configuration.PlaceholderUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static net.flawe.offlinemanager.util.constants.Messages.*;

public class EnderChestCommand extends OMCommand {

    public EnderChestCommand(String name, String help, String permission, String[] aliases) {
        super(name, help, permission, aliases);
    }

    @Override
    public void execute(Player player, String[] args) {
        addPlaceholder("%function%", "Ender chest");
        addPlaceholder("%player%", player.getName());
        addPlaceholder("%permission%", getPermission());
        String msg;
        if (!api.getConfigManager().getEnderChestConfig().enabled()) {
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
        Player target = Bukkit.getPlayerExact(playerName);
        if (target != null && target.isOnline()) {
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
        if (api.getSession().containsValue(user.getUUID(), ActiveType.ENDER_CHEST)) {
            msg = api.getConfigManager().getMessageString(player, alreadyBeingEdited);
            player.sendMessage(PlaceholderUtil.fillPlaceholders(msg, getPlaceholders()));
            return;
        }
        OpenOfflineInventoryEvent event = new OpenOfflineInventoryEvent(player, user, InventoryType.ENDER_CHEST);
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled())
                return;
            OfflineEnderChestHolder holder = new OfflineEnderChestHolder(user);
            player.openInventory(holder.getInventory());
            api.getSession().add(player.getUniqueId(), user.getUUID(), ActiveType.ENDER_CHEST);
    }
}

package net.flawe.offlinemanager.commands.subs;

import net.flawe.offlinemanager.api.IUser;
import net.flawe.offlinemanager.api.enums.SavePlayerType;
import net.flawe.offlinemanager.commands.OMCommand;
import net.flawe.offlinemanager.events.ClearOfflineInventoryEvent;
import net.flawe.offlinemanager.util.configuration.PlaceholderUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static net.flawe.offlinemanager.util.constants.Messages.*;

public class ClearCommand extends OMCommand {

    public ClearCommand(String name, String help, String permission) {
        super(name, help, permission);
        addPlaceholder("%function%", "Clear");
        addPlaceholder("%permission%", getPermission());
    }

    @Override
    public void execute(Player player, String[] args) {
        addPlaceholder("%player%", player.getName());
        String msg;
        if (!api.getConfigManager().getCommandClearInventoryConfig().enabled()) {
            msg = api.getConfigManager().getMessageString(player, functionDisabled);
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
        ClearOfflineInventoryEvent event = new ClearOfflineInventoryEvent(player, user);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled())
            return;
        user.getPlayer().getInventory().clear();
        user.save(SavePlayerType.INVENTORY);
        msg = api.getConfigManager().getMessageString(player, clearInventory);
        player.sendMessage(PlaceholderUtil.fillPlaceholders(msg, getPlaceholders()));
    }
}

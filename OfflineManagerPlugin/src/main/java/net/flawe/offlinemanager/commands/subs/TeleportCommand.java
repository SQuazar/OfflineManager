package net.flawe.offlinemanager.commands.subs;

import net.flawe.offlinemanager.api.IUser;
import net.flawe.offlinemanager.commands.OMCommand;
import net.flawe.offlinemanager.events.OfflinePlayerTeleportEvent;
import net.flawe.offlinemanager.events.TeleportToOfflinePlayerEvent;
import net.flawe.offlinemanager.util.configuration.PlaceholderUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static net.flawe.offlinemanager.util.constants.Messages.*;

public class TeleportCommand extends OMCommand {

    public TeleportCommand(String name, String help, String permission, String[] aliases) {
        super(name, help, permission, aliases);
    }

    @Override
    public void execute(Player player, String[] args) {
        addPlaceholder("%player%", player.getName());
        addPlaceholder("%permission%", getPermission());
        addPlaceholder("%function%", "Teleport");
        String msg;
        if (!api.getConfigManager().getCommandTeleportConfig().enabled()) {
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
        if (args.length == 2) {
            TeleportToOfflinePlayerEvent event = new TeleportToOfflinePlayerEvent(player, user, player.getLocation(), user.getLocation());
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled())
                return;
            player.teleport(user.getLocation());
            msg = api.getConfigManager().getMessageString(player, teleportSuccess);
            player.sendMessage(PlaceholderUtil.fillPlaceholders(msg, getPlaceholders()));
            return;
        }
        String toPlayer = args[2];
        addPlaceholder("%to%", toPlayer);
        Player to = Bukkit.getPlayerExact(toPlayer);
        OfflinePlayerTeleportEvent event;
        if (to != null && to.isOnline()) {
            event = new OfflinePlayerTeleportEvent(player, user, to.getLocation(), user.getLocation());
            if (event.isCancelled())
                return;
            user.teleport(to.getLocation());
            msg = api.getConfigManager().getMessageString(player, teleportAnother);
            player.sendMessage(PlaceholderUtil.fillPlaceholders(msg, getPlaceholders()));
            return;
        }
        if (!api.getStorage().hasPlayer(toPlayer)) {
            msg = api.getConfigManager().getMessageString(player, playerNotFound);
            player.sendMessage(PlaceholderUtil.fillPlaceholders(msg, getPlaceholders()));
            return;
        }
        IUser toUser = api.getUser(toPlayer);
        event = new OfflinePlayerTeleportEvent(player, user, toUser.getLocation(), user.getLocation());
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled())
            return;
        user.teleport(toUser.getLocation());
        msg = api.getConfigManager().getMessageString(player, teleportAnother);
        player.sendMessage(PlaceholderUtil.fillPlaceholders(msg, getPlaceholders()));
    }
}

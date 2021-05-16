package net.flawe.offlinemanager.commands.sub;

import net.flawe.offlinemanager.api.IUser;
import net.flawe.offlinemanager.commands.OMCommand;
import net.flawe.offlinemanager.api.events.entity.player.OfflinePlayerTeleportEvent;
import net.flawe.offlinemanager.api.events.entity.player.TeleportToOfflinePlayerEvent;
import net.flawe.offlinemanager.util.configuration.PlaceholderUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;



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
        if (!settings.getTeleportConfiguration().enabled()) {
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
        Player target = Bukkit.getPlayerExact(playerName);
        if (target != null && target.isOnline()) {
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
        if (args.length == 2) {
            TeleportToOfflinePlayerEvent event = new TeleportToOfflinePlayerEvent(player, user, player.getLocation(), user.getLocation());
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled())
                return;
            player.teleport(user.getLocation());
            msg = api.getConfigManager().fillMessage(player, messages.getTeleportSuccess());
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
            msg = api.getConfigManager().fillMessage(player, messages.getTeleportAnother());
            player.sendMessage(PlaceholderUtil.fillPlaceholders(msg, getPlaceholders()));
            return;
        }
        if (!api.getStorage().hasPlayer(toPlayer)) {
            msg = api.getConfigManager().fillMessage(player, messages.getPlayerNotFound());
            player.sendMessage(PlaceholderUtil.fillPlaceholders(msg, getPlaceholders()));
            return;
        }
        IUser toUser = api.getUser(toPlayer);
        event = new OfflinePlayerTeleportEvent(player, user, toUser.getLocation(), user.getLocation());
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled())
            return;
        user.teleport(toUser.getLocation());
        msg = api.getConfigManager().fillMessage(player, messages.getTeleportAnother());
        player.sendMessage(PlaceholderUtil.fillPlaceholders(msg, getPlaceholders()));
    }
}

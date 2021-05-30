package net.flawe.offlinemanager.commands.sub;

import net.flawe.offlinemanager.api.IUser;
import net.flawe.offlinemanager.commands.OMCommand;
import net.flawe.offlinemanager.api.events.entity.player.OfflinePlayerTeleportEvent;
import net.flawe.offlinemanager.util.configuration.PlaceholderUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;



public class TeleportHereCommand extends OMCommand {

    public TeleportHereCommand(String name, String help, String permission) {
        super(name, help, permission);
    }

    @Override
    public void execute(Player player, String[] args) {
        addPlaceholder("%player%", player.getName());
        addPlaceholder("%permission%", getPermission());
        addPlaceholder("%function%", "Teleport here");
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
        OfflinePlayerTeleportEvent event = new OfflinePlayerTeleportEvent(player, user, player.getLocation(), user.getLocation());
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled())
            return;
        user.teleport(player);
        msg = api.getConfigManager().fillMessage(player, messages.getTeleportHere());
        player.sendMessage(PlaceholderUtil.fillPlaceholders(msg, getPlaceholders()));
    }
}
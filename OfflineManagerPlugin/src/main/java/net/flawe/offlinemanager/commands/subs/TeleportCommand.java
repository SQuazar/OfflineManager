package net.flawe.offlinemanager.commands.subs;

import net.flawe.offlinemanager.OfflineManager;
import net.flawe.offlinemanager.api.IUser;
import net.flawe.offlinemanager.api.enums.SavePlayerType;
import net.flawe.offlinemanager.commands.OMCommand;
import net.flawe.offlinemanager.events.OfflineManagerEvent;
import net.flawe.offlinemanager.events.OfflinePlayerTeleportEvent;
import net.flawe.offlinemanager.events.TeleportToOfflinePlayerEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static net.flawe.offlinemanager.util.Messages.*;

public class TeleportCommand extends OMCommand {

    private final OfflineManager plugin;

    public TeleportCommand(String name, String help, String permission, String[] aliases, OfflineManager plugin) {
        super(name, help, permission, aliases);
        this.plugin = plugin;
    }

    @Override
    public void execute(Player player, String[] args) {
        if (!api.getConfigManager().getCommandTeleportConfig().enabled()) {
            player.sendMessage(api.getConfigManager().getMessageString(player, functionDisabled)
                    .replace("%function%", "Teleport"));
            return;
        }
        if (!hasPermission(player)) {
            player.sendMessage(api.getConfigManager().getMessageString(player, permissionDeny));
            return;
        }
        if (args.length == 1) {
            player.sendMessage(api.getConfigManager().getMessageString(player, enterNickname));
            return;
        }
        String playerName = args[1];
        Player target = Bukkit.getPlayerExact(playerName);
        if (target != null && target.isOnline()) {
            player.sendMessage(api.getConfigManager().getMessageString(player, playerIsOnline));
            return;
        }
        if (!api.getStorage().hasPlayer(playerName)) {
            player.sendMessage(api.getConfigManager().getMessageString(player, playerNotFound));
            return;
        }
        IUser user = api.getUser(playerName);
        if (args.length == 2) {
            TeleportToOfflinePlayerEvent event = new TeleportToOfflinePlayerEvent(player, user, player.getLocation(), user.getLocation());
            Bukkit.getScheduler().runTask(plugin, () -> {
                Bukkit.getPluginManager().callEvent(event);
                if (event.isCancelled())
                    return;
                player.teleport(user.getLocation());
                player.sendMessage(api.getConfigManager().getMessageString(player, teleportSuccess));
            });
            return;
        }
        String toPlayer = args[2];
        Player to = Bukkit.getPlayerExact(toPlayer);
        OfflinePlayerTeleportEvent event;
        if (to != null && to.isOnline()) {
            event = new OfflinePlayerTeleportEvent(player, user, to.getLocation(), user.getLocation());
            Bukkit.getScheduler().runTask(plugin, () -> {
                if (event.isCancelled())
                    return;
                user.teleport(to.getLocation());
                player.sendMessage(api.getConfigManager().getMessageString(player, teleportAnother)
                        .replace("%target%", user.getPlayer().getName())
                        .replace("%to%", to.getName()));
            });
            return;
        }
        if (!api.getStorage().hasPlayer(toPlayer)) {
            player.sendMessage(api.getConfigManager().getMessageString(player, playerNotFound));
            return;
        }
        IUser toUser = api.getUser(toPlayer);
        event = new OfflinePlayerTeleportEvent(player, user, toUser.getLocation(), user.getLocation());
        Bukkit.getScheduler().runTask(plugin, () -> {
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled())
                return;
            user.teleport(toUser.getLocation());
            player.sendMessage(api.getConfigManager().getMessageString(player, teleportAnother)
                    .replace("%target%", user.getPlayer().getName())
                    .replace("%to%", toUser.getPlayer().getName()));
        });
    }
}

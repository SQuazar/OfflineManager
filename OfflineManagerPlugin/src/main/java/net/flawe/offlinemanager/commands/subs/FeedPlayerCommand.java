package net.flawe.offlinemanager.commands.subs;

import net.flawe.offlinemanager.OfflineManager;
import net.flawe.offlinemanager.api.IUser;
import net.flawe.offlinemanager.api.enums.SavePlayerType;
import net.flawe.offlinemanager.commands.OMCommand;
import net.flawe.offlinemanager.events.FeedOfflinePlayerEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static net.flawe.offlinemanager.util.Messages.*;
import static net.flawe.offlinemanager.util.Messages.playerNotFound;

public class FeedPlayerCommand extends OMCommand {

    private final OfflineManager plugin;

    public FeedPlayerCommand(String name, String help, String permission, OfflineManager plugin) {
        super(name, help, permission);
        this.plugin = plugin;
    }

    @Override
    public void execute(Player player, String[] args) {
        if (!api.getConfigManager().getCommandFeedConfig().enabled()) {
            player.sendMessage(api.getConfigManager().getMessageString(player, functionDisabled)
                    .replace("%function%", "Feed")
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
        Player target = Bukkit.getPlayerExact(playerName);
        if (target != null && target.isOnline()) {
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
        FeedOfflinePlayerEvent event = new FeedOfflinePlayerEvent(player, user);
        Bukkit.getScheduler().runTask(plugin, () -> {
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled())
                return;
            user.getPlayer().setFoodLevel(20);
            user.save(SavePlayerType.FOOD_LEVEL);
            player.sendMessage(api.getConfigManager().getMessageString(player, feedPlayer)
                    .replace("%target%", user.getPlayer().getName())
                    .replace("%player%", player.getName()));
        });
    }
}

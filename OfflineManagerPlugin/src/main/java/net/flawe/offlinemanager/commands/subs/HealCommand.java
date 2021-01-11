package net.flawe.offlinemanager.commands.subs;

import net.flawe.offlinemanager.api.IUser;
import net.flawe.offlinemanager.api.enums.SavePlayerType;
import net.flawe.offlinemanager.commands.OMCommand;
import net.flawe.offlinemanager.events.HealOfflinePlayerEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static net.flawe.offlinemanager.util.Messages.*;

public class HealCommand extends OMCommand {

    public HealCommand(String name, String help, String permission) {
        super(name, help, permission);
    }

    @Override
    public void execute(Player player, String[] args) {
        if (!api.getConfigManager().getCommandHealConfig().enabled()) {
            player.sendMessage(api.getConfigManager().getMessageString(player, functionDisabled)
                    .replace("%function%", "Heal")
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
        HealOfflinePlayerEvent event = new HealOfflinePlayerEvent(player, user);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled())
            return;
        user.getPlayer().setHealth(20);
        user.save(SavePlayerType.HEALTHS);
        player.sendMessage(api.getConfigManager().getMessageString(player, healPlayer)
                .replace("%target%", user.getPlayer().getName())
                .replace("%player%", player.getName()));
    }
}

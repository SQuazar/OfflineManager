package net.flawe.offlinemanager.commands.subs;

import net.flawe.offlinemanager.api.IUser;
import net.flawe.offlinemanager.commands.OMCommand;
import net.flawe.offlinemanager.events.GameModeChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import static net.flawe.offlinemanager.util.Messages.*;

public class SpectatorCommand extends OMCommand {

    public SpectatorCommand(String name, String help, String permission) {
        super(name, help, permission);
    }

    @Override
    public void execute(Player player, String[] args) {
        if (!api.getConfigManager().getCommandGameModeConfig().enabled()) {
            player.sendMessage(api.getConfigManager().getMessageString(player, functionDisabled)
                    .replace("%function%", "GameMode change")
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
        GameModeChangeEvent event = new GameModeChangeEvent(user, player, GameMode.SPECTATOR);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled())
            return;
        user.setGameMode(GameMode.SPECTATOR);
        player.sendMessage(api.getConfigManager().getMessageString(player, gamemodeChanged)
                .replace("%target%", user.getPlayer().getName())
                .replace("%player%", player.getName())
                .replace("%gamemode%", getName()));
    }
}

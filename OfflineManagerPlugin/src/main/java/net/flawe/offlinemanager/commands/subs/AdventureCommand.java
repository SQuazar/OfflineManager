package net.flawe.offlinemanager.commands.subs;

import net.flawe.offlinemanager.api.IUser;
import net.flawe.offlinemanager.commands.OMCommand;
import net.flawe.offlinemanager.events.GameModeChangeEvent;
import net.flawe.offlinemanager.util.configuration.PlaceholderUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import static net.flawe.offlinemanager.util.constants.Messages.*;

public class AdventureCommand extends OMCommand {

    public AdventureCommand(String name, String help, String permission) {
        super(name, help, permission);
        addPlaceholder("%function%", "GameMode change");
        addPlaceholder("%permission%", getPermission());
        addPlaceholder("%gamemode%", getName());
    }

    @Override
    public void execute(Player player, String[] args) {
        addPlaceholder("%player%", player.getName());
        String msg;
        if (!api.getConfigManager().getCommandGameModeConfig().enabled()) {
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
        Player t = Bukkit.getPlayerExact(playerName);
        if (t != null && t.isOnline()) {
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
        GameModeChangeEvent event = new GameModeChangeEvent(user, player, GameMode.ADVENTURE);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled())
            return;
        user.setGameMode(GameMode.ADVENTURE);
        msg = api.getConfigManager().getMessageString(player, gamemodeChanged);
        player.sendMessage(PlaceholderUtil.fillPlaceholders(msg, getPlaceholders()));
    }
}

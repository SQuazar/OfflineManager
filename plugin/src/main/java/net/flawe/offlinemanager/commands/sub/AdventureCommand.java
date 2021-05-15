package net.flawe.offlinemanager.commands.sub;

import net.flawe.offlinemanager.api.IUser;
import net.flawe.offlinemanager.commands.OMCommand;
import net.flawe.offlinemanager.api.events.entity.player.GameModeChangeEvent;
import net.flawe.offlinemanager.util.configuration.PlaceholderUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

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
        if (!settings.getChangeGamemodeConfiguration().enabled()) {
            msg = api.getConfigManager().getMessage(player, messages.getFunctionDisabled());
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
        Player t = Bukkit.getPlayerExact(playerName);
        if (t != null && t.isOnline()) {
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
        GameModeChangeEvent event = new GameModeChangeEvent(user, player, GameMode.ADVENTURE);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled())
            return;
        user.setGameMode(GameMode.ADVENTURE);
        msg = api.getConfigManager().fillMessage(player, messages.getGamemodeChanged());
        player.sendMessage(PlaceholderUtil.fillPlaceholders(msg, getPlaceholders()));
    }
}

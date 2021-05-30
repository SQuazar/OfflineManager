package net.flawe.offlinemanager.commands.sub;

import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.enums.SavePlayerType;
import net.flawe.offlinemanager.api.events.entity.player.GameModeChangeEvent;
import net.flawe.offlinemanager.commands.OMCommand;
import net.flawe.offlinemanager.placeholders.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.Locale;

public class GameModeCommand extends OMCommand {

    public GameModeCommand(String name, String help, String permission) {
        super(name, help, permission);
        addPlaceholders
                (
                        new Placeholder("%function%", "GameMode change"),
                        new Placeholder("%permission%", permission),
                        new Placeholder("%gamemode%", name)
                );
    }

    @Override
    public void execute(Player player, String[] args) {
        addPlaceholder(new Placeholder("%player%", player.getName()));
        if (!settings.getChangeGamemodeConfiguration().enabled()) {
            sendPlayerMessage(player, messages.getFunctionDisabled());
            return;
        }
        if (args.length == 1) {
            sendPlayerMessage(player, messages.getEnterNickname());
            return;
        }
        String playerName = args[1];
        addPlaceholder(new Placeholder("%target%", playerName));
        Player t = Bukkit.getPlayerExact(playerName);
        if (t != null && t.isOnline()) {
            sendPlayerMessage(player, messages.getPlayerIsOnline());
            return;
        }
        if (!api.getStorage().hasPlayer(playerName)) {
            sendPlayerMessage(player, messages.getPlayerNotFound());
            return;
        }
        IPlayerData playerData = api.getPlayerData(playerName);
        GameMode gameMode = GameMode.valueOf(getName().toUpperCase(Locale.ENGLISH));
        GameModeChangeEvent event = new GameModeChangeEvent(playerData, player, gameMode);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled())
            return;
        playerData.setGameMode(gameMode);
        playerData.save(SavePlayerType.GAMEMODE);
        sendPlayerMessage(player, messages.getGamemodeChanged());
    }
}

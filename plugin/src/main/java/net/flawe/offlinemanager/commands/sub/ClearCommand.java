package net.flawe.offlinemanager.commands.sub;

import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.enums.SavePlayerType;
import net.flawe.offlinemanager.api.events.inventory.ClearOfflineInventoryEvent;
import net.flawe.offlinemanager.commands.OMCommand;
import net.flawe.offlinemanager.placeholders.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ClearCommand extends OMCommand {

    public ClearCommand(String name, String help, String permission) {
        super(name, help, permission);
        addPlaceholders
                (
                        new Placeholder("%function%", "Clear"),
                        new Placeholder("%permission%", permission)
                );
    }

    @Override
    public void execute(Player player, String[] args) {
        addPlaceholder(new Placeholder("%player%", player.getName()));
        if (!settings.getClearConfiguration().enabled()) {
            sendPlayerMessage(player, messages.getFunctionDisabled());
            return;
        }
        if (args.length == 1) {
            sendPlayerMessage(player, messages.getEnterNickname());
            return;
        }
        String playerName = args[1];
        addPlaceholder(new Placeholder("%target%", playerName));
        Player target = Bukkit.getPlayerExact(playerName);
        if (target != null && target.isOnline()) {
            sendPlayerMessage(player, messages.getPlayerIsOnline());
            return;
        }
        if (!api.getStorage().hasPlayer(playerName)) {
            sendPlayerMessage(player, messages.getPlayerNotFound());
            return;
        }
        IPlayerData playerData = api.getPlayerData(playerName);
        ClearOfflineInventoryEvent event = new ClearOfflineInventoryEvent(player, playerData);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled())
            return;
        playerData.getInventory().clear();
        playerData.save(SavePlayerType.INVENTORY);
        sendPlayerMessage(player, messages.getClearInventory());
    }
}

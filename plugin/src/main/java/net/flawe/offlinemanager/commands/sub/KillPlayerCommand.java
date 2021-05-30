package net.flawe.offlinemanager.commands.sub;

import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.enums.SavePlayerType;
import net.flawe.offlinemanager.api.events.entity.player.KillOfflinePlayerEvent;
import net.flawe.offlinemanager.commands.OMCommand;
import net.flawe.offlinemanager.placeholders.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class KillPlayerCommand extends OMCommand {

    public KillPlayerCommand(String name, String help, String permission) {
        super(name, help, permission);
        addPlaceholders
                (
                        new Placeholder("%function%", "Kill"),
                        new Placeholder("%permission%", permission)
                );
    }

    @Override
    public void execute(Player player, String[] args) {
        addPlaceholder(new Placeholder("%player%", player.getName()));
        String msg;
        if (!settings.getCommandKillConfiguration().enabled()) {
            sendPlayerMessage(player, messages.getFunctionDisabled());
            return;
        }
        if (!hasPermission(player)) {
            sendPlayerMessage(player, messages.getPermissionDeny());
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
        KillOfflinePlayerEvent event = new KillOfflinePlayerEvent(player, playerData);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled())
            return;
        playerData.setHealth(0F);
        if (settings.getCommandKillConfiguration().dropItems()) {
            for (ItemStack stack : playerData.getInventory()) {
                if (stack == null)
                    continue;
                if (playerData.getWorld() == null)
                    break;
                playerData.getWorld().dropItemNaturally(playerData.getLocation(), stack);
            }
        }
        if (settings.getCommandKillConfiguration().clearItems()) {
            playerData.getInventory().clear();
            playerData.save(SavePlayerType.INVENTORY);
        }
        playerData.save(SavePlayerType.HEALTHS);
        sendPlayerMessage(player, messages.getKillPlayer());
    }
}

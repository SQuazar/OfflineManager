package net.flawe.offlinemanager.commands.sub;

import net.flawe.offlinemanager.api.IUser;
import net.flawe.offlinemanager.api.enums.SavePlayerType;
import net.flawe.offlinemanager.commands.OMCommand;
import net.flawe.offlinemanager.api.events.inventory.ClearOfflineInventoryEvent;
import net.flawe.offlinemanager.util.configuration.PlaceholderUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ClearCommand extends OMCommand {

    public ClearCommand(String name, String help, String permission) {
        super(name, help, permission);
        addPlaceholder("%function%", "Clear");
        addPlaceholder("%permission%", getPermission());
    }

    @Override
    public void execute(Player player, String[] args) {
        addPlaceholder("%player%", player.getName());
        String msg;
        if (!settings.getClearConfiguration().enabled()) {
            msg = api.getConfigManager().fillMessage(player, messages.getFunctionDisabled());
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
        ClearOfflineInventoryEvent event = new ClearOfflineInventoryEvent(player, user);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled())
            return;
        user.getPlayer().getInventory().clear();
        user.save(SavePlayerType.INVENTORY);
        msg = api.getConfigManager().fillMessage(player, messages.getClearInventory());
        player.sendMessage(PlaceholderUtil.fillPlaceholders(msg, getPlaceholders()));
    }
}

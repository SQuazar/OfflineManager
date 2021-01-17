package net.flawe.offlinemanager.commands.subs;

import net.flawe.offlinemanager.api.IUser;
import net.flawe.offlinemanager.api.enums.SavePlayerType;
import net.flawe.offlinemanager.commands.OMCommand;
import net.flawe.offlinemanager.events.KillOfflinePlayerEvent;
import net.flawe.offlinemanager.util.configuration.PlaceholderUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static net.flawe.offlinemanager.util.constants.Messages.*;

public class KillPlayerCommand extends OMCommand {

    public KillPlayerCommand(String name, String help, String permission) {
        super(name, help, permission);
    }

    @Override
    public void execute(Player player, String[] args) {
        addPlaceholder("%player%", player.getName());
        addPlaceholder("%permission%", getPermission());
        addPlaceholder("%function%", "Kill");
        String msg;
        if (!api.getConfigManager().getCommandKillConfig().enabled()) {
            msg = api.getConfigManager().getMessageString(player, functionDisabled);
            player.sendMessage(PlaceholderUtil.fillPlaceholders(msg, getPlaceholders()));
            return;
        }
        if (!hasPermission(player)) {
            msg = api.getConfigManager().getMessageString(player, permissionDeny);
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
        KillOfflinePlayerEvent event = new KillOfflinePlayerEvent(player, user);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled())
            return;
        user.kill();
        if (api.getConfigManager().getCommandKillConfig().dropItems()) {
            for (ItemStack stack : user.getPlayer().getInventory()) {
                if (stack == null)
                    continue;
                if (user.getLocation().getWorld() == null)
                    break;
                user.getLocation().getWorld().dropItemNaturally(user.getLocation(), stack);
            }
        }
        if (api.getConfigManager().getCommandKillConfig().clearItems()) {
            user.getPlayer().getInventory().clear();
            user.save(SavePlayerType.INVENTORY);
        }
        user.save(SavePlayerType.HEALTHS);
        msg = api.getConfigManager().getMessageString(player, killPlayer);
        player.sendMessage(PlaceholderUtil.fillPlaceholders(msg, getPlaceholders()));
    }
}

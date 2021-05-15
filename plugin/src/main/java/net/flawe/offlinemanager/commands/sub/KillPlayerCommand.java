package net.flawe.offlinemanager.commands.sub;

import net.flawe.offlinemanager.api.IUser;
import net.flawe.offlinemanager.api.enums.SavePlayerType;
import net.flawe.offlinemanager.commands.OMCommand;
import net.flawe.offlinemanager.api.events.entity.player.KillOfflinePlayerEvent;
import net.flawe.offlinemanager.util.configuration.PlaceholderUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;



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
        if (!settings.getCommandKillConfiguration().enabled()) {
            msg = api.getConfigManager().fillMessage(player, messages.getFunctionDisabled());
            player.sendMessage(PlaceholderUtil.fillPlaceholders(msg, getPlaceholders()));
            return;
        }
        if (!hasPermission(player)) {
            msg = api.getConfigManager().fillMessage(player, messages.getPermissionDeny());
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
        KillOfflinePlayerEvent event = new KillOfflinePlayerEvent(player, user);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled())
            return;
        user.kill();
        if (settings.getCommandKillConfiguration().dropItems()) {
            for (ItemStack stack : user.getPlayer().getInventory()) {
                if (stack == null)
                    continue;
                if (user.getLocation().getWorld() == null)
                    break;
                user.getLocation().getWorld().dropItemNaturally(user.getLocation(), stack);
            }
        }
        if (settings.getCommandKillConfiguration().clearItems()) {
            user.getPlayer().getInventory().clear();
            user.save(SavePlayerType.INVENTORY);
        }
        user.save(SavePlayerType.HEALTHS);
        msg = api.getConfigManager().fillMessage(player, messages.getKillPlayer());
        player.sendMessage(PlaceholderUtil.fillPlaceholders(msg, getPlaceholders()));
    }
}

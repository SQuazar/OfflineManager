package net.flawe.offlinemanager.commands.subs;

import net.flawe.offlinemanager.OfflineManager;
import net.flawe.offlinemanager.api.IUser;
import net.flawe.offlinemanager.api.enums.SavePlayerType;
import net.flawe.offlinemanager.commands.OMCommand;
import net.flawe.offlinemanager.events.KillOfflinePlayerEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static net.flawe.offlinemanager.util.Messages.*;

public class KillPlayerCommand extends OMCommand {

    private final OfflineManager plugin;

    public KillPlayerCommand(String name, String help, String permission, OfflineManager plugin) {
        super(name, help, permission);
        this.plugin = plugin;
    }

    @Override
    public void execute(Player player, String[] args) {
        if (!api.getConfigManager().getCommandKillConfig().enabled()) {
            player.sendMessage(api.getConfigManager().getMessageString(player, functionDisabled)
                    .replace("%function%", "Kill")
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
        KillOfflinePlayerEvent event = new KillOfflinePlayerEvent(player, user);
        Bukkit.getScheduler().runTask(plugin, () -> {
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
            player.sendMessage(api.getConfigManager().getMessageString(player, killPlayer)
                    .replace("%target%", user.getPlayer().getName())
                    .replace("%player%", player.getName()));
        });
    }
}

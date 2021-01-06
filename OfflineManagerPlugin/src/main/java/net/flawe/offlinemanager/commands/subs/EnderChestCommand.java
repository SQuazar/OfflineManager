package net.flawe.offlinemanager.commands.subs;

import net.flawe.offlinemanager.OfflineManager;
import net.flawe.offlinemanager.api.IUser;
import net.flawe.offlinemanager.api.enums.ActiveType;
import net.flawe.offlinemanager.api.enums.InventoryType;
import net.flawe.offlinemanager.commands.OMCommand;
import net.flawe.offlinemanager.events.OpenOfflineInventoryEvent;
import net.flawe.offlinemanager.holders.OfflineEnderChestHolder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static net.flawe.offlinemanager.util.Messages.*;
import static net.flawe.offlinemanager.util.Messages.playerNotFound;

public class EnderChestCommand extends OMCommand {

    private final OfflineManager plugin;

    public EnderChestCommand(String name, String help, String permission, String[] aliases, OfflineManager plugin) {
        super(name, help, permission, aliases);
        this.plugin = plugin;
    }

    @Override
    public void execute(Player player, String[] args) {
        if (!api.getConfigManager().getEnderChestConfig().enabled()) {
            player.sendMessage(api.getConfigManager().getMessageString(player, functionDisabled)
                    .replace("%function%", "Ender Chest")
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
        Player target = Bukkit.getPlayerExact(playerName);
        if (target != null && target.isOnline()) {
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
        if (api.getSession().containsValue(user.getUUID(), ActiveType.ENDER_CHEST)) {
            player.sendMessage(api.getConfigManager().getMessageString(player, alreadyBeingEdited)
                    .replace("%player%", player.getName())
                    .replace("%target%", playerName));
            return;
        }
        OpenOfflineInventoryEvent event = new OpenOfflineInventoryEvent(player, user, InventoryType.ENDER_CHEST);
        Bukkit.getScheduler().runTask(plugin, () -> {
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled())
                return;
            OfflineEnderChestHolder holder = new OfflineEnderChestHolder(user);
            player.openInventory(holder.getInventory());
            api.getSession().add(player.getUniqueId(), user.getUUID(), ActiveType.ENDER_CHEST);
        });
    }
}

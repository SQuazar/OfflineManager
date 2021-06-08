package net.flawe.offlinemanager.commands.sub;

import net.flawe.offlinemanager.commands.OMCommand;
import org.bukkit.entity.Player;

public class ReloadCommand extends OMCommand {

    public ReloadCommand(String name, String help, String permission) {
        super(name, help, permission);
    }

    @Override
    public void execute(Player player, String[] args) {
        if (!hasPermission(player)) {
            sendPlayerMessage(player, messages.getPermissionDeny());
            return;
        }
        api.getConfigManager().reloadConfig();
        sendPlayerMessage(player, "&a[OfflineManager] &eConfiguration file success reloaded!");
    }
}

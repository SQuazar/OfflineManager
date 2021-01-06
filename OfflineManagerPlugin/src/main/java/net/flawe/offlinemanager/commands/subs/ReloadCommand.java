package net.flawe.offlinemanager.commands.subs;

import net.flawe.offlinemanager.commands.OMCommand;
import net.flawe.offlinemanager.util.ColorUtils;
import org.bukkit.entity.Player;

import static net.flawe.offlinemanager.util.Messages.*;

public class ReloadCommand extends OMCommand {

    public ReloadCommand(String name, String help, String permission) {
        super(name, help, permission);
    }

    @Override
    public void execute(Player player, String[] args) {
        if (!hasPermission(player)) {
            player.sendMessage(api.getConfigManager().getMessageString(player, permissionDeny)
                    .replace("%player%", player.getName())
                    .replace("%permission%", getPermission()));
            return;
        }
        api.getConfigManager().reloadConfig();
        api.getConfigManager().reloadMessages();
        player.sendMessage(ColorUtils.getFormattedString("&a[OfflineManager] &eConfiguration file success reloaded!"));
    }
}

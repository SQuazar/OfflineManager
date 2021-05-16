package net.flawe.offlinemanager.commands.sub;

import net.flawe.offlinemanager.commands.OMCommand;
import net.flawe.offlinemanager.util.configuration.PlaceholderUtil;
import org.bukkit.entity.Player;



/**
 * Not supported in beta-0.1
 */
public class UpdateConfigCommand extends OMCommand {

    public UpdateConfigCommand(String name, String help, String permission) {
        super(name, help, permission);
    }

    @Override
    public void execute(Player player, String[] args) {
        addPlaceholder("%player%", player.getName());
        addPlaceholder("%permission%", getPermission());
        if (!hasPermission(player)) {
            String msg = api.getConfigManager().fillMessage(player, messages.getPermissionDeny());
            player.sendMessage(PlaceholderUtil.fillPlaceholders(msg, getPlaceholders()));
            return;
        }
        api.getConfigManager().update();
    }

}

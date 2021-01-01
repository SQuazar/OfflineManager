package net.flawe.offlinemanager.commands.subs;

import net.flawe.offlinemanager.commands.OMCommand;
import org.bukkit.entity.Player;

import static net.flawe.offlinemanager.util.Messages.*;

/**
 * Not supported in beta-0.1
 */
public class UpdateConfigCommand extends OMCommand {

    public UpdateConfigCommand(String name, String help, String permission) {
        super(name, help, permission);
    }

    @Override
    public void execute(Player player, String[] args) {
        if (!hasPermission(player)) {
            player.sendMessage(api.getConfigManager().getMessageString(player, permissionDeny));
            return;
        }
        api.getConfigManager().update();
    }

}

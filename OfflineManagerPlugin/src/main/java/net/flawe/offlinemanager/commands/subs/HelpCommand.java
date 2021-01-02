package net.flawe.offlinemanager.commands.subs;

import net.flawe.offlinemanager.api.ICommand;
import net.flawe.offlinemanager.commands.OMCommand;
import net.flawe.offlinemanager.util.ColorUtils;
import org.bukkit.entity.Player;

import static net.flawe.offlinemanager.util.Messages.functionDisabled;

public class HelpCommand extends OMCommand {

    public HelpCommand(String name, String help, String permission) {
        super(name, help, permission);
    }

    @Override
    public void execute(Player player, String[] args) {
        if (!hasPermission(player)) {
            player.sendMessage(api.getConfigManager().getMessageString(player, functionDisabled));
            return;
        }
        StringBuilder builder = new StringBuilder("&a[OfflineManager] &eCommand list:");
        for (ICommand command : api.getCommandManager().getSubCommands()) {
            if (command.hasPermission(player))
                builder.append("\n &e- &a/om ")
                        .append(command.getName())
                        .append(" &e- &e")
                        .append(command.getHelp());
        }
        player.sendMessage(ColorUtils.getFormattedString(builder.toString()));
    }
}

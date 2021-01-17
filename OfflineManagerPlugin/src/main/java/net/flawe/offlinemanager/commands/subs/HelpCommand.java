package net.flawe.offlinemanager.commands.subs;

import net.flawe.offlinemanager.api.ICommand;
import net.flawe.offlinemanager.commands.OMCommand;
import net.flawe.offlinemanager.util.ColorUtils;
import net.flawe.offlinemanager.util.configuration.PlaceholderUtil;
import org.bukkit.entity.Player;

import static net.flawe.offlinemanager.util.constants.Messages.permissionDeny;

public class HelpCommand extends OMCommand {

    public HelpCommand(String name, String help, String permission) {
        super(name, help, permission);
    }

    @Override
    public void execute(Player player, String[] args) {
        addPlaceholder("%player%", player.getName());
        addPlaceholder("%permission%", getPermission());
        if (!hasPermission(player)) {
            String msg = api.getConfigManager().getMessageString(player, permissionDeny);
            player.sendMessage(PlaceholderUtil.fillPlaceholders(msg, getPlaceholders()));
            return;
        }
        StringBuilder builder = new StringBuilder("&a[OfflineManager] &eOfflineManager help menu:");
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

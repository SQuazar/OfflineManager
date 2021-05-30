package net.flawe.offlinemanager.commands.sub;

import net.flawe.offlinemanager.api.command.ICommand;
import net.flawe.offlinemanager.commands.OMCommand;
import net.flawe.offlinemanager.placeholders.Placeholder;
import org.bukkit.entity.Player;

public class HelpCommand extends OMCommand {

    public HelpCommand(String name, String help, String permission) {
        super(name, help, permission);
        addPlaceholder(new Placeholder("%permission%", permission));
    }

    @Override
    public void execute(Player player, String[] args) {
        addPlaceholder(new Placeholder("%player%", player.getName()));
        if (!hasPermission(player)) {
            sendPlayerMessage(player, messages.getPermissionDeny());
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
        sendPlayerMessage(player, builder.toString());
    }
}

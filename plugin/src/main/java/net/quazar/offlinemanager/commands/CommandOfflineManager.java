package net.quazar.offlinemanager.commands;

import net.quazar.offlinemanager.OfflineManager;
import net.quazar.offlinemanager.api.command.CommandBase;
import net.quazar.offlinemanager.commands.sub.CommandOMClear;
import net.quazar.offlinemanager.configuration.Messages;
import org.bukkit.command.CommandSender;

public final class CommandOfflineManager extends CommandBase {
    private final Messages messages;

    public CommandOfflineManager(OfflineManager plugin, String name, String description, String permission) {
        super(name, description, permission, false);
        this.messages = plugin.getMessages();
        addCommand(new CommandOMClear(plugin, "clear", "Clears offline player inventory", "offlinemanager.clear", false));
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        CommandHelper.sendMessage(sender, messages.getEnterSubCommand());
    }
}

package net.quazar.offlinemanager.commands.sub;

import net.quazar.offlinemanager.OfflineManager;
import net.quazar.offlinemanager.api.IPlaceholder;
import net.quazar.offlinemanager.api.OfflineManagerAPI;
import net.quazar.offlinemanager.api.command.CommandBase;
import net.quazar.offlinemanager.configuration.Messages;
import net.quazar.offlinemanager.configuration.Settings;
import net.quazar.offlinemanager.placeholders.Placeholder;
import org.bukkit.command.CommandSender;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommandOMFeed extends CommandBase {
    private final OfflineManagerAPI api;
    private final Settings settings;
    private final Messages messages;

    private final Set<IPlaceholder> placeholders = new HashSet<>();

    public CommandOMFeed(OfflineManager plugin, String name, String description, String permission) {
        super(name, description, permission);
        this.api = plugin;
        this.settings = plugin.getSettings();
        this.messages = plugin.getMessages();
        placeholders.add(Placeholder.of("%function%", "Feed"));
        placeholders.add(Placeholder.of("%permission%", permission));
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) return api.getStorage().getListForComplete(args[0]);
        return null;
    }
}

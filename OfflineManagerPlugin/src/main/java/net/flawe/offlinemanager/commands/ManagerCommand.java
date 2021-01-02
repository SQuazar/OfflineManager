package net.flawe.offlinemanager.commands;

import net.flawe.offlinemanager.OfflineManager;
import net.flawe.offlinemanager.OfflineManagerAPI;
import net.flawe.offlinemanager.api.ICommand;
import net.flawe.offlinemanager.commands.subs.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static net.flawe.offlinemanager.util.Messages.commandNotFound;
import static net.flawe.offlinemanager.util.Messages.enterSubCommand;
import static net.flawe.offlinemanager.util.Permissions.*;

public class ManagerCommand implements CommandExecutor, TabCompleter {

    private final OfflineManagerAPI api = OfflineManager.getApi();

    public ManagerCommand(OfflineManager plugin) {
        api.getCommandManager().addSubCommand(new InventoryCommand("invsee", "Open player inventory", invsee, plugin));
        api.getCommandManager().addSubCommand(new ReloadCommand("reload", "Reload plugin configuration", reload));
//        api.getCommandManager().addSubCommand(new UpdateConfigCommand("update", "Update configuration file", updateConfig));
        api.getCommandManager().addSubCommand(new TeleportCommand("teleport", "Teleport to player", teleport, new String[]{"tp"}, plugin));
        api.getCommandManager().addSubCommand(new CreativeCommand("creative", "Set creative mode for player", creative, plugin));
        api.getCommandManager().addSubCommand(new SurvivalCommand("survival", "Set survival mode for player", survival, plugin));
        api.getCommandManager().addSubCommand(new SpectatorCommand("spectator", "Set spectator mode for player", spectator, plugin));
        api.getCommandManager().addSubCommand(new AdventureCommand("adventure", "Set adventure mode for player", adventure, plugin));
        api.getCommandManager().addSubCommand(new ClearCommand("clear", "Clear offline player inventory", clear, plugin));
        api.getCommandManager().addSubCommand(new TeleportHereCommand("tphere", "Teleport offline player to yourself", tphere, plugin));
        api.getCommandManager().addSubCommand(new KillPlayerCommand("kill", "Kill offline player", kill, plugin));
        api.getCommandManager().addSubCommand(new HealCommand("heal", "Heal offline player", heal, plugin));
        api.getCommandManager().addSubCommand(new FeedPlayerCommand("feed", "Feed offline player", feed, plugin));
        api.getCommandManager().addSubCommand(new EnderChestCommand("enderchest", "Open offline player enderchest", enderchest, new String[]{"ec"}, plugin));
        api.getCommandManager().addSubCommand(new HelpCommand("help", "Get command list for plugin", usage));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("[OfflineManager] This command can use only player!");
            return true;
        }
        Player p = (Player) sender;
        if (args.length == 0) {
            p.sendMessage(api.getConfigManager().getMessageString(p, enterSubCommand));
            return true;
        }
        if (!hasCommand(args[0])) {
            p.sendMessage(api.getConfigManager().getMessageString(p, commandNotFound));
            return true;
        }
        ICommand command = getCommand(args[0]);
        if (command != null)
            command.execute(p, args);
        return true;
    }

    private ICommand getCommand(String name) {
        if (api.getCommandManager().getSubCommands()
                .stream()
                .noneMatch(cmd -> cmd.getName().equalsIgnoreCase(name) || Arrays.stream(cmd.getAliases()).anyMatch(a -> a.equalsIgnoreCase(name))))
            return null;
        return api.getCommandManager().getSubCommands()
                .stream()
                .filter(cmd -> cmd.getName().equalsIgnoreCase(name) || Arrays.stream(cmd.getAliases()).anyMatch(a -> a.equalsIgnoreCase(name)))
                .findFirst().get();
    }

    private boolean hasCommand(String name) {
        return api.getCommandManager().getSubCommands()
                .stream()
                .anyMatch(cmd -> cmd.getName().equalsIgnoreCase(name) || Arrays.stream(cmd.getAliases()).anyMatch(a -> a.equalsIgnoreCase(name)));
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player))
            return Collections.emptyList();
        Player p = (Player) sender;
        if (args.length == 1 && api.getConfigManager().commandComplete())
            return api.getCommandManager().getSubCommands().stream()
                    .filter(c -> p.hasPermission(c.getPermission()) && c.getName().toLowerCase().startsWith(args[0].toLowerCase()))
                    .flatMap(c -> Arrays.stream(c.getAliases()))
                    .collect(Collectors.toList());
        if (args.length == 2 && api.getConfigManager().playerComplete())
            return api.getStorage().getListForComplete(args);
        if (args.length == 3 && args[0].equalsIgnoreCase("invsee")) {
            return Collections.singletonList("armor");
        }
        return Collections.emptyList();
    }
}

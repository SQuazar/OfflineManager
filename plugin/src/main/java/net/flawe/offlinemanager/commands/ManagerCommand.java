package net.flawe.offlinemanager.commands;

import net.flawe.offlinemanager.OfflineManager;
import net.flawe.offlinemanager.api.OfflineManagerAPI;
import net.flawe.offlinemanager.api.command.ICommand;
import net.flawe.offlinemanager.commands.sub.*;
import net.flawe.offlinemanager.configuration.Messages;
import net.flawe.offlinemanager.configuration.Settings;
import net.flawe.offlinemanager.util.configuration.PlaceholderUtil;
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

import static net.flawe.offlinemanager.util.constants.Permissions.*;
import static net.flawe.offlinemanager.util.ColorUtils.*;

public class ManagerCommand implements CommandExecutor, TabCompleter {

    private final OfflineManagerAPI api = OfflineManager.getApi();
    private final Messages messages = ((OfflineManager) api).getMessages();
    private final Settings settings = ((OfflineManager) api).getSettings();

    public ManagerCommand() {
        api.getCommandManager().addSubCommand(new InventoryCommand("invsee", "Open player inventory", OFFLINEMANAGER_INVSEE));
        api.getCommandManager().addSubCommand(new ReloadCommand("reload", "Reload plugin configuration", OFFLINEMANAGER_RELOAD));
//        api.getCommandManager().addSubCommand(new UpdateConfigCommand("update", "Update configuration file", updateConfig));
        api.getCommandManager().addSubCommand(new TeleportCommand("teleport", "Teleport to player", OFFLINEMANAGER_TELEPORT, new String[]{"tp"}));
        api.getCommandManager().addSubCommand(new CreativeCommand("creative", "Set creative mode for player", OFFLINEMANAGER_CREATIVE));
        api.getCommandManager().addSubCommand(new SurvivalCommand("survival", "Set survival mode for player", OFFLINEMANAGER_SURVIVAL));
        api.getCommandManager().addSubCommand(new SpectatorCommand("spectator", "Set spectator mode for player", OFFLINEMANAGER_SPECTATOR));
        api.getCommandManager().addSubCommand(new AdventureCommand("adventure", "Set adventure mode for player", OFFLINEMANAGER_ADVENTURE));
        api.getCommandManager().addSubCommand(new ClearCommand("clear", "Clear offline player inventory", OFFLINEMANAGER_CLEAR));
        api.getCommandManager().addSubCommand(new TeleportHereCommand("tphere", "Teleport offline player to yourself", OFFLINEMANAGER_TPHERE));
        api.getCommandManager().addSubCommand(new KillPlayerCommand("kill", "Kill offline player", OFFLINEMANAGER_KILL));
        api.getCommandManager().addSubCommand(new HealCommand("heal", "Heal offline player", OFFLINEMANAGER_HEAL));
        api.getCommandManager().addSubCommand(new FeedPlayerCommand("feed", "Feed offline player", OFFLINEMANAGER_FEED));
        api.getCommandManager().addSubCommand(new EnderChestCommand("enderchest", "Open offline player enderchest", OFFLINEMANAGER_ENDERCHEST, new String[]{"ec"}));
        api.getCommandManager().addSubCommand(new HelpCommand("help", "Get command list for plugin", OFFLINEMANAGER_USAGE));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("[OfflineManager] This command can use only player!");
            return true;
        }
        Player p = (Player) sender;
        if (args.length == 0) {
            p.sendMessage(getFormattedString(messages.getEnterSubCommand()
                    .replace("%player%", p.getName())));
            return true;
        }
        if (!hasCommand(args[0])) {
            p.sendMessage(getFormattedString(messages.getCommandNotFound()
                    .replace("%player%", p.getName())
                    .replace("%command%", args[0])));
            return true;
        }
        ICommand command = getCommand(args[0]);
        if (command == null)
            return true;
        if (!sender.hasPermission(command.getPermission())) {
            sender.sendMessage(PlaceholderUtil.fillPlaceholders(getFormattedString(messages.getPermissionDeny()), command.getPlaceholders()));
            return true;
        }
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
                .findFirst().orElse(null);
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
        if (args.length == 1 && settings.isCommandComplete())
            return api.getCommandManager().getSubCommands().stream()
                    .filter(c -> p.hasPermission(c.getPermission()) && c.getName().toLowerCase().startsWith(args[0].toLowerCase()))
                    .flatMap(c -> Arrays.stream(c.getAliases()))
                    .collect(Collectors.toList());
        if (args.length == 2 && settings.isPlayerComplete())
            return api.getStorage().getListForComplete(args);
        if (args.length == 3 && args[0].equalsIgnoreCase("invsee"))
            return Collections.singletonList("armor");
        return Collections.emptyList();
    }
}

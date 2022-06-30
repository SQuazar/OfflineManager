/*
 * Copyright (c) 2021 squazar
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.quazar.offlinemanager.commands;

import net.quazar.offlinemanager.OfflineManager;
import net.quazar.offlinemanager.api.OfflineManagerAPI;
import net.quazar.offlinemanager.api.command.ICommand;
import net.quazar.offlinemanager.api.enums.InventoryType;
import net.quazar.offlinemanager.api.event.command.CommandEvent;
import net.quazar.offlinemanager.commands.sub.*;
import net.quazar.offlinemanager.configuration.Messages;
import net.quazar.offlinemanager.configuration.Settings;
import net.quazar.offlinemanager.placeholders.PlaceholderUtil;
import org.bukkit.Bukkit;
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
import java.util.Optional;
import java.util.stream.Collectors;

import static net.quazar.offlinemanager.util.ColorUtils.getFormattedString;
import static net.quazar.offlinemanager.util.constants.Permissions.*;

public class ManagerCommand implements CommandExecutor, TabCompleter {

    private final OfflineManagerAPI api = OfflineManager.getApi();
    private final Messages messages = ((OfflineManager) api).getMessages();
    private final Settings settings = ((OfflineManager) api).getSettings();

    public ManagerCommand() {
        api.getCommandManager().addSubCommand(new ReloadCommand("reload", "Reload plugin configuration", OFFLINEMANAGER_RELOAD));
        api.getCommandManager().addSubCommand(new TeleportCommand("teleport", "Teleport to player", OFFLINEMANAGER_TELEPORT, new String[]{"tp"}));
        api.getCommandManager().addSubCommand(new GameModeCommand("creative", "Set creative mode for player", OFFLINEMANAGER_CREATIVE));
        api.getCommandManager().addSubCommand(new GameModeCommand("survival", "Set survival mode for player", OFFLINEMANAGER_SURVIVAL));
        api.getCommandManager().addSubCommand(new GameModeCommand("spectator", "Set spectator mode for player", OFFLINEMANAGER_SPECTATOR));
        api.getCommandManager().addSubCommand(new GameModeCommand("adventure", "Set adventure mode for player", OFFLINEMANAGER_ADVENTURE));
        api.getCommandManager().addSubCommand(new ClearCommand("clear", "Clear offline player inventory", OFFLINEMANAGER_CLEAR));
        api.getCommandManager().addSubCommand(new TeleportHereCommand("tphere", "Teleport offline player to yourself", OFFLINEMANAGER_TPHERE));
        api.getCommandManager().addSubCommand(new KillPlayerCommand("kill", "Kill offline player", OFFLINEMANAGER_KILL));
        api.getCommandManager().addSubCommand(new HealCommand("heal", "Heal offline player", OFFLINEMANAGER_HEAL));
        api.getCommandManager().addSubCommand(new FeedPlayerCommand("feed", "Feed offline player", OFFLINEMANAGER_FEED));
        api.getCommandManager().addSubCommand(new HelpCommand("help", "Get command list for plugin", OFFLINEMANAGER_USAGE));
        api.getCommandManager().addSubCommand(new ContainerCommand("invsee", "Open player inventory", OFFLINEMANAGER_INVSEE, InventoryType.DEFAULT, settings.getInventoryConfiguration()));
        api.getCommandManager().addSubCommand(new ContainerCommand("enderchest", "Open offline player enderchest", OFFLINEMANAGER_ENDERCHEST, new String[]{"ec"},
                InventoryType.ENDER_CHEST, settings.getEnderChestConfiguration()));
        api.getCommandManager().addSubCommand(new ContainerCommand("armor", "Open player offline armor inventory", OFFLINEMANAGER_INVSEE_ARMOR, InventoryType.ARMOR, settings.getArmorInventoryConfiguration()));
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
        Optional<ICommand> commandOptional = findCommand(args[0]);
        if (!commandOptional.isPresent()) {
            p.sendMessage(getFormattedString(messages.getCommandNotFound()
                    .replace("%player%", p.getName())
                    .replace("%command%", args[0])));
            return true;
        }
        ICommand command = commandOptional.get();
        if (!sender.hasPermission(command.getPermission())) {
            sender.sendMessage(PlaceholderUtil.fillPlaceholders(getFormattedString(messages.getPermissionDeny()), command.getPlaceholders()));
            return true;
        }
        CommandEvent commandEvent = new CommandEvent(p, command, label);
        Bukkit.getPluginManager().callEvent(commandEvent);
        if (commandEvent.isCancelled())
            return true;
        command.execute(p, args);
        return true;
    }

    private Optional<ICommand> findCommand(String name) {
        return api.getCommandManager().getSubCommands()
                .stream()
                .filter(cmd -> cmd.getName().equalsIgnoreCase(name) || Arrays.stream(cmd.getAliases()).anyMatch(a -> a.equalsIgnoreCase(name)))
                .findFirst();
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player))
            return Collections.emptyList();
        if (!settings.isCommandComplete())
            return Collections.emptyList();
        Player p = (Player) sender;
        if (args.length == 1)
            return api.getCommandManager().getSubCommands()
                    .stream()
                    .filter(c -> p.hasPermission(c.getPermission()) && c.getName().toLowerCase().startsWith(args[0].toLowerCase()))
                    .flatMap(c -> Arrays.stream(c.getAliases()))
                    .collect(Collectors.toList());
        if (args.length == 2)
            return api.getStorage().getListForComplete(args);
        return Collections.emptyList();
    }
}

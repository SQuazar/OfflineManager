package net.quazar.offlinemanager.api.command;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public abstract class CommandBase implements CommandExecutor, TabCompleter {
    @Getter
    private final String name;
    @Getter
    private final String description;
    @Getter
    private final String permission;
    private String[] aliases;
    private final Map<String, CommandBase> subCommands;
    private final boolean onlyPlayerExecute;

    public CommandBase(String name, String description, String permission) {
        this(name, description, permission, true);
    }

    public CommandBase(String name, String description, String permission, String... aliases) {
        this(name, description, permission, true, aliases);
    }

    public CommandBase(String name, String description, String permission, boolean onlyPlayerExecute) {
        this.name = name;
        this.description = description;
        this.permission = permission;
        this.onlyPlayerExecute = onlyPlayerExecute;
        this.subCommands = new HashMap<>();
    }

    public CommandBase(String name, String description, String permission, boolean onlyPlayerExecute, String... aliases) {
        this.name = name;
        this.description = description;
        this.permission = permission;
        this.aliases = aliases;
        this.onlyPlayerExecute = onlyPlayerExecute;
        this.subCommands = new HashMap<>();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player) && onlyPlayerExecute) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cOnly players can execute this command"));
            return true;
        }
        if (!sender.hasPermission(permission)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou don't have permissions for this"));
            return true;
        }
        if (args.length > 0) {
            CommandBase subCommand;
            if ((subCommand = getCommand(args[0])) != null) {
                String[] newArgs = new String[args.length - 1];
                System.arraycopy(args, 1, newArgs, 0, newArgs.length);
                subCommand.onCommand(sender, command, s, newArgs);
                return true;
            }
        }
        execute(sender, args);
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player) && onlyPlayerExecute) return null;
        if (!sender.hasPermission(permission)) return null;
        if (args.length > 0) {
            CommandBase subCommand;
            if ((subCommand = getCommand(args[0])) != null) {
                String[] newArgs = new String[args.length - 1];
                System.arraycopy(args, 1, newArgs, 0, newArgs.length);
                return subCommand.onTabComplete(sender, command, s, newArgs);
            }
        }
        return tabComplete(sender, args);
    }

    public abstract void execute(CommandSender sender, String[] args);

    public List<String> tabComplete(CommandSender sender, String[] args) {
        return subCommands.values().stream()
                .filter(c -> sender.hasPermission(c.permission)
                        && c.name.startsWith(args[0].toLowerCase()))
                .map(CommandBase::getName)
                .collect(Collectors.toList());
    }

    protected void addCommand(CommandBase command) {
        subCommands.put(command.name, command);
    }

    public CommandBase getCommand(String name) {
        CommandBase command = subCommands.get(name);
        if (command == null) {
            find:
            for (CommandBase c : subCommands.values()) {
                if (c.aliases != null) {
                    for (String alias : c.aliases) {
                        if (alias.equals(name)) {
                            command = c;
                            break find;
                        }
                    }
                }
            }
        }

        return command;
    }

    protected Set<CommandBase> getSubCommands() {
        return new HashSet<>(subCommands.values());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(name, ((ICommand) o).getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}

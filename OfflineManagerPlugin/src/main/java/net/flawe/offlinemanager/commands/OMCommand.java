package net.flawe.offlinemanager.commands;

import net.flawe.offlinemanager.OfflineManager;
import net.flawe.offlinemanager.OfflineManagerAPI;
import net.flawe.offlinemanager.api.ICommand;
import org.bukkit.entity.Player;

public abstract class OMCommand implements ICommand {

    private final String name;
    private final String help;
    private final String permission;
    private final String[] aliases;

    protected final OfflineManagerAPI api = OfflineManager.getApi();

    public OMCommand(String name, String help, String permission) {
        this(name, help, permission, new String[0]);
    }

    public OMCommand(String name, String help, String permission, String[] aliases) {
        this.name = name;
        this.help = help;
        this.permission = permission;
        this.aliases = aliases;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getHelp() {
        return help;
    }

    @Override
    public String getPermission() {
        return permission;
    }

    @Override
    public String[] getAliases() {
        String[] a = new String[aliases.length + 1];
        System.arraycopy(aliases, 0, a, 0, aliases.length);
        a[aliases.length] = name;
        return a;
    }

    @Override
    public void execute(Player player, String[] args) {

    }

    @Override
    public boolean hasPermission(Player player) {
        return player.hasPermission(permission);
    }
}

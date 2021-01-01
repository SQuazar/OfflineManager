package net.flawe.offlinemanager.api;

import org.bukkit.entity.Player;

public interface ICommand {
    String getName();

    String getPermission();

    String getHelp();

    String[] getAliases();

    void execute(Player player, String[] args);

    boolean hasPermission(Player player);
}

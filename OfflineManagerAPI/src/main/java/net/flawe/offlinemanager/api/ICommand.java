package net.flawe.offlinemanager.api;

import org.bukkit.entity.Player;

import java.util.Map;

public interface ICommand {
    String getName();

    String getPermission();

    String getHelp();

    String[] getAliases();

    void execute(Player player, String[] args);

    boolean hasPermission(Player player);

    void addPlaceholder(String key, String value);

    void removePlaceholder(String key);

    Map<String, String> getPlaceholders();
}

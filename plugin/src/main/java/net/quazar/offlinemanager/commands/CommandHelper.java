package net.quazar.offlinemanager.commands;

import net.quazar.offlinemanager.OfflineManager;
import net.quazar.offlinemanager.api.IPlaceholder;
import net.quazar.offlinemanager.placeholders.PlaceholderUtil;
import org.bukkit.entity.Player;

import java.util.Collection;

public final class CommandHelper {
    public static void sendPlayerMessage(Player player, String message, Collection<IPlaceholder> placeholders) {
        message = OfflineManager.getApi().getConfigManager().fillMessage(player, message);
        player.sendMessage(PlaceholderUtil.fillPlaceholders(message, placeholders));
    }
}

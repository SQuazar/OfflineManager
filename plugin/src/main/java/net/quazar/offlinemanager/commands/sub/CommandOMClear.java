package net.quazar.offlinemanager.commands.sub;

import net.quazar.offlinemanager.OfflineManager;
import net.quazar.offlinemanager.api.IPlaceholder;
import net.quazar.offlinemanager.api.OfflineManagerAPI;
import net.quazar.offlinemanager.api.command.CommandBase;
import net.quazar.offlinemanager.api.data.entity.IPlayerData;
import net.quazar.offlinemanager.api.enums.SavePlayerType;
import net.quazar.offlinemanager.api.event.inventory.ClearOfflineEnderchestEvent;
import net.quazar.offlinemanager.api.event.inventory.ClearOfflineInventoryEvent;
import net.quazar.offlinemanager.commands.CommandHelper;
import net.quazar.offlinemanager.configuration.Messages;
import net.quazar.offlinemanager.configuration.Settings;
import net.quazar.offlinemanager.placeholders.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public final class CommandOMClear extends CommandBase {
    private final OfflineManagerAPI api;
    private final Settings settings;
    private final Messages messages;

    private final Set<IPlaceholder> placeholders = new HashSet<>();

    public CommandOMClear(OfflineManager plugin, String name, String description, String permission, boolean onlyPlayerExecute) {
        super(name, description, permission, onlyPlayerExecute);
        this.api = plugin;
        this.settings = plugin.getSettings();
        this.messages = plugin.getMessages();
        placeholders.add(Placeholder.of("%function%", "Clear"));
        placeholders.add(Placeholder.of("%permission%", permission));
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!settings.getClearConfiguration().enabled()) {
            CommandHelper.sendMessage(sender, messages.getFunctionDisabled(), placeholders);
            return;
        }
        if (args.length == 0) {
            CommandHelper.sendMessage(sender, messages.getEnterNickname(), placeholders);
            return;
        }

        String playerName = args[0];
        placeholders.add(Placeholder.of("%target%", playerName));

        Player target = Bukkit.getPlayerExact(playerName);
        if (target != null && target.isOnline()) {
            CommandHelper.sendMessage(sender, messages.getPlayerIsOnline(), placeholders);
            return;
        }

        if (!api.getStorage().hasPlayer(playerName)) {
            CommandHelper.sendMessage(sender, messages.getPlayerNotFound(), placeholders);
            return;
        }

        IPlayerData playerData = api.getPlayerData(playerName);
        if (args.length == 1) {
            ClearOfflineInventoryEvent event = new ClearOfflineInventoryEvent(playerData);
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled())
                return;
            playerData.getInventory().clear();
            playerData.save(SavePlayerType.INVENTORY);
            CommandHelper.sendMessage(sender, messages.getClearInventory(), placeholders);
        } else if (args[1].equalsIgnoreCase("ec")) {
            ClearOfflineEnderchestEvent event = new ClearOfflineEnderchestEvent(playerData);
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled())
                return;
            playerData.getEnderChest().getEnderChest().clear();
            playerData.save(SavePlayerType.ENDER_CHEST);
            CommandHelper.sendMessage(sender, messages.getClearEnderChest(), placeholders);
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) return api.getStorage().getListForComplete(args[0]);
        if (args.length == 2) return Collections.singletonList("ec");
        return null;
    }
}

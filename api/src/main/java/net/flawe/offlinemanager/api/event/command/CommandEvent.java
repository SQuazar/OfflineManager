package net.flawe.offlinemanager.api.event.command;

import net.flawe.offlinemanager.api.command.ICommand;
import net.flawe.offlinemanager.api.event.OfflineManagerEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

/**
 * Called when player execute the command
 * @author flawe
 */
public class CommandEvent extends OfflineManagerEvent implements Cancellable {

    private final Player player;
    private final ICommand command;
    private final String message;
    private boolean cancelled;

    /**
     *
     * @param player who execute the command
     * @param command the command that was executed
     * @param message the full command message
     */
    public CommandEvent(Player player, ICommand command, String message) {
        this.player = player;
        this.command = command;
        this.message = message;
    }

    /**
     * Gets the command executor
     * @return command sender
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the command
     * @return the executable command
     */
    public ICommand getCommand() {
        return command;
    }

    /**
     * Gets the command label
     * @return the full player message
     */
    public String getMessage() {
        return message;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

}

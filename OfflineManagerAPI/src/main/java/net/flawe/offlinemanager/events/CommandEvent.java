package net.flawe.offlinemanager.events;

import net.flawe.offlinemanager.api.ICommand;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

/**
 * Not supported in beta version
 * @author flaweoff
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
     *
     * @return The player command sender
     */
    public Player getPlayer() {
        return player;
    }

    /**
     *
     * @return The executable command
     */
    public ICommand getCommand() {
        return command;
    }

    /**
     *
     * @return The full player message
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

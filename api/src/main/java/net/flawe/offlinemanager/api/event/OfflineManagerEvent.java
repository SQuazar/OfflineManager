package net.flawe.offlinemanager.api.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Parent class for all plugin events
 * @author flawe
 */
public class OfflineManagerEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    public OfflineManagerEvent(boolean async) {
        super(async);
    }

    public OfflineManagerEvent() {

    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

}

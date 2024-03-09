package net.quazar.offlinemanager.api.event.inventory;

import lombok.Getter;
import lombok.Setter;
import net.quazar.offlinemanager.api.data.entity.IPlayerData;
import net.quazar.offlinemanager.api.event.OfflineManagerEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;

/**
 * Called when offline player enderchest is cleared
 * @author quazar
 */
public class ClearOfflineEnderchestEvent extends OfflineManagerEvent implements Cancellable {
    private final IPlayerData playerData;
    @Getter
    @Setter
    private boolean cancelled;

    public ClearOfflineEnderchestEvent(@NotNull IPlayerData playerData) {
        this.playerData = playerData;
    }

    /**
     * Gets the owner of enderchest
     * @return inventory enderchest
     */
    @NotNull
    public IPlayerData getPlayerData() {
        return playerData;
    }
}

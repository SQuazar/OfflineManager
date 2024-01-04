package net.quazar.offlinemanager.api.event.inventory;

import lombok.Getter;
import lombok.Setter;
import net.quazar.offlinemanager.api.data.entity.IPlayerData;
import net.quazar.offlinemanager.api.event.OfflineManagerEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;

/**
 * Called when the player clears the offline player's enderchest
 * @author quazar
 */
public class ClearOfflineEnderchestEvent extends OfflineManagerEvent implements Cancellable {
    private final Player player;
    private final IPlayerData playerData;
    @Getter
    @Setter
    private boolean cancelled;

    public ClearOfflineEnderchestEvent(@NotNull Player player, @NotNull IPlayerData playerData) {
        this.player = player;
        this.playerData = playerData;
    }

    /**
     * Gets the player who cleared the enderchest
     * @return the player who cleared the enderchest
     */
    @NotNull
    public Player getPlayer() {
        return player;
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

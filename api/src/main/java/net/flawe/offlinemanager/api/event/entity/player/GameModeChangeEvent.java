/*
 * Copyright (c) 2021 flaweoff
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.flawe.offlinemanager.api.event.entity.player;

import lombok.Getter;
import lombok.Setter;
import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.event.OfflineManagerEvent;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;

/**
 * Called when player was changed the offline player's game mode
 * @author flawe
 */
public class GameModeChangeEvent extends OfflineManagerEvent implements Cancellable {
    private final IPlayerData playerData;
    private final Player player;
    private final GameMode gameMode;
    @Getter @Setter
    private boolean cancelled;

    public GameModeChangeEvent(@NotNull IPlayerData playerData, @NotNull Player player, @NotNull GameMode gameMode) {
        this.playerData = playerData;
        this.player = player;
        this.gameMode = gameMode;
    }

    /**
     * Gets a player whose game mode is changed
     * @return player whose game mode is changed
     */
    @NotNull
    public IPlayerData getPlayerData() {
        return playerData;
    }

    /**
     * Gets a player who change game mode for offline player
     * @return player who change game mode for offline player
     */
    @NotNull
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the new game mode
     * @return new game mode
     */
    @NotNull
    public GameMode getGameMode() {
        return gameMode;
    }
}

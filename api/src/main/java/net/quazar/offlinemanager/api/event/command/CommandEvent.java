/*
 * Copyright (c) 2021 squazar
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

package net.quazar.offlinemanager.api.event.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.quazar.offlinemanager.api.command.ICommand;
import net.quazar.offlinemanager.api.event.OfflineManagerEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

/**
 * Called when player execute the command
 * @author quazar
 */
@RequiredArgsConstructor
public class CommandEvent extends OfflineManagerEvent implements Cancellable {

    private final Player player;
    private final ICommand command;
    private final String message;
    @Getter @Setter
    private boolean cancelled;

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

}

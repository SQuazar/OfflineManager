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

package net.quazar.offlinemanager.api.command;

import net.quazar.offlinemanager.api.IPlaceholder;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Interface for implementing all commands
 *
 * @author quazar
 */
public interface ICommand {
    /**
     * Gets the command name
     * @return command name
     */
    String getName();

    /**
     * Gets the command permission
     * @return command permission
     */
    String getPermission();

    /**
     * Gets the help for command
     * @return command help information
     */
    String getHelp();

    /**
     * Gets the command aliases
     * @return command aliases
     */
    String[] getAliases();

    /**
     * Executes the command
     * @param player the command executor
     * @param args   command arguments
     */
    void execute(Player player, String[] args);

    /**
     * Checks if player has permission on command
     * @param player command sender
     * @return true if player has permission on command
     */
    default boolean hasPermission(Player player) {
        return player.hasPermission(getPermission());
    }

    /**
     * Adds the placeholder to command
     * @param key   placeholder key
     * @param value placeholder value
     */
    @Deprecated
    default void addPlaceholder(String key, String value) { }

    /**
     * Adds the placeholder to command
     * @param placeholder placeholder
     */
    default void addPlaceholder(IPlaceholder placeholder) { }

    /**
     * Adds the placeholders to command
     * @param placeholders placeholders
     */
    default void addPlaceholders(IPlaceholder... placeholders) { }

    /**
     * Removes the placeholder from command
     * @param key placeholder key
     */
    default void removePlaceholder(String key) { }

    /**
     * Removes the placeholder from command
     * @param placeholder placeholder
     */
    default void removePlaceholder(IPlaceholder placeholder) { }

    /**
     * Gets the placeholders as map
     * @return placeholders as map
     */
    default Map<String, String> getPlaceholdersAsMap() {
        return new HashMap<>();
    }

    /**
     * Gets the placeholders set collection
     * @return placeholders set collection
     */
    default Set<IPlaceholder> getPlaceholders() {
        return new HashSet<>();
    }

}

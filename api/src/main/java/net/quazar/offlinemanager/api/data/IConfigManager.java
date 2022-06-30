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

package net.quazar.offlinemanager.api.data;

import org.bukkit.entity.Player;

/**
 * Helper class for interactions with configuration
 *
 * @author quazar
 */
public interface IConfigManager {

    /**
     * Method for configuration update (Not working in current version)
     */
    void update();

    /**
     * Gets the colorized and filled PAPI placeholders (if enabled) in message
     *
     * @param player player for %player% placeholder
     * @param s      message to be filled
     * @return formatted message with color and PAPI placeholders
     */
    @Deprecated
    String getMessage(Player player, String s);

    /**
     * Reload the plugin configuration
     */
    void reloadConfig();

    /**
     * Gets the filled message with placeholders
     * @param player command sender / placeholder owner
     * @param s message for fill
     * @return filled message
     */
    String fillMessage(Player player, String s);

    /**
     * Gets the colorized message from parameter
     *
     * @param s message to be formatting
     * @return formatted message from parameter
     */
    @Deprecated
    String getMessage(String s);


}

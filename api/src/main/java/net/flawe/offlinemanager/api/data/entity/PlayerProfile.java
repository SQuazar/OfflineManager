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

package net.flawe.offlinemanager.api.data.entity;

import java.util.Objects;
import java.util.UUID;

/**
 * Player profile class
 * @since 3.0.3
 * @author flawe
 */
public class PlayerProfile {

    private final String name;
    private final UUID uuid;

    /**
     * Player profile constructor
     * @param name player name
     * @param uuid player uuid
     */
    private PlayerProfile(String name, UUID uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    /**
     * Gets player name
     * @return player name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets player uuid
     * @return player uuid
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Creates player profile
     * @param uuid player uuid
     * @param name player name
     * @return player profile
     */
    public static PlayerProfile of(UUID uuid, String name) {
        return new PlayerProfile(name, uuid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerProfile that = (PlayerProfile) o;
        return Objects.equals(name, that.name) && Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, uuid);
    }
}

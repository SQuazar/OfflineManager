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

package net.flawe.offlinemanager.api.util.v1_17_R1;

import net.flawe.offlinemanager.api.OfflineManagerAPI;
import net.flawe.offlinemanager.api.data.INMSManager;
import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.data.entity.PlayerProfile;
import net.flawe.offlinemanager.api.util.v1_17_R1.data.PlayerData;
import net.minecraft.server.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_17_R1.CraftServer;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class NMSManager implements INMSManager {
    private final OfflineManagerAPI api;

    public NMSManager(OfflineManagerAPI api) {
        this.api = api;
    }

    @Override
    public List<String> getSeenPlayers() {
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        return Arrays.asList(server.k.getSeenPlayers());
    }

    @Override
    public IPlayerData getPlayerData(@NotNull UUID uuid) {
        return new PlayerData(uuid, api);
    }

    @Override
    public IPlayerData getPlayerData(@NotNull String name) {
        return new PlayerData(name, api);
    }

    @Override
    public IPlayerData getPlayerData(@NotNull PlayerProfile profile) {
        return new PlayerData(profile, api);
    }
}

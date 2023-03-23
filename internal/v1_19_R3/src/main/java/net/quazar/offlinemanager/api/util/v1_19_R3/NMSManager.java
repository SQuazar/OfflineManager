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

package net.quazar.offlinemanager.api.util.v1_19_R3;

import net.minecraft.server.MinecraftServer;
import net.quazar.offlinemanager.api.OfflineManagerAPI;
import net.quazar.offlinemanager.api.data.INMSManager;
import net.quazar.offlinemanager.api.data.entity.IPlayerData;
import net.quazar.offlinemanager.api.data.entity.PlayerProfile;
import net.quazar.offlinemanager.api.util.v1_19_R3.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class NMSManager implements INMSManager {
    private final MinecraftServer server;
    private final OfflineManagerAPI api;

    public NMSManager(OfflineManagerAPI api) {
        this.api = api;
        this.server = ((CraftServer) Bukkit.getServer()).getServer();
    }

    @Override
    public List<String> getSeenPlayers() {
        return Arrays.asList(server.playerDataStorage.getSeenPlayers());
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

    @Override
    public boolean removePlayerData(UUID uuid) {
        File file = new File(server.playerDataStorage.getPlayerDir(), uuid.toString() + ".dat");
        if (!file.exists()) return false;
        return file.delete();
    }
}

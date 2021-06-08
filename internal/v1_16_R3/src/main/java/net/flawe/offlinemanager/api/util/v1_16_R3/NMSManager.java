package net.flawe.offlinemanager.api.util.v1_16_R3;

import net.flawe.offlinemanager.api.OfflineManagerAPI;
import net.flawe.offlinemanager.api.data.INMSManager;
import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.entity.IUser;
import net.flawe.offlinemanager.api.util.v1_16_R3.data.OfflineUser;
import net.flawe.offlinemanager.api.util.v1_16_R3.data.PlayerData;
import net.minecraft.server.v1_16_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
        return Arrays.asList(server.worldNBTStorage.getSeenPlayers());
    }

    @Override
    public IUser getUser(String s) {
        return new OfflineUser((Plugin) api, s);
    }

    @Override
    public IUser getUser(UUID uuid) {
        return new OfflineUser((Plugin) api, uuid);
    }

    @Override
    public @Nullable IPlayerData getPlayerData(@NotNull UUID uuid) {
        return new PlayerData(uuid, api);
    }

    @Override
    public @Nullable IPlayerData getPlayerData(@NotNull String name) {
        return new PlayerData(name, api);
    }

}

package net.flawe.offlinemanager.api.util.v1_16_R1;

import net.flawe.offlinemanager.api.data.INMSManager;
import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.entity.IUser;
import net.minecraft.server.v1_16_R1.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R1.CraftServer;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class NMSManager implements INMSManager {

    private final Plugin plugin;

    public NMSManager(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> getSeenPlayers() {
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        return Arrays.asList(server.worldNBTStorage.getSeenPlayers());
    }

    @Override
    public IUser getUser(String s) {
        return new OfflineUser(plugin, s);
    }

    @Override
    public IUser getUser(UUID uuid) {
        return new OfflineUser(plugin, uuid);
    }

    @Override
    public @Nullable IPlayerData getPlayerData(@NotNull UUID uuid) {
        return new PlayerData(uuid, plugin);
    }

    @Override
    public @Nullable IPlayerData getPlayerData(@NotNull String name) {
        return new PlayerData(name, plugin);
    }

}

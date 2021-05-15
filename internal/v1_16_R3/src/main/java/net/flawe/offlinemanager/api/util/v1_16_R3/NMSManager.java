package net.flawe.offlinemanager.api.util.v1_16_R3;

import net.flawe.offlinemanager.api.data.INMSManager;
import net.flawe.offlinemanager.api.IUser;
import net.minecraft.server.v1_16_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.plugin.Plugin;

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

}

package net.flawe.offlinemanager.api.util.v1_16_R3;

import net.flawe.offlinemanager.api.INMSManager;
import net.minecraft.server.v1_16_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;

import java.util.Arrays;
import java.util.List;

public class NMSManager implements INMSManager {
    @Override
    public List<String> getSeenPlayers() {
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        return Arrays.asList(server.worldNBTStorage.getSeenPlayers());
    }

}

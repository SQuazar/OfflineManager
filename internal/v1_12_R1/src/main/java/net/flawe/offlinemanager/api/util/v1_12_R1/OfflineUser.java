package net.flawe.offlinemanager.api.util.v1_12_R1;

import com.mojang.authlib.GameProfile;
import net.flawe.offlinemanager.api.IUser;
import net.flawe.offlinemanager.api.enums.SavePlayerType;
import net.flawe.offlinemanager.api.events.data.LoadPlayerEvent;
import net.flawe.offlinemanager.api.events.data.SavePlayerEvent;
import net.flawe.offlinemanager.api.inventory.IArmorInventory;
import net.flawe.offlinemanager.api.inventory.IEnderChest;
import net.flawe.offlinemanager.api.inventory.IInventory;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class OfflineUser implements IUser {

    private final OfflinePlayer offlinePlayer;
    private final Player player;
    private final NBTTagCompound nbtTagCompound;
    private final UUID uuid;

    public OfflineUser(Plugin plugin, String name) {
        this(plugin, Bukkit.getOfflinePlayer(name));
    }

    public OfflineUser(Plugin plugin, UUID uuid) {
        this(plugin, Bukkit.getOfflinePlayer(uuid));
    }

    public OfflineUser(Plugin plugin, OfflinePlayer offlinePlayer) {
        this.offlinePlayer = offlinePlayer;
        this.player = getEntityPlayer().getBukkitEntity().getPlayer();
        if (player != null) {
            LoadPlayerEvent event = new LoadPlayerEvent(player);
            try {
                Bukkit.getPluginManager().callEvent(event);
            } catch (IllegalStateException e) {
                Bukkit.getScheduler().runTask(plugin, () -> Bukkit.getPluginManager().callEvent(event));
            }
            player.loadData();
        }
        this.uuid = offlinePlayer.getUniqueId();
        nbtTagCompound = ((ServerNBTManager) getMinecraftServer().getWorld().getDataManager()).getPlayerData(uuid.toString());
    }

    private MinecraftServer getMinecraftServer() {
        return ((CraftServer) Bukkit.getServer()).getServer();
    }

    private EntityPlayer getEntityPlayer() {
        GameProfile profile = new GameProfile(offlinePlayer.getUniqueId(), offlinePlayer.getName());
        return new EntityPlayer(getMinecraftServer(), getWorldServer(), profile, new PlayerInteractManager(getWorldServer()));
    }

    private WorldServer getWorldServer() {
        return getMinecraftServer().getWorldServer(0);
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public OfflinePlayer getOfflinePlayer() {
        return offlinePlayer;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public Location getLocation() {
        NBTTagList pos = (NBTTagList) nbtTagCompound.get("Pos");
        NBTTagList rotation = (NBTTagList) nbtTagCompound.get("Rotation");
        if (pos == null || rotation == null)
            return player.getLocation();
        double x = pos.f(0);
        double y = pos.f(1);
        double z = pos.f(2);
        float yaw = rotation.g(0);
        float pitch = rotation.g(1);
        World world = Bukkit.getWorld(new UUID(nbtTagCompound.getLong("WorldUUIDMost"), nbtTagCompound.getLong("WorldUUIDLeast")));
        return new Location(world, x, y, z, yaw, pitch);
    }

    @Override
    public IInventory getInventory() {
        return new OfflineInventory(player);
    }

    @Override
    public IEnderChest getEnderChest() {
        return new OfflineEnderChest(player);
    }

    @Override
    public IArmorInventory getArmorInventory() {
        return new ArmorInventory(player);
    }

    @Override
    public GameMode getGameMode() {
        return player.getGameMode();
    }

    @Override
    public void kill() {
        ((CraftPlayer) player).getHandle().setHealth(0);
    }

    @Override
    public void setGameMode(GameMode gameMode) {
        int value = gameMode.getValue();
        nbtTagCompound.setInt("playerGameType", value);
        tagSave(nbtTagCompound, SavePlayerType.GAMEMODE);
    }

    @Override
    public void teleport(Location location) {
        if (nbtTagCompound == null)
            return;
        NBTTagList pos = new NBTTagList();
        NBTTagList rotation = new NBTTagList();
        pos.add(new NBTTagDouble(location.getX()));
        pos.add(new NBTTagDouble(location.getY()));
        pos.add(new NBTTagDouble(location.getZ()));
        rotation.add(new NBTTagFloat(location.getYaw()));
        rotation.add(new NBTTagFloat(location.getPitch()));
        nbtTagCompound.set("Pos", pos);
        nbtTagCompound.set("Rotation", rotation);
        nbtTagCompound.setLong("WorldUUIDMost", location.getWorld().getUID().getMostSignificantBits());
        nbtTagCompound.setLong("WorldUUIDLeast", location.getWorld().getUID().getLeastSignificantBits());
        tagSave(nbtTagCompound, SavePlayerType.LOCATION);
    }

    @Override
    public void teleport(Player player) {
        teleport(player.getLocation());
    }

    @Override
    public void save() {
        SavePlayerEvent event = new SavePlayerEvent(player, SavePlayerType.DEFAULT);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled())
            return;
        player.saveData();
    }

    @Override
    public void save(SavePlayerType type) {
        SavePlayerEvent event = new SavePlayerEvent(player, type);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled())
            return;
        player.saveData();
    }

    private void tagSave(NBTTagCompound tag, SavePlayerType type) {
        SavePlayerEvent event = new SavePlayerEvent(player, type);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled())
            return;
        try (FileOutputStream stream = new FileOutputStream(new File(((ServerNBTManager) getMinecraftServer().getWorld().getDataManager()).getPlayerDir(), uuid + ".dat"))) {
            NBTCompressedStreamTools.a(tag, stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

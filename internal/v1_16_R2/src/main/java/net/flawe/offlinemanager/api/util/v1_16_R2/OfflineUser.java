package net.flawe.offlinemanager.api.util.v1_16_R2;

import com.mojang.authlib.GameProfile;
import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.entity.IUser;
import net.flawe.offlinemanager.api.enums.SavePlayerType;
import net.flawe.offlinemanager.api.events.data.LoadPlayerEvent;
import net.flawe.offlinemanager.api.events.data.SavePlayerEvent;
import net.flawe.offlinemanager.api.inventory.IArmorInventory;
import net.flawe.offlinemanager.api.inventory.IEnderChest;
import net.flawe.offlinemanager.api.inventory.IInventory;
import net.minecraft.server.v1_16_R2.*;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_16_R2.CraftServer;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class OfflineUser implements IUser {

    private final Plugin plugin;
    private final OfflinePlayer offlinePlayer;
    private final Player player;
    private final WorldNBTStorage storage = getWorldServer().getMinecraftServer().getPlayerList().playerFileData;
    private final UUID uuid;
    private final NBTTagCompound tag;

    @Deprecated
    public OfflineUser(Plugin plugin, String name) {
        this(plugin, Bukkit.getOfflinePlayer(name));
    }

    @Deprecated
    public OfflineUser(Plugin plugin, UUID uuid) {
        this(plugin, Bukkit.getOfflinePlayer(uuid));
    }

    @Deprecated
    public OfflineUser(Plugin plugin, OfflinePlayer offlinePlayer) {
        this.plugin = plugin;
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
        this.tag = storage.getPlayerData(offlinePlayer.getUniqueId().toString());
    }

    public OfflineUser(Plugin plugin, UUID uuid, NBTTagCompound tagCompound) {
        this.plugin = plugin;
        this.offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        EntityPlayer entityPlayer = getEntityPlayer();
        entityPlayer.loadData(tagCompound);
        entityPlayer.load(tagCompound);
        this.player = entityPlayer.getBukkitEntity().getPlayer();
        this.tag = tagCompound;
        this.uuid = uuid;
    }

    private MinecraftServer getMinecraftServer() {
        return ((CraftServer) Bukkit.getServer()).getServer();
    }

    private EntityPlayer getEntityPlayer() {
        GameProfile profile = new GameProfile(offlinePlayer.getUniqueId(), offlinePlayer.getName());
        return new EntityPlayer(getMinecraftServer(), getWorldServer(), profile, new PlayerInteractManager(getWorldServer()));
    }

    private WorldServer getWorldServer() {
        return getMinecraftServer().getWorldServer(World.OVERWORLD);
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
        NBTTagCompound tag = storage.getPlayerData(offlinePlayer.getUniqueId().toString());
        NBTTagList pos = (NBTTagList) tag.get("Pos");
        NBTTagList rotation = (NBTTagList) tag.get("Rotation");
        if (pos == null || rotation == null)
            return player.getLocation();
        double x = pos.h(0);
        double y = pos.h(1);
        double z = pos.h(2);
        float yaw = rotation.i(0);
        float pitch = rotation.i(1);
        org.bukkit.World world = Bukkit.getWorld(new UUID(tag.getLong("WorldUUIDMost"), tag.getLong("WorldUUIDLeast")));
        return new Location(world, x, y, z, yaw, pitch);
    }

    @Override
    public IInventory getInventory() {
        return new OfflineInventory(player);
    }

    @Override
    public IEnderChest getEnderChest() {
        return new OfflineEnderChest(player.getEnderChest(), tag);
    }

    @Override
    public IArmorInventory getArmorInventory() {
        return new ArmorInventory(this);
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
        NBTTagCompound tag = storage.getPlayerData(uuid.toString());
        tag.setInt("playerGameType", value);
        tagSave(tag, SavePlayerType.GAMEMODE);
    }

    @Override
    public void teleport(Location location) {
        NBTTagCompound tag = storage.getPlayerData(uuid.toString());
        if (tag == null)
            return;
        NBTTagList pos = new NBTTagList();
        NBTTagList rotation = new NBTTagList();
        pos.add(0, NBTTagDouble.a(location.getX()));
        pos.add(1, NBTTagDouble.a(location.getY()));
        pos.add(2, NBTTagDouble.a(location.getZ()));
        rotation.add(0, NBTTagFloat.a(location.getYaw()));
        rotation.add(1, NBTTagFloat.a(location.getPitch()));
        tag.set("Pos", pos);
        tag.set("Rotation", rotation);
        tag.setLong("WorldUUIDMost", location.getWorld().getUID().getMostSignificantBits());
        tag.setLong("WorldUUIDLeast", location.getWorld().getUID().getLeastSignificantBits());
        WorldServer w = ((CraftWorld) location.getWorld()).getHandle();
        tag.setString("Dimension", w.getDimensionKey().a().toString());
        tagSave(tag, SavePlayerType.LOCATION);
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

    @Override
    public IPlayerData getPlayerData() {
        return new PlayerData(uuid, plugin);
    }

    private void tagSave(NBTTagCompound tag, SavePlayerType type) {
        SavePlayerEvent event = new SavePlayerEvent(player, type);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled())
            return;
        try (FileOutputStream stream = new FileOutputStream(new File(storage.getPlayerDir(), uuid + ".dat"))) {
            NBTCompressedStreamTools.a(tag, stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

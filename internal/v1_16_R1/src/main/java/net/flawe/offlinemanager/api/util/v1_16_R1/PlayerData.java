package net.flawe.offlinemanager.api.util.v1_16_R1;

import com.mojang.authlib.GameProfile;
import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.entity.IUser;
import net.flawe.offlinemanager.api.enums.SavePlayerType;
import net.flawe.offlinemanager.api.inventory.AbstractPlayerInventory;
import net.flawe.offlinemanager.api.inventory.IArmorInventory;
import net.flawe.offlinemanager.api.inventory.IEnderChest;
import net.minecraft.server.v1_16_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R1.CraftServer;
import org.bukkit.craftbukkit.v1_16_R1.inventory.CraftInventoryPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class PlayerData implements IPlayerData {

    private final Plugin plugin;
    private final UUID uuid;
    private final String name;
    private final WorldNBTStorage worldNBTStorage;
    private final NBTTagCompound tag;
    private final File playerDir;
    private final net.flawe.offlinemanager.api.util.v1_16_R1.PlayerInventory playerInventory;
    private final ArmorInventory armorInventory;
    private final IEnderChest enderChest;

    public PlayerData(String name, Plugin plugin) {
        //noinspection deprecation
        this(Bukkit.getOfflinePlayer(name).getUniqueId(), plugin);
    }

    public PlayerData(UUID uuid, Plugin plugin) {
        this(uuid, ((CraftServer) Bukkit.getServer()).getHandle().playerFileData.getPlayerData(uuid.toString()), plugin);
    }

    public PlayerData(UUID uuid, NBTTagCompound tagCompound, Plugin plugin) {
        this.plugin = plugin;
        this.uuid = uuid;
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer worldServer = server.getWorldServer(net.minecraft.server.v1_16_R1.World.OVERWORLD);
        if (worldServer == null)
            throw new NullPointerException("Overworld cannot be null!");
        GameProfile profile = new GameProfile(uuid, Bukkit.getOfflinePlayer(uuid).getName());
        EntityPlayer entityPlayer = new EntityPlayer(server, worldServer, profile, new PlayerInteractManager(worldServer));
        entityPlayer.load(tagCompound);
        entityPlayer.loadData(tagCompound);
        this.name = entityPlayer.getName();
        this.worldNBTStorage = server.worldNBTStorage;
        this.tag = tagCompound;
        this.playerDir = worldNBTStorage.getPlayerDir();
        NBTTagList inventoryList = (NBTTagList) tag.get("Inventory");
        net.minecraft.server.v1_16_R1.PlayerInventory virtual = new net.minecraft.server.v1_16_R1.PlayerInventory(entityPlayer);
        virtual.b(inventoryList);
        this.playerInventory = new net.flawe.offlinemanager.api.util.v1_16_R1.PlayerInventory(new CraftInventoryPlayer(virtual), tag);
        this.armorInventory = new ArmorInventory(this);
        this.enderChest = new OfflineEnderChest(Bukkit.createInventory(null, InventoryType.ENDER_CHEST), tag);
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public AbstractPlayerInventory getInventory() {
        return playerInventory;
    }

    @Override
    public IArmorInventory getArmorInventory() {
        return armorInventory;
    }

    @Override
    public IEnderChest getEnderChest() {
        return enderChest;
    }

    @Override
    public float getFallDistance() {
        return tag.getFloat("FallDistance");
    }

    @Override
    public void setFallDistance(float distance) {
        tag.set("FallDistance", NBTTagFloat.a(distance));
    }

    @Override
    public short getFireTicks() {
        return tag.getShort("Fire");
    }

    @Override
    public void setFireTicks(short ticks) {
        tag.set("Fire", NBTTagShort.a(ticks));
    }

    @Override
    public float getExhaustion() {
        return tag.getFloat("foodExhaustionLevel");
    }

    @Override
    public void setExhaustion(float exhaustion) {
        tag.set("foodExhaustionLevel", NBTTagFloat.a(exhaustion));
    }

    @Override
    public int getFoodLevel() {
        return tag.getInt("foodLevel");
    }

    @Override
    public void setFoodLevel(int level) {
        tag.set("foodLevel", NBTTagInt.a(level));
    }

    @Override
    public float getSaturation() {
        return tag.getFloat("foodSaturationLevel");
    }

    @Override
    public void setSaturation(float saturation) {
        tag.set("foodSaturationLevel", NBTTagFloat.a(saturation));
    }

    @Override
    public float getHealth() {
        return tag.getFloat("Health");
    }

    @Override
    public void setHealth(float health) {
        tag.set("Health", NBTTagFloat.a(health));
    }

    @Override
    public boolean isInvulnerable() {
        return tag.getBoolean("Invulnerable");
    }

    @Override
    public void setInvulnerable(boolean b) {
        tag.set("Invulnerable", NBTTagByte.a(b));
    }

    @Override
    public boolean isOnGround() {
        return tag.getBoolean("OnGround");
    }

    @Override
    public GameMode getGameMode() {
        return GameMode.getByValue(tag.getInt("playerGameType"));
    }

    @Override
    public void setGameMode(GameMode gameMode) {
        tag.set("playerGameType", NBTTagInt.a(gameMode.getValue()));
    }

    @Override
    public int getPortalCooldown() {
        return tag.getInt("PortalCooldown");
    }

    @Override
    public void setPortalCooldown(int cooldown) {
        tag.set("PortalCooldown", NBTTagInt.a(cooldown));
    }

    @Override
    public int getSelectedSlot() {
        return tag.getInt("SelectedItemSlot");
    }

    @Override
    public void setSelectedSlot(int slot) {
        tag.set("SelectedItemSlot", NBTTagInt.a(slot));
    }

    @Override
    public World getWorld() {
        long most = tag.getLong("WorldUUIDMost");
        long least = tag.getLong("WorldUUIDLeast");
        return Bukkit.getWorld(new UUID(most, least));
    }

    @Override
    public Location getLocation() {
        NBTTagList position = (NBTTagList) tag.get("Pos");
        NBTTagList rotation = (NBTTagList) tag.get("Rotation");
        double x = 0, y = 0, z = 0;
        if (position != null) {
            x = position.h(0);
            y = position.h(1);
            z = position.h(2);
        }
        float yaw = 0, pitch = 0;
        if (rotation != null) {
            yaw = rotation.i(0);
            pitch = rotation.i(1);
        }
        World world = getWorld();
        return new Location(world, x, y, z, yaw, pitch);
    }

    @Override
    public void setLocation(Location location) {
        if (location.getWorld() == null)
            return;
        NBTTagList position = new NBTTagList();
        position.add(0, NBTTagDouble.a(location.getX()));
        position.add(1, NBTTagDouble.a(location.getY()));
        position.add(2, NBTTagDouble.a(location.getZ()));
        NBTTagList rotation = new NBTTagList();
        rotation.add(0, NBTTagFloat.a(location.getYaw()));
        rotation.add(1, NBTTagFloat.a(location.getPitch()));
        tag.set("Pos", position);
        tag.set("Rotation", rotation);
        UUID worldUID = location.getWorld().getUID();
        tag.set("WorldUUIDLeast", NBTTagLong.a(worldUID.getLeastSignificantBits()));
        tag.set("WorldUUIDMost", NBTTagLong.a(worldUID.getMostSignificantBits()));
    }

    @Override
    public void setLocation(Entity entity) {
        setLocation(entity.getLocation());
    }

    @Override
    public int getExpLevel() {
        return tag.getInt("XpLevel");
    }

    @Override
    public void setExpLevel(int level) {
        tag.set("XpLevel", NBTTagInt.a(level));
    }

    @Override
    public float getExp() {
        return tag.getFloat("XpP");
    }

    @Override
    public void setExp(float exp) {
        tag.set("XpP", NBTTagFloat.a(exp));
    }

    @Override
    public int getExpTotal() {
        return tag.getInt("XpTotal");
    }

    @Override
    public void setExpTotal(int exp) {
        tag.set("XpTotal", NBTTagInt.a(exp));
    }

    @Override
    public void save() {
        save(SavePlayerType.DEFAULT);
    }

    @Override
    public void save(SavePlayerType type) {
        try {
            File file = new File(this.playerDir, uuid + ".dat.tmp");
            File file1 = new File(this.playerDir, uuid + ".dat");
            NBTCompressedStreamTools.a(tag, new FileOutputStream(file));
            if (file1.exists())
                if (!file1.delete()) throw new IOException(String.format("Failed to delete tmp player file %s", uuid.toString()));
            if (!file.renameTo(file1)) throw new IOException(String.format("Failed to rename player file %s", uuid.toString()));
        } catch (Exception e) {
            plugin.getLogger().warning("Failed to save player data for " + name);
            e.printStackTrace();
        }
    }

    @Override
    public IUser getUser() {
        return new OfflineUser(plugin, uuid, tag);
    }

}

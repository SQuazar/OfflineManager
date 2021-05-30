package net.flawe.offlinemanager.api.util.v1_12_R1;

import com.mojang.authlib.GameProfile;
import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.entity.IUser;
import net.flawe.offlinemanager.api.enums.SavePlayerType;
import net.flawe.offlinemanager.api.inventory.AbstractPlayerInventory;
import net.flawe.offlinemanager.api.inventory.IArmorInventory;
import net.flawe.offlinemanager.api.inventory.IEnderChest;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftInventoryPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.UUID;

public class PlayerData implements IPlayerData {

    private final Plugin plugin;
    private final UUID uuid;
    private final String name;
    private final WorldNBTStorage worldNBTStorage;
    private final NBTTagCompound tag;
    private final File playerDir;
    private final PlayerInventory playerInventory;
    private final ArmorInventory armorInventory;
    private final IEnderChest enderChest;

    public PlayerData(String name, Plugin plugin) {
        this(Bukkit.getOfflinePlayer(name).getUniqueId(), plugin);
    }

    public PlayerData(UUID uuid, Plugin plugin) {
        this(uuid, (WorldNBTStorage) ((CraftServer) Bukkit.getServer()).getServer().getWorld().getDataManager(), plugin);

    }

    public PlayerData(UUID uuid, WorldNBTStorage worldNBTStorage, Plugin plugin) {
        this.plugin = plugin;
        this.uuid = uuid;
        this.worldNBTStorage = worldNBTStorage;
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer worldServer = server.getWorldServer(0);
        if (worldServer == null)
            throw new NullPointerException("Overworld cannot be null!");
        GameProfile profile = new GameProfile(uuid, Bukkit.getOfflinePlayer(uuid).getName());
        EntityPlayer entityPlayer = new EntityPlayer(server, worldServer, profile, new PlayerInteractManager(worldServer));
        this.tag = worldNBTStorage.getPlayerData(uuid.toString());
        entityPlayer.f(tag);
        entityPlayer.a(tag);
        this.name = entityPlayer.getName();
        this.playerDir = worldNBTStorage.getPlayerDir();
        NBTTagList inventoryList = (NBTTagList) tag.get("Inventory");
        net.minecraft.server.v1_12_R1.PlayerInventory virtual = new net.minecraft.server.v1_12_R1.PlayerInventory(entityPlayer);
        virtual.b(inventoryList);
        this.playerInventory = new PlayerInventory(new CraftInventoryPlayer(virtual), tag);
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

    }

    @Override
    public short getFireTicks() {
        return 0;
    }

    @Override
    public void setFireTicks(short ticks) {

    }

    @Override
    public float getExhaustion() {
        return 0;
    }

    @Override
    public void setExhaustion(float exhaustion) {

    }

    @Override
    public int getFoodLevel() {
        return 0;
    }

    @Override
    public void setFoodLevel(int level) {

    }

    @Override
    public float getSaturation() {
        return 0;
    }

    @Override
    public void setSaturation(float saturation) {

    }

    @Override
    public float getHealth() {
        return 0;
    }

    @Override
    public boolean isInvulnerable() {
        return false;
    }

    @Override
    public void setInvulnerable(boolean b) {

    }

    @Override
    public boolean isOnGround() {
        return false;
    }

    @Override
    public GameMode getGameMode() {
        return null;
    }

    @Override
    public void setGameMode(GameMode gameMode) {

    }

    @Override
    public int getPortalCooldown() {
        return 0;
    }

    @Override
    public void setPortalCooldown(int cooldown) {

    }

    @Override
    public int getSelectedSlot() {
        return 0;
    }

    @Override
    public World getWorld() {
        return null;
    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public void setLocation(Location location) {

    }

    @Override
    public void setLocation(Entity entity) {

    }

    @Override
    public void setSelectedSlot(int slot) {

    }

    @Override
    public void setHealth(float health) {

    }

    @Override
    public int getExpLevel() {
        return 0;
    }

    @Override
    public void setExpLevel(int level) {

    }

    @Override
    public float getExp() {
        return 0;
    }

    @Override
    public void setExp(float exp) {

    }

    @Override
    public int getExpTotal() {
        return 0;
    }

    @Override
    public void setExpTotal(int total) {

    }

    @Override
    public void save() {

    }

    @Override
    public void save(SavePlayerType type) {

    }

    @Override
    public IUser getUser() {
        return null;
    }
}

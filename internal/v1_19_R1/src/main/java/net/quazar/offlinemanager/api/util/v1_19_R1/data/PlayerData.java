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

package net.quazar.offlinemanager.api.util.v1_19_R1.data;

import com.mojang.authlib.GameProfile;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.storage.PlayerDataStorage;
import net.quazar.offlinemanager.api.OfflineManagerAPI;
import net.quazar.offlinemanager.api.data.entity.AbstractPlayerData;
import net.quazar.offlinemanager.api.data.entity.PlayerProfile;
import net.quazar.offlinemanager.api.enums.SavePlayerType;
import net.quazar.offlinemanager.api.event.data.LoadPlayerEvent;
import net.quazar.offlinemanager.api.event.data.SavePlayerEvent;
import net.quazar.offlinemanager.api.inventory.AbstractPlayerInventory;
import net.quazar.offlinemanager.api.inventory.IArmorInventory;
import net.quazar.offlinemanager.api.inventory.IEnderChest;
import net.quazar.offlinemanager.api.util.v1_19_R1.inventory.ArmorInventory;
import net.quazar.offlinemanager.api.util.v1_19_R1.inventory.OfflineEnderChest;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R1.CraftServer;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftInventoryPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Objects;
import java.util.UUID;

public class PlayerData extends AbstractPlayerData {
    private final OfflineManagerAPI api;
    private final UUID uuid;
    private final String name;
    private PlayerDataStorage worldNBTStorage;
    private CompoundTag tag;
    private final File playerDir;
    private net.quazar.offlinemanager.api.util.v1_19_R1.inventory.PlayerInventory playerInventory;
    private ArmorInventory armorInventory;
    private IEnderChest enderChest;

    public PlayerData(String name, OfflineManagerAPI api) {
        //noinspection deprecation
        this(Bukkit.getOfflinePlayer(name).getUniqueId(), api);
    }

    public PlayerData(UUID uuid, OfflineManagerAPI api) {
        this(PlayerProfile.of(uuid, Bukkit.getOfflinePlayer(uuid).getName()), new TagAdapter(((CraftServer) Bukkit.getServer()).getHandle().playerIo.getPlayerData(uuid.toString())), api);
    }

    public PlayerData(PlayerProfile profile, OfflineManagerAPI api) {
        this(profile, new TagAdapter(((CraftServer) Bukkit.getServer()).getHandle().playerIo.getPlayerData(profile.getUuid().toString())), api);
    }

    public PlayerData(PlayerProfile profile, TagAdapter compound, OfflineManagerAPI api) {
        super(profile, compound);
        this.api = api;
        this.uuid = profile.getUuid();
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        ServerLevel level = server.getLevel(ServerLevel.OVERWORLD);
        if (level == null)
            throw new NullPointerException("Overworld cannot be null!");
        GameProfile gameProfile = new GameProfile(uuid, profile.getName());
        ServerPlayer entityPlayer
                 = new ServerPlayer(server, level, gameProfile, null);
        this.tag = Objects.requireNonNull(compound.getTag(), "Player file cannot be loaded! File name is " + uuid);
        entityPlayer.load(tag); //load
        entityPlayer.readAdditionalSaveData(tag); //loadData
        this.name = entityPlayer.displayName;
        this.worldNBTStorage = server.playerDataStorage;
        this.playerDir = worldNBTStorage.getPlayerDir();
        ListTag inventoryList = (ListTag) tag.get("Inventory");
        Inventory virtual = new Inventory(entityPlayer);
        virtual.load(inventoryList);
        this.playerInventory = new net.quazar.offlinemanager.api.util.v1_19_R1.inventory.PlayerInventory(new CraftInventoryPlayer(virtual), tag);
        this.armorInventory = new ArmorInventory(this);
        this.enderChest = new OfflineEnderChest(Bukkit.createInventory(null, InventoryType.ENDER_CHEST), tag);
        LoadPlayerEvent event = new LoadPlayerEvent(this);
        Bukkit.getPluginManager().callEvent(event);
    }

    @Override
    public void setOnline(boolean b) {
        Player player = Bukkit.getPlayer(uuid);
        if (b) {
            if (player == null) return;
            ServerPlayer e = ((CraftPlayer) player).getHandle();
            e.load(tag);
            e.readAdditionalSaveData(tag);
            return;
        }
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        ServerLevel worldServer = server.getLevel(ServerLevel.OVERWORLD);
        if (worldServer == null)
            throw new NullPointerException("Overworld cannot be null!");
        GameProfile profile = new GameProfile(uuid, Bukkit.getOfflinePlayer(uuid).getName());
        ServerPlayer ep;
        worldNBTStorage = ((CraftServer) Bukkit.getServer()).getHandle().playerIo;
        adapter = new TagAdapter(worldNBTStorage.getPlayerData(uuid.toString()));
        tag = ((TagAdapter) adapter).getTag();
        if (player != null) {
            ep = ((CraftPlayer) player).getHandle();
            adapter = new TagAdapter(ep.saveWithoutId(tag)); //save
            ep.addAdditionalSaveData(tag); //saveData
        }
        ep = new ServerPlayer(server, worldServer, profile, null);
        ep.load(tag);
        ep.readAdditionalSaveData(tag);
        ListTag inventoryList = (ListTag) tag.get("Inventory");
        Inventory virtual = new Inventory(ep);
        virtual.load(inventoryList);
        this.playerInventory = new net.quazar.offlinemanager.api.util.v1_19_R1.inventory.PlayerInventory(new CraftInventoryPlayer(virtual), tag);
        this.armorInventory = new ArmorInventory(this);
        this.enderChest = new OfflineEnderChest(Bukkit.createInventory(null, InventoryType.ENDER_CHEST), tag);
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
    public void save() {
        save(SavePlayerType.DEFAULT);
    }

    @Override
    public void save(SavePlayerType type) {
        SavePlayerEvent event = new SavePlayerEvent(this, type);
        Bukkit.getPluginManager().callEvent(event);
        File file = new File(this.playerDir, uuid + ".dat.tmp");
        File file1 = new File(this.playerDir, uuid + ".dat");
        try (OutputStream out = Files.newOutputStream(file.toPath())){
            NbtIo.writeCompressed(tag, out);
            if (file1.exists())
                if (!file1.delete())
                    throw new IOException(String.format("Failed to delete tmp player file %s", uuid.toString()));
            if (!file.renameTo(file1))
                throw new IOException(String.format("Failed to rename player file %s", uuid.toString()));
        } catch (Exception e) {
            ((Plugin) api).getLogger().warning("Failed to save player data for " + name);
            e.printStackTrace();
        }
    }

}

package net.flawe.offlinemanager.api.util.v1_12_R1;

import net.flawe.offlinemanager.api.inventory.AbstractPlayerInventory;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagInt;
import net.minecraft.server.v1_12_R1.NBTTagList;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftInventoryPlayer;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class PlayerInventory extends AbstractPlayerInventory {

    private final NBTTagCompound tag;

    public PlayerInventory(org.bukkit.inventory.PlayerInventory inventory, NBTTagCompound tag) {
        super(inventory);
        this.tag = tag;
    }

    @Override
    public void setHeldItemSlot(int i) {
        tag.set("SelectedItemSlot", new NBTTagInt(i));
    }

    @Override
    protected void update() {
        NBTTagList inventory = new NBTTagList();
        ((CraftInventoryPlayer) this.inventory).getInventory().a(inventory);
        tag.set("Inventory", inventory);
    }

    @Override
    public String getName() {
        return inventory.getName();
    }

    @Override
    public boolean contains(int i) {
        return inventory.contains(i);
    }

    @Override
    public boolean contains(int i, int i1) {
        return inventory.contains(i, i1);
    }

    @Override
    public HashMap<Integer, ? extends ItemStack> all(int i) {
        return inventory.all(i);
    }

    @Override
    public int first(int i) {
        return inventory.first(i);
    }

    @Override
    public void remove(int i) {
        inventory.remove(i);
        update();
    }

    @Override
    public String getTitle() {
        return inventory.getTitle();
    }
}

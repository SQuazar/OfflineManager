package net.flawe.offlinemanager.api.util.v1_17_R1.inventory;

import net.flawe.offlinemanager.api.inventory.AbstractPlayerInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftInventoryPlayer;

public class PlayerInventory extends AbstractPlayerInventory {

    private final NBTTagCompound tag;

    public PlayerInventory(org.bukkit.inventory.PlayerInventory inventory, NBTTagCompound tag) {
        super(inventory);
        this.tag = tag;
    }

    @Override
    protected void update() {
        NBTTagList inventory = new NBTTagList();
        ((CraftInventoryPlayer) this.inventory).getInventory().a(inventory);
        tag.set("Inventory", inventory);
    }

    @Override
    public void setHeldItemSlot(int i) {
        tag.set("SelectedItemSlot", NBTTagInt.a(i));
    }
}

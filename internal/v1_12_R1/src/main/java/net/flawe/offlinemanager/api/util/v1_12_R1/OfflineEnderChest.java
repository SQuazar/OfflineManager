package net.flawe.offlinemanager.api.util.v1_12_R1;

import net.flawe.offlinemanager.api.inventory.AbstractInventory;
import net.flawe.offlinemanager.api.inventory.IEnderChest;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_12_R1.inventory.InventoryWrapper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class OfflineEnderChest extends AbstractInventory implements IEnderChest {

    private final Inventory inventory;
    private final NBTTagCompound tag;

    public OfflineEnderChest(Inventory inventory, NBTTagCompound tag) {
        super(inventory);
        this.inventory = inventory;
        NBTTagList list = (NBTTagList) tag.get("EnderItems");
        if (list == null)
            throw new NullPointerException("EnderItems cannot be null!");
        for (int i = 0; i < list.size(); i++) {
            NBTTagCompound item = list.get(i);
            int slot = item.getByte("Slot") & 0xFF;
            if (slot < inventory.getSize())
                inventory.setItem(slot, CraftItemStack.asBukkitCopy(new ItemStack(item)));
        }
        this.tag = tag;
    }

    @Override
    protected void update() {
        NBTTagList inv = new NBTTagList();
        IInventory inventory = ((CraftInventory) this.inventory).getInventory();
        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (stack != null && !stack.isEmpty()) {
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setByte("Slot", (byte) i);
                stack.save(tagCompound);
                inv.add(tagCompound);
            }
        }
        tag.set("EnderItems", inv);
    }

    @Override
    public Inventory getEnderChest() {
        return this;
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
    public HashMap<Integer, ? extends org.bukkit.inventory.ItemStack> all(int i) {
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

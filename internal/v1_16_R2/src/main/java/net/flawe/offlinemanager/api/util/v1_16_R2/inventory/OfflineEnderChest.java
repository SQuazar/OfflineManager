package net.flawe.offlinemanager.api.util.v1_16_R2.inventory;

import net.flawe.offlinemanager.api.inventory.AbstractInventory;
import net.flawe.offlinemanager.api.inventory.IEnderChest;
import net.minecraft.server.v1_16_R2.*;
import org.bukkit.craftbukkit.v1_16_R2.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_16_R2.inventory.CraftItemStack;
import org.bukkit.inventory.Inventory;

public class OfflineEnderChest extends AbstractInventory implements IEnderChest {

    private final Inventory inventory;
    private final NBTTagCompound tag;

    public OfflineEnderChest(Inventory inventory, NBTTagCompound tag) {
        super(inventory);
        this.inventory = inventory;
        NBTTagList list = (NBTTagList) tag.get("EnderItems");
        if (list == null)
            throw new NullPointerException("EnderItems cannot be null!");
        for (NBTBase base : list) {
            if (!(base instanceof NBTTagCompound)) continue;
            NBTTagCompound item = (NBTTagCompound) base;
            int slot = item.getByte("Slot") & 0xFF;
            if (slot < inventory.getSize())
                inventory.setItem(slot, CraftItemStack.asBukkitCopy(ItemStack.a(item)));
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
}

/*
 * Copyright (c) 2021 flaweoff
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

package net.flawe.offlinemanager.api.util.v1_17_R1.inventory;

import net.flawe.offlinemanager.api.inventory.AbstractInventory;
import net.flawe.offlinemanager.api.inventory.IEnderChest;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.IInventory;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.inventory.Inventory;

public class OfflineEnderChest extends AbstractInventory implements IEnderChest {

    private final NBTTagCompound tag;

    public OfflineEnderChest(Inventory inventory, NBTTagCompound tag) {
        super(inventory);
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

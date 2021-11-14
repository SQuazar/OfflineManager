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

package net.flawe.offlinemanager.api.util.v1_16_R2.inventory;

import net.flawe.offlinemanager.api.inventory.AbstractPlayerInventory;
import net.minecraft.server.v1_16_R2.NBTTagCompound;
import net.minecraft.server.v1_16_R2.NBTTagInt;
import net.minecraft.server.v1_16_R2.NBTTagList;
import org.bukkit.craftbukkit.v1_16_R2.inventory.CraftInventoryPlayer;

public class PlayerInventory extends AbstractPlayerInventory {

    private final org.bukkit.inventory.PlayerInventory inventory;
    private final NBTTagCompound tag;

    public PlayerInventory(org.bukkit.inventory.PlayerInventory inventory, NBTTagCompound tag) {
        super(inventory);
        this.tag = tag;
        this.inventory = inventory;
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

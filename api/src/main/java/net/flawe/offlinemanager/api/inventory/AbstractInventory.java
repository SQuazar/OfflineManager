package net.flawe.offlinemanager.api.inventory;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

/**
 * Abstract player inventory. Equal to bukkit inventory but working with player data
 * @author flawe
 */
public abstract class AbstractInventory implements Inventory {

    protected final Inventory inventory;

    public AbstractInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public int getSize() {
        return inventory.getSize();
    }

    @Override
    public int getMaxStackSize() {
        return inventory.getMaxStackSize();
    }

    @Override
    public void setMaxStackSize(int i) {
        inventory.setMaxStackSize(i);
    }

    @Nullable
    @Override
    public ItemStack getItem(int i) {
        return inventory.getItem(i);
    }

    @Override
    public void setItem(int i, @Nullable ItemStack itemStack) {
        inventory.setItem(i, itemStack);
        update();
    }

    @NotNull
    @Override
    public HashMap<Integer, ItemStack> addItem(@NotNull ItemStack... itemStacks) throws IllegalArgumentException {
        HashMap<Integer, ItemStack> map = inventory.addItem(itemStacks);
        update();
        return map;
    }

    @NotNull
    @Override
    public HashMap<Integer, ItemStack> removeItem(@NotNull ItemStack... itemStacks) throws IllegalArgumentException {
        HashMap<Integer, ItemStack> map = inventory.removeItem(itemStacks);
        update();
        return map;
    }

    @NotNull
    @Override
    public ItemStack[] getContents() {
        return inventory.getContents();
    }

    @Override
    public void setContents(@NotNull ItemStack[] itemStacks) throws IllegalArgumentException {
        inventory.setContents(itemStacks);
        update();
    }

    @NotNull
    @Override
    public ItemStack[] getStorageContents() {
        return inventory.getStorageContents();
    }

    @Override
    public void setStorageContents(@NotNull ItemStack[] itemStacks) throws IllegalArgumentException {
        inventory.setStorageContents(itemStacks);
        update();
    }

    @Override
    public boolean contains(@NotNull Material material) throws IllegalArgumentException {
        return inventory.contains(material);
    }

    @Override
    public boolean contains(@Nullable ItemStack itemStack) {
        return inventory.contains(itemStack);
    }

    @Override
    public boolean contains(@NotNull Material material, int i) throws IllegalArgumentException {
        return inventory.contains(material, i);
    }

    @Override
    public boolean contains(@Nullable ItemStack itemStack, int i) {
        return inventory.contains(itemStack, i);
    }

    @Override
    public boolean containsAtLeast(@Nullable ItemStack itemStack, int i) {
        return inventory.containsAtLeast(itemStack, i);
    }

    @NotNull
    @Override
    public HashMap<Integer, ? extends ItemStack> all(@NotNull Material material) throws IllegalArgumentException {
        return inventory.all(material);
    }

    @NotNull
    @Override
    public HashMap<Integer, ? extends ItemStack> all(@Nullable ItemStack itemStack) {
        return inventory.all(itemStack);
    }

    @Override
    public int first(@NotNull Material material) throws IllegalArgumentException {
        return inventory.first(material);
    }

    @Override
    public int first(@NotNull ItemStack itemStack) {
        return inventory.first(itemStack);
    }

    @Override
    public int firstEmpty() {
        return inventory.firstEmpty();
    }

    @Override
    public boolean isEmpty() {
        return inventory.isEmpty();
    }

    @Override
    public void remove(@NotNull Material material) throws IllegalArgumentException {
        inventory.remove(material);
        update();
    }

    @Override
    public void remove(@NotNull ItemStack itemStack) {
        inventory.remove(itemStack);
        update();
    }

    @Override
    public void clear(int i) {
        inventory.clear(i);
        update();
    }

    @Override
    public void clear() {
        inventory.clear();
        update();
    }

    @NotNull
    @Override
    public List<HumanEntity> getViewers() {
        return inventory.getViewers();
    }

    @NotNull
    @Override
    public InventoryType getType() {
        return inventory.getType();
    }

    @Nullable
    @Override
    public InventoryHolder getHolder() {
        return inventory.getHolder();
    }

    @NotNull
    @Override
    public ListIterator<ItemStack> iterator() {
        return inventory.iterator();
    }

    @NotNull
    @Override
    public ListIterator<ItemStack> iterator(int i) {
        return  inventory.iterator(i);
    }

    @Nullable
    @Override
    public Location getLocation() {
        return inventory.getLocation();
    }

    /**
     * Update inventory in player tag compound
     */
    protected abstract void update();

}

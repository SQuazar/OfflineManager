package net.flawe.offlinemanager.api.inventory;

import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The player inventory. Equal to bukkit player inventory, but working with player data
 */
public abstract class AbstractPlayerInventory extends AbstractInventory {

    protected final PlayerInventory inventory;

    public AbstractPlayerInventory(PlayerInventory inventory) {
        super(inventory);
        this.inventory = inventory;
    }

    @NotNull
    public ItemStack[] getArmorContents() {
        return inventory.getArmorContents();
    }

    @NotNull
    public ItemStack[] getExtraContents() {
        return inventory.getExtraContents();
    }

    @Nullable
    public ItemStack getHelmet() {
        return inventory.getHelmet();
    }

    @Nullable
    public ItemStack getChestplate() {
        return inventory.getChestplate();
    }

    @Nullable
    public ItemStack getLeggings() {
        return inventory.getLeggings();
    }

    @Nullable
    public ItemStack getBoots() {
        return inventory.getBoots();
    }

    public void setItem(@NotNull EquipmentSlot equipmentSlot, @Nullable ItemStack itemStack) {
        inventory.setItem(equipmentSlot, itemStack);
        update();
    }

    @NotNull
    public ItemStack getItem(@NotNull EquipmentSlot equipmentSlot) {
        return inventory.getItem(equipmentSlot);
    }

    public void setArmorContents(@Nullable ItemStack[] itemStacks) {
        inventory.setArmorContents(itemStacks);
        update();
    }

    public void setExtraContents(@Nullable ItemStack[] itemStacks) {
        inventory.setExtraContents(itemStacks);
        update();
    }

    public void setHelmet(@Nullable ItemStack itemStack) {
        inventory.setHelmet(itemStack);
        update();
    }

    public void setChestplate(@Nullable ItemStack itemStack) {
        inventory.setChestplate(itemStack);
        update();
    }

    public void setLeggings(@Nullable ItemStack itemStack) {
        inventory.setLeggings(itemStack);
        update();
    }

    public void setBoots(@Nullable ItemStack itemStack) {
        inventory.setBoots(itemStack);
        update();
    }

    @NotNull
    public ItemStack getItemInMainHand() {
        return inventory.getItemInMainHand();
    }

    public void setItemInMainHand(@Nullable ItemStack itemStack) {
        inventory.setItemInMainHand(itemStack);
        update();
    }

    @NotNull
    public ItemStack getItemInOffHand() {
        return inventory.getItemInOffHand();
    }

    public void setItemInOffHand(@Nullable ItemStack itemStack) {
        inventory.setItemInOffHand(itemStack);
        update();
    }

    @NotNull
    public ItemStack getItemInHand() {
        return inventory.getItemInMainHand();
    }

    public void setItemInHand(@Nullable ItemStack itemStack) {
        inventory.setItemInHand(itemStack);
        update();
    }

    public int getHeldItemSlot() {
        return inventory.getHeldItemSlot();
    }

    public abstract void setHeldItemSlot(int i);

    /**
     * Update inventory in player tag compound
     */
    protected abstract void update();

}

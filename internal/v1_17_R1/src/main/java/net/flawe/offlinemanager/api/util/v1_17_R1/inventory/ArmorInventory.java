package net.flawe.offlinemanager.api.util.v1_17_R1.inventory;

import net.flawe.offlinemanager.api.data.entity.IPlayerData;
import net.flawe.offlinemanager.api.entity.IUser;
import net.flawe.offlinemanager.api.inventory.IArmorInventory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ArmorInventory implements IArmorInventory {

    private final IPlayerData playerData;

    public ArmorInventory(IUser user) {
        this.playerData = user.getPlayerData();
    }

    public ArmorInventory(IPlayerData playerData) {
        this.playerData = playerData;
    }

    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(null, 9);
        for (int i = 0; i < 9; i++) {
            if (i > 4) {
                inventory.setItem(i, blockedItem());
                continue;
            }
            if (i == 4) {
                inventory.setItem(i, playerData.getInventory().getItemInOffHand());
                continue;
            }
            inventory.setItem(i, playerData.getInventory().getArmorContents()[i]);

        }
        return inventory;
    }

    private ItemStack blockedItem() {
        ItemStack stack = new ItemStack(Material.BARRIER);
        ItemMeta meta = stack.getItemMeta();
        assert meta != null;
        meta.setDisplayName(" ");
        stack.setItemMeta(meta);
        return stack;
    }
}

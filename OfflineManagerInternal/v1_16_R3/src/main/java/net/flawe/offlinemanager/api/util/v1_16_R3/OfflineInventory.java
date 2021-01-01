package net.flawe.offlinemanager.api.util.v1_16_R3;

import net.flawe.offlinemanager.api.IInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class OfflineInventory implements IInventory {

    private final Player player;

    public OfflineInventory(Player player) {
        this.player = player;
    }

    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(null, 36);
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, player.getInventory().getItem(i));
        }
        return inventory;
    }
}

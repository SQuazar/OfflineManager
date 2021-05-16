package net.flawe.offlinemanager.api.util.v1_16_R3;

import net.flawe.offlinemanager.api.inventory.IEnderChest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class OfflineEnderChest implements IEnderChest {

    private final Player player;

    public OfflineEnderChest(Player player) {
        this.player = player;
    }

    @Override
    public Inventory getEnderChest() {
        return player.getEnderChest();
    }

}

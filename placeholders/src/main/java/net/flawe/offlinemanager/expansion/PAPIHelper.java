package net.flawe.offlinemanager.expansion;

import me.clip.placeholderapi.PlaceholderAPI;
import net.flawe.offlinemanager.api.OfflineManagerAPI;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PAPIHelper {

    private final OfflineManagerAPI api;

    public PAPIHelper(OfflineManagerAPI api) {
        this.api = api;
    }

    public String fillMessage(@NotNull Player player, @NotNull String s) {
        if (!api.papi())
            return s;
        return PlaceholderAPI.setPlaceholders(player, s);
    }
}

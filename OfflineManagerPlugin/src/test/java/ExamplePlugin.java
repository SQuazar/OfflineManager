import net.flawe.offlinemanager.OfflineManager;
import net.flawe.offlinemanager.OfflineManagerAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ExamplePlugin extends JavaPlugin implements CommandExecutor {

    private OfflineManagerAPI offlineManagerAPI;

    @Override
    public void onEnable() {
        if (Bukkit.getPluginManager().getPlugin("OfflineManager") == null) {
            getLogger().severe("Could not find OfflineManager! This plugin is required!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        offlineManagerAPI = OfflineManager.getApi();
        Bukkit.getPluginCommand("example").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player))
            return false;
        Player player = (Player) sender;
        String playerName = args[0];
        if (offlineManagerAPI.getStorage().hasPlayer(playerName))
            player.teleport(offlineManagerAPI.getUser(playerName).getLocation());
        return true;
    }
}

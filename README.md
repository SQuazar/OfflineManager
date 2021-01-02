# OfflineManager

<div>
<a href="https://github.com/flaweoff/OfflineManager/releases/latest"><img src="https://img.shields.io/github/v/release/flaweoff/OfflineManager?include_prereleases" alt="Last release"></a>
<a href="https://github.com/flaweoff/OfflineManager/releases/latest"><img src="https://img.shields.io/github/downloads/flaweoff/OfflineManager/total" alt="Downloads"></a>
<a href="https://github.com/flaweoff/OfflineManager/releases/latest"><img src="https://img.shields.io/github/release-date-pre/flaweoff/OfflineManager" alt="Release date"></a>
<a href="https://discord.gg/VGE2zCcNQD"><img src="https://img.shields.io/discord/794710411060183130" alt="Discord chat"></a>
</div>

# Description

<p>As you know, offline players cannot be edited in vanilla minecraft without using utility programs, 
but using the OfflineManager plugin, you can use the list of functions to edit the player you need! 
The plugin also provides API for developers, with which you can add your own functions.</p>

# Features

<div>
<ul>
<li>Has API</li>
<li>PlaceholderAPI support</li>
<li>Permissions support</li>
<li>Offline player management</li>
<li>Search for offline players using TAB</li>
<li>Has configuration plugin</li>
</ul>
</div>

# Installation

<div>
<p>To install the plugin, transfer the plugin to the plugins folder and restart or reload the server. 
If you update plugin to 3.0 version, recommended remove config.yml and messages.yml files.</p>
</div>

# Commands & Permissions

<div>
<table>
<tr>
<th>Command</th>
<th>Aliases</th>
<th>Description</th>
<th>Permission</th>
</tr>
<tr>
<th>/offlinemanager</th>
<th>om</th>
<th>OfflineManager main command</th>
<th>offlinemanager.use</th>
</tr>
<tr>
<th>/om help</th>
<th>none</th>
<th>Get plugin help page</th>
<th>offlinemanager.use</th>
</tr>
<tr>
<th>/om invsee [player]</th>
<th>none</th>
<th>Open player's offline inventory</th>
<th>offlinemanager.invsee</th>
</tr>
<tr>
<th>/om invsee [player] armor</th>
<th>none</th>
<th>Open player's offline armor inventory</th>
<th>offlinemanager.invsee.armor</th>
</tr>
<tr>
<th>/om enderchest [player]</th>
<th>ec</th>
<th>Open player's offline ender chest</th>
<th>offlinemanager.enderchest</th>
</tr>
<tr>
<th>/om reload [player]</th>
<th>none</th>
<th>Reload the plugin configuration</th>
<th>offlinemanager.reload</th>
</tr>
<tr>
<th>/om teleport [player]</th>
<th>tp</th>
<th>Teleport to offline player</th>
<th>offlinemanager.teleport</th>
</tr>
<tr>
<th>/om tphere [player]</th>
<th>none</th>
<th>Teleport an offline player to you</th>
<th>offlinemanager.tphere</th>
</tr>
<tr>
<th>/om adventure [player]</th>
<th>none</th>
<th>Set the adventure mode to the offline player</th>
<th>offlinemanager.adventure</th>
</tr>
<tr>
<th>/om creative [player]</th>
<th>none</th>
<th>Set the creative mode to the offline player</th>
<th>offlinemanager.creative</th>
</tr>
<tr>
<th>/om survival [player]</th>
<th>none</th>
<th>Set the survival mode to the offline player</th>
<th>offlinemanager.survival</th>
</tr>
<tr>
<th>/om spectator [player]</th>
<th>none</th>
<th>Set the spectator mode to the offline player</th>
<th>offlinemanager.spectator</th>
</tr>
<tr>
<th>/om clear [player]</th>
<th>none</th>
<th>Clear the offline player inventory</th>
<th>offlinemanager.clear</th>
</tr>
<tr>
<th>/om kill [player]</th>
<th>none</th>
<th>Kill the offline player</th>
<th>offlinemanager.kill</th>
</tr>
<tr>
<th>/om heal [player]</th>
<th>none</th>
<th>Heal the offline player</th>
<th>offlinemanager.heal</th>
</tr>
<tr>
<th>/om feed [player]</th>
<th>none</th>
<th>Feed the offline player</th>
<th>offlinemanager.feed</th>
</tr>
</table>
</div>

# Placeholders

<div>
<h3>For all placeholders use om_ prefix!</h3>
<table>
<tr>
<th>Placeholder</th>
<th>Description</th>
</tr>
<tr>
<th>player_name</th>
<th>Returns the player name</th>
</tr>
<tr>
<th>player_uuid</th>
<th>Returns the player's uuid</th>
</tr>
<tr>
<th>player_healths</th>
<th>Returns the player's healths count</th>
</tr>
<tr>
<th>player_food</th>
<th>Returns the player's food count</th>
</tr>
<tr>
<th>player_locX</th>
<th>Returns the player's x coordinate</th>
</tr>
<tr>
<th>player_locY</th>
<th>Returns the player's y coordinate</th>
</tr>
<tr>
<th>player_locZ</th>
<th>Returns the player's z coordinate</th>
</tr>
<tr>
<th>player_locYaw</th>
<th>Returns the player's yaw</th>
</tr>
<tr>
<th>player_locPitch</th>
<th>Returns the player's pitch</th>
</tr>
<tr>
<th>player_locWorld</th>
<th>Returns the player's world name</th>
</tr>
</table>
</div>

# Placeholders usage

<div>
In order to get information from the player you need, you must:
<ul>
<li>Install PlaceholderAPI</li>
<li>Install ParseOther using <code>/papi ecloud download ParseOther</code></li>
<li>Use <code>parseother_{playername}_{om_placeholder}</code> in placeholders</li>
</ul>
<h3>Examples</h3>
<table>
<tr>
<th>Placeholder</th>
<th>Result</th>
</tr>
<tr>
<th>parseother_{playername}_{om_player_uuid}</th>
<th>Return the player's uuid</th>
</tr>
<tr>
<th>parseother_{playername}_{om_player_locX}</th>
<th>Return the player's x coordinate</th>
</tr>
</table>
</div>

# API Usage

### Installation & Usage
```java
package net.example;

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

```

# Support

<div>
<ul>
<li><a href="https://discord.gg/VGE2zCcNQD">Discord Support</a></li>
<li><a href="https://t.me/flawe_sv">Telegram Support</a></li>
</ul>
</div>

# Links

<div>
<ul>
<li><a href="https://dev.bukkit.org/projects/offlinemanager">Bukkit Plugin page</a></li>
</ul>
</div>

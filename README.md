# OfflineManager

<div>
<a href="https://github.com/flaweoff/OfflineManager/releases/latest"><img src="https://img.shields.io/github/v/release/flaweoff/OfflineManager?include_prereleases" alt="Last release"></a>
<a href="https://github.com/flaweoff/OfflineManager/releases/latest"><img src="https://img.shields.io/github/downloads-pre/flaweoff/OfflineManager/latest/total" alt="Downloads"></a>
<a href="https://github.com/flaweoff/OfflineManager/releases/latest"><img src="https://img.shields.io/github/release-date-pre/flaweoff/OfflineManager" alt="Release date"></a>
<a href="https://discord.gg/VGE2zCcNQD"><img src="https://img.shields.io/discord/794710411060183130" alt="Discord chat"></a>
JavaDoc - https://ci.codemc.io/job/flaweoff/job/OfflineManager/Javadoc/
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
<td>/offlinemanager</td>
<td>om</td>
<td>OfflineManager main command</td>
<td>offlinemanager.use</td>
</tr>
<tr>
<td>/om help</td>
<td>none</td>
<td>Get plugin help page</td>
<td>offlinemanager.use</td>
</tr>
<tr>
<td>/om invsee [player]</td>
<td>none</td>
<td>Open player's offline inventory</td>
<td>offlinemanager.invsee</td>
</tr>
<tr>
<td>/om invsee [player] armor</td>
<td>none</td>
<td>Open player's offline armor inventory</td>
<td>offlinemanager.invsee.armor</td>
</tr>
<tr>
<td>/om enderchest [player]</td>
<td>ec</td>
<td>Open player's offline ender chest</td>
<td>offlinemanager.enderchest</td>
</tr>
<tr>
<td>/om reload [player]</td>
<td>none</td>
<td>Reload the plugin configuration</td>
<td>offlinemanager.reload</td>
</tr>
<tr>
<td>/om teleport [player]</td>
<td>tp</td>
<td>Teleport to offline player</td>
<td>offlinemanager.teleport</td>
</tr>
<tr>
<td>/om tphere [player]</td>
<td>none</td>
<td>Teleport an offline player to you</td>
<td>offlinemanager.tphere</td>
</tr>
<tr>
<td>/om adventure [player]</td>
<td>none</td>
<td>Set the adventure mode to the offline player</td>
<td>offlinemanager.adventure</td>
</tr>
<tr>
<td>/om creative [player]</td>
<td>none</td>
<td>Set the creative mode to the offline player</td>
<td>offlinemanager.creative</td>
</tr>
<tr>
<td>/om survival [player]</td>
<td>none</td>
<td>Set the survival mode to the offline player</td>
<td>offlinemanager.survival</td>
</tr>
<tr>
<td>/om spectator [player]</td>
<td>none</td>
<td>Set the spectator mode to the offline player</td>
<td>offlinemanager.spectator</td>
</tr>
<tr>
<td>/om clear [player]</td>
<td>none</td>
<td>Clear the offline player inventory</td>
<td>offlinemanager.clear</td>
</tr>
<tr>
<td>/om kill [player]</td>
<td>none</td>
<td>Kill the offline player</td>
<td>offlinemanager.kill</td>
</tr>
<tr>
<td>/om heal [player]</td>
<td>none</td>
<td>Heal the offline player</td>
<td>offlinemanager.heal</td>
</tr>
<tr>
<td>/om feed [player]</td>
<td>none</td>
<td>Feed the offline player</td>
<td>offlinemanager.feed</td>
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
<td>player_name</td>
<td>Returns the player name</td>
</tr>
<tr>
<td>player_uuid</td>
<td>Returns the player's uuid</td>
</tr>
<tr>
<td>player_healths</td>
<td>Returns the player's healths count</td>
</tr>
<tr>
<td>player_food</td>
<td>Returns the player's food count</td>
</tr>
<tr>
<td>player_locX</td>
<td>Returns the player's x coordinate</td>
</tr>
<tr>
<td>player_locY</td>
<td>Returns the player's y coordinate</td>
</tr>
<tr>
<td>player_locZ</td>
<td>Returns the player's z coordinate</td>
</tr>
<tr>
<td>player_locYaw</td>
<td>Returns the player's yaw</td>
</tr>
<tr>
<td>player_locPitch</td>
<td>Returns the player's pitch</td>
</tr>
<tr>
<td>player_locWorld</td>
<td>Returns the player's world name</td>
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
<td>parseother_{playername}_{om_player_uuid}</td>
<td>Return the player's uuid</td>
</tr>
<tr>
<td>parseother_{playername}_{om_player_locX}</td>
<td>Return the player's x coordinate</td>
</tr>
</table>
</div>

# Message configuration placeholders

<div>
<table>
<tr>
<th>Message</th>
<th>Placeholders</th>
</tr>
<tr>
<td>permission-deny</td>
<td>%player%, %permission%, %function%</td>
</tr>
<tr>
<td>enter-nickname</td>
<td>%player%, %permission%, %function%</td>
</tr>
<tr>
<td>enter-subcommand</td>
<td>%player%, %permission%, %function%</td>
</tr>
<tr>
<td>player-not-found</td>
<td>%player%, %target%, %permission%, %function%</td>
</tr>
<tr>
<td>player-is-online</td>
<td>%player%, %target%, %permission%, %function%</td>
</tr>
<tr>
<td>command-not-found</td>
<td>%player%, %command%</td>
</tr>
<tr>
<td>function-disabled</td>
<td>%player%, %function%, %permission%</td>
</tr>
<tr>
<td>already-being-edited</td>
<td>%player%, %target%, %permission%, %function%</td>
</tr>
<tr>
<td>teleport-success</td>
<td>%player%, %target%, %permission%, %function%</td>
</tr>
<tr>
<td>teleport-here</td>
<td>%player%, %target%, %permission%, %function%</td>
</tr>
<tr>
<td>teleport-another</td>
<td>%player%, %target%, %to%, %permission%, %function%</td>
</tr>
<tr>
<td>gamemode-changed</td>
<td>%player%, %target%, %gamemode%, %permission%, %function%</td>
</tr>
<tr>
<td>heal-player</td>
<td>%player%, %target%, %permission%, %function%</td>
</tr>
<tr>
<td>kill-player</td>
<td>%player%, %target%, %permission%, %function%</td>
</tr>
<tr>
<td>feed-player</td>
<td>%player%, %target%, %permission%, %function%</td>
</tr>
<tr>
<td>clear-inventory</td>
<td>%player%, %target%, %permission%, %function%</td>
</tr>
</table>
<table>
<tr>
<th colspan="2">Placeholders description</th>
</tr>
<tr>
<th>Placeholder</th>
<th>Returns</th>
</tr>
<tr>
<td>%player%</td>
<td>Command sender name</td>
</tr>
<tr>
<td>%target%</td>
<td>Target offline player name</td>
</tr>
<tr>
<td>%permission%</td>
<td>Permission name</td>
</tr>
<tr>
<td>%command%</td>
<td>Command name</td>
</tr>
<tr>
<td>%function%</td>
<td>Function name</td>
</tr>
<tr>
<td>%to%</td>
<td>The name of the player to which the offline player is teleported</td>
</tr>
<tr>
<td>%gamemode%</td>
<td>GameMode name</td>
</tr>
</table>
<h3>Also messages support the placeholders from PlaceholderAPI</h3>
</div>

# API Usage

### Installation & Usage
```java
package net.example;

import net.flawe.offlinemanager.OfflineManager;
import net.flawe.offlinemanager.api.OfflineManagerAPI;
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

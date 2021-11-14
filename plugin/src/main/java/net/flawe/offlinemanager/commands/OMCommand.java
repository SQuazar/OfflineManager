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

package net.flawe.offlinemanager.commands;

import net.flawe.offlinemanager.OfflineManager;
import net.flawe.offlinemanager.api.IPlaceholder;
import net.flawe.offlinemanager.api.OfflineManagerAPI;
import net.flawe.offlinemanager.api.command.ICommand;
import net.flawe.offlinemanager.configuration.Messages;
import net.flawe.offlinemanager.configuration.Settings;
import net.flawe.offlinemanager.placeholders.Placeholder;
import net.flawe.offlinemanager.placeholders.PlaceholderUtil;
import org.bukkit.entity.Player;

import java.util.*;

public abstract class OMCommand implements ICommand {

    private final String name;
    private final String help;
    private final String permission;
    private final String[] aliases;
    private final Set<IPlaceholder> placeholders = new HashSet<>();

    protected final OfflineManagerAPI api = OfflineManager.getApi();
    protected final Messages messages = ((OfflineManager) api).getMessages();
    protected final Settings settings = ((OfflineManager) api).getSettings();

    public OMCommand(String name, String help, String permission) {
        this(name, help, permission, new String[0]);
    }

    public OMCommand(String name, String help, String permission, String[] aliases) {
        this.name = name;
        this.help = help;
        this.permission = permission;
        this.aliases = aliases;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getHelp() {
        return help;
    }

    @Override
    public String getPermission() {
        return permission;
    }

    @Override
    public String[] getAliases() {
        String[] a = new String[aliases.length + 1];
        System.arraycopy(aliases, 0, a, 0, aliases.length);
        a[aliases.length] = name;
        return a;
    }

    @Override
    public abstract void execute(Player player, String[] args);

    @Override
    public boolean hasPermission(Player player) {
        return player.hasPermission(permission);
    }

    @Override
    public void addPlaceholder(String key, String value) {
        addPlaceholders(new Placeholder(key, value));
    }

    @Override
    public void addPlaceholder(IPlaceholder placeholder) {
        placeholders.remove(placeholder);
        placeholders.add(placeholder);
    }

    @Override
    public void addPlaceholders(IPlaceholder... placeholders) {
        List<IPlaceholder> list = Arrays.asList(placeholders);
        list.forEach(this.placeholders::remove);
        this.placeholders.addAll(list);
    }

    @Override
    public void removePlaceholder(String key) {
        placeholders.removeIf(placeholder -> placeholder.getKey().equals(key));
    }

    @Override
    public void removePlaceholder(IPlaceholder placeholder) {
        placeholders.remove(placeholder);
    }

    @Override
    public Map<String, String> getPlaceholdersAsMap() {
        Map<String, String> map = new HashMap<>();
        for (IPlaceholder placeholder : placeholders)
            map.put(placeholder.getKey(), placeholder.getValue());
        return map;
    }

    @Override
    public Set<IPlaceholder> getPlaceholders() {
        return placeholders;
    }

    protected void sendPlayerMessage(Player player, String message) {
        message = api.getConfigManager().fillMessage(player, message);
        player.sendMessage(PlaceholderUtil.fillPlaceholders(message, placeholders));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(name, ((ICommand) o).getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

}

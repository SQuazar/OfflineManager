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

package net.flawe.offlinemanager.commands.sub;

import net.flawe.offlinemanager.api.command.ICommand;
import net.flawe.offlinemanager.commands.OMCommand;
import net.flawe.offlinemanager.placeholders.Placeholder;
import org.bukkit.entity.Player;

public class HelpCommand extends OMCommand {

    public HelpCommand(String name, String help, String permission) {
        super(name, help, permission);
        addPlaceholder(new Placeholder("%permission%", permission));
    }

    @Override
    public void execute(Player player, String[] args) {
        addPlaceholder(new Placeholder("%player%", player.getName()));
        if (!hasPermission(player)) {
            sendPlayerMessage(player, messages.getPermissionDeny());
            return;
        }
        StringBuilder builder = new StringBuilder("&a[OfflineManager] &eOfflineManager help menu:");
        for (ICommand command : api.getCommandManager().getSubCommands()) {
            if (command.hasPermission(player))
                builder.append("\n &e- &a/om ")
                        .append(command.getName())
                        .append(" &e- &e")
                        .append(command.getHelp());
        }
        sendPlayerMessage(player, builder.toString());
    }
}

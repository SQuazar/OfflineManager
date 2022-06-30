/*
 * Copyright (c) 2021 squazar
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

package net.quazar.offlinemanager.commands;

import net.quazar.offlinemanager.api.command.ICommand;
import net.quazar.offlinemanager.api.command.ICommandManager;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements ICommandManager {

    private static final List<ICommand> subs = new ArrayList<>();

    @Override
    public List<ICommand> getSubCommands() {
        return subs;
    }

    @Override
    public void addSubCommand(ICommand command) {
        subs.add(command);
    }

    @Override
    public void removeSubCommand(ICommand command) {
        subs.remove(command);
    }

    @Override
    public void removeSubCommand(String name) {
        subs.removeIf(cmd -> cmd.getName().equals(name));
    }
}

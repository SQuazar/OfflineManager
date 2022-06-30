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

package net.quazar.offlinemanager.addon.menus.menu;

import net.quazar.offlinemanager.addon.menus.MenuAction;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MenuItem {

    private final ItemStack stack;
    private final int slot;
    private final String command;
    private final String permission;
    private final String name;
    private final List<String> lore = new ArrayList<>();
    private final MenuAction menuAction;
    private Menu menuToOpen;

    public MenuItem(ItemStack stack, int slot, String command, String permission, String name, List<String> lore, MenuAction menuAction) {
        this.stack = stack;
        this.slot = slot;
        this.command = command;
        this.permission = permission;
        this.name = name;
        this.menuAction = menuAction;
        this.lore.addAll(lore);
    }

    public ItemStack getStack() {
        return stack;
    }

    public int getSlot() {
        return slot;
    }

    public String getCommand() {
        return command;
    }

    public String getPermission() {
        return permission;
    }

    public String getName() {
        return name;
    }

    public MenuAction getMenuAction() {
        return menuAction;
    }

    public Menu getMenuToOpen() {
        return menuToOpen;
    }

    public void setMenuToOpen(Menu menuToOpen) {
        this.menuToOpen = menuToOpen;
    }

    public List<String> getLore() {
        return lore;
    }
}

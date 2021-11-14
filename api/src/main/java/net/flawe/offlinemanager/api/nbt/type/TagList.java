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

package net.flawe.offlinemanager.api.nbt.type;

import net.flawe.offlinemanager.api.nbt.TagValue;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TagList implements TagValue<List<TagValue<?>>>, Iterable<TagValue<?>> {

    private List<TagValue<?>> value = new ArrayList<>();

    public TagList(List<TagValue<?>> values) {
        this.value.addAll(values);
    }

    public TagList() { }

    @Override
    public List<TagValue<?>> getValue() {
        return value;
    }

    @Override
    public void setValue(List<TagValue<?>> value) {
        this.value = value;
    }

    public void addValue(TagValue<?> value) {
        this.value.add(value);
    }

    public TagValue<?> getTagValue(int i) {
        return value.get(i);
    }

    @Override
    public TagType getType() {
        return TagType.LIST;
    }

    @NotNull
    @Override
    public Iterator<TagValue<?>> iterator() {
        return value.iterator();
    }

}

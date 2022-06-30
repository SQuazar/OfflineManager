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

package net.quazar.offlinemanager.api.util.v1_18_R1.data;

import net.minecraft.nbt.*;
import net.quazar.offlinemanager.api.nbt.ITagAdapter;
import net.quazar.offlinemanager.api.nbt.TagValue;
import net.quazar.offlinemanager.api.nbt.type.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TagAdapter implements ITagAdapter {

    private final NBTTagCompound tag;

    public TagAdapter(NBTTagCompound tag) {
        this.tag = tag;
    }

    @Override
    public TagValue<?> getTagValue(@NotNull String key) {
        NBTBase nbtBase = tag.c(key);
        return getTagValue(nbtBase);
    }

    @Override
    public ITagAdapter getTagCompound(@NotNull String key) {
        NBTBase base = tag.c(key);
        if (!(base instanceof NBTTagCompound)) return null;
        return new TagAdapter((NBTTagCompound) base);
    }

    @Override
    public void setValue(@NotNull String key, @Nullable TagValue<?> value) {
        if (value == null) {
            tag.r(key);
            return;
        }
        tag.a(key, getNBTValue(value));
    }

    private NBTBase getNBTValue(TagValue<?> value) {
        switch (value.getType()) {
            case BYTE:
                return NBTTagByte.a(((TagByte) value).getValue());
            case SHORT:
                return NBTTagShort.a(((TagShort) value).getValue());
            case INTEGER:
                return NBTTagInt.a(((TagInteger) value).getValue());
            case LONG:
                return NBTTagLong.a(((TagLong) value).getValue());
            case FLOAT:
                return NBTTagFloat.a(((TagFloat) value).getValue());
            case DOUBLE:
                return NBTTagDouble.a(((TagDouble) value).getValue());
            case STRING:
                return NBTTagString.a(((TagString) value).getValue());
            case LIST:
                NBTTagList list = new NBTTagList();
                TagList tagList = (TagList) value;
                for (TagValue<?> val : tagList.getValue())
                    list.add(getNBTValue(val));
                return list;
            case COMPOUND: {
                NBTTagCompound compound = new NBTTagCompound();
                CompoundTag compoundTag = (CompoundTag) value;
                TagValue<?> val;
                for (String key : compoundTag.getKeys()) {
                    val = compoundTag.get(key);
                    if (val == null) continue;
                    compound.a(key, getNBTValue(val));
                }
                return compound;
            }
        }
        return null;
    }

    private TagValue<?> getTagValue(NBTBase base) {
        if (base == null) return null;
        switch (base.a()) {
            case TagTypes.BYTE:
                return new TagByte(((NBTTagByte) base).h());
            case TagTypes.SHORT:
                return new TagShort(((NBTTagShort) base).g());
            case TagTypes.INTEGER:
                return new TagInteger(((NBTTagInt) base).f());
            case TagTypes.LONG:
                return new TagLong(((NBTTagLong) base).e());
            case TagTypes.FLOAT:
                return new TagFloat(((NBTTagFloat) base).j());
            case TagTypes.DOUBLE:
                return new TagDouble(((NBTTagDouble) base).i());
            case TagTypes.STRING:
                return new TagString(base.e_());
            case TagTypes.LIST:
                TagList list = new TagList();
                for (NBTBase val : (NBTTagList) base)
                    list.addValue(getTagValue(val));
                return list;
            case TagTypes.COMPOUND: {
                CompoundTag compoundTag = new CompoundTag();
                NBTTagCompound compound = (NBTTagCompound) base;
                NBTBase val;
                for (String key : compound.d()) {
                    val = compound.c(key);
                    if (val == null) continue;
                    compoundTag.set(key, getTagValue(val));
                }
                return compoundTag;
            }
        }
        return null;
    }

    public NBTTagCompound getTag() {
        return tag;
    }

}


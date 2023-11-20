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

package net.quazar.offlinemanager.api.util.v1_12_R1.data;

import net.minecraft.server.v1_12_R1.*;
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
        NBTBase nbtBase = tag.get(key);
        return getTagValue(nbtBase);
    }

    @Override
    public ITagAdapter getTagCompound(@NotNull String key) {
        NBTBase base = tag.get(key);
        if (!(base instanceof NBTTagCompound)) return null;
        return new TagAdapter((NBTTagCompound) base);
    }

    @Override
    public void setValue(@NotNull String key, @Nullable TagValue<?> value) {
        if (value == null) {
            tag.remove(key);
            return;
        }
        tag.set(key, getNBTValue(value));
    }

    private NBTBase getNBTValue(TagValue<?> value) {
        switch (value.getType()) {
            case BYTE:
                return new NBTTagByte(((TagByte) value).getValue());
            case SHORT:
                return new NBTTagShort(((TagShort) value).getValue());
            case INTEGER:
                return new NBTTagInt(((TagInteger) value).getValue());
            case LONG:
                return new NBTTagLong(((TagLong) value).getValue());
            case FLOAT:
                return new NBTTagFloat(((TagFloat) value).getValue());
            case DOUBLE:
                return new NBTTagDouble(((TagDouble) value).getValue());
            case BYTE_ARRAY:
                return new NBTTagByteArray(((TagByteArray) value).getValue());
            case STRING:
                return new NBTTagString(((TagString) value).getValue());
            case LIST:
                NBTTagList list = new NBTTagList();
                TagList tagList = (TagList) value;
                for (TagValue<?> val : tagList)
                    list.add(getNBTValue(val));
                return list;
            case COMPOUND: {
                NBTTagCompound compound = new NBTTagCompound();
                CompoundTag compoundTag = (CompoundTag) value;
                TagValue<?> val;
                for (String key : compoundTag.getKeys()) {
                    val = compoundTag.get(key);
                    if (val == null) continue;
                    compound.set(key, getNBTValue(val));
                }
                return compound;
            }
            case INT_ARRAY:
                return new NBTTagIntArray(((TagIntArray) value).getValue());
            case LONG_ARRAY:
                return new NBTTagLongArray(((TagLongArray) value).getValue());
        }
        return null;
    }

    private TagValue<?> getTagValue(NBTBase base) {
        if (base == null) return null;
        switch (base.getTypeId()) {
            case TagTypes.BYTE:
                return new TagByte(((NBTTagByte) base).g());
            case TagTypes.SHORT:
                return new TagShort(((NBTTagShort) base).f());
            case TagTypes.INTEGER:
                return new TagInteger(((NBTTagInt) base).e());
            case TagTypes.LONG:
                return new TagLong(((NBTTagLong) base).d());
            case TagTypes.FLOAT:
                return new TagFloat(((NBTTagFloat) base).i());
            case TagTypes.DOUBLE:
                return new TagDouble(((NBTTagDouble) base).asDouble());
            case TagTypes.BYTE_ARRAY:
                return new TagByteArray(((NBTTagByteArray) base).c());
            case TagTypes.STRING:
                return new TagString(((NBTTagString) base).c_());
            case TagTypes.LIST:
                TagList list = new TagList();
                NBTTagList tagList = (NBTTagList) base;
                for (int i = 0; i < tagList.size(); i++)
                    list.addValue(getTagValue(tagList.i(i)));
                return list;
            case TagTypes.COMPOUND: {
                CompoundTag compoundTag = new CompoundTag();
                NBTTagCompound compound = (NBTTagCompound) base;
                NBTBase val;
                for (String key : compound.c()) {
                    val = compound.get(key);
                    if (val == null) continue;
                    compoundTag.set(key, getTagValue(val));
                }
                return compoundTag;
            }
            case TagTypes.INT_ARRAY:
                return new TagIntArray(((NBTTagIntArray) base).d());
            case TagTypes.LONG_ARRAY:
                try {
                    long[] value = (long[]) base.getClass().getDeclaredField("b").get(base);
                    return new TagLongArray(value);
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    return null;
                }
        }
        return null;
    }

    public NBTTagCompound getTag() {
        return tag;
    }

}

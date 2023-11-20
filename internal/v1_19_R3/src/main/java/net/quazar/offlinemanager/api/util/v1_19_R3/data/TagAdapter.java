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

package net.quazar.offlinemanager.api.util.v1_19_R3.data;

import net.minecraft.nbt.*;
import net.minecraft.nbt.CompoundTag;
import net.quazar.offlinemanager.api.nbt.ITagAdapter;
import net.quazar.offlinemanager.api.nbt.TagValue;
import net.quazar.offlinemanager.api.nbt.type.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TagAdapter implements ITagAdapter {

    private final CompoundTag tag;

    public TagAdapter(CompoundTag tag) {
        this.tag = tag;
    }

    @Override
    public TagValue<?> getTagValue(@NotNull String key) {
        Tag nbtBase = tag.get(key);
        return getTagValue(nbtBase);
    }

    @Override
    public ITagAdapter getTagCompound(@NotNull String key) {
        Tag base = tag.get(key);
        if (!(base instanceof CompoundTag)) return null;
        return new TagAdapter((CompoundTag) base);
    }

    @Override
    public void setValue(@NotNull String key, @Nullable TagValue<?> value) {
        if (value == null) {
            tag.remove(key);
            return;
        }
        tag.put(key, getNBTValue(value));
    }

    private Tag getNBTValue(TagValue<?> value) {
        switch (value.getType()) {
            case BYTE:
                return ByteTag.valueOf(((TagByte) value).getValue());
            case SHORT:
                return ShortTag.valueOf(((TagShort) value).getValue());
            case INTEGER:
                return IntTag.valueOf(((TagInteger) value).getValue());
            case LONG:
                return LongTag.valueOf(((TagLong) value).getValue());
            case FLOAT:
                return FloatTag.valueOf(((TagFloat) value).getValue());
            case DOUBLE:
                return DoubleTag.valueOf(((TagDouble) value).getValue());
            case BYTE_ARRAY:
                return new ByteArrayTag(((TagByteArray) value).getValue());
            case STRING:
                return StringTag.valueOf(((TagString) value).getValue());
            case LIST:
                ListTag list = new ListTag();
                TagList tagList = (TagList) value;
                for (TagValue<?> val : tagList.getValue())
                    list.add(getNBTValue(val));
                return list;
            case COMPOUND: {
                CompoundTag compound = new CompoundTag();
                net.quazar.offlinemanager.api.nbt.type.CompoundTag compoundTag = (net.quazar.offlinemanager.api.nbt.type.CompoundTag) value;
                TagValue<?> val;
                for (String key : compoundTag.getKeys()) {
                    val = compoundTag.get(key);
                    if (val == null) continue;
                    compound.put(key, getNBTValue(val));
                }
                return compound;
            }
            case INT_ARRAY:
                return new IntArrayTag(((TagIntArray) value).getValue());
            case LONG_ARRAY:
                return new LongArrayTag(((TagLongArray) value).getValue());
        }
        return null;
    }

    private TagValue<?> getTagValue(Tag base) {
        if (base == null) return null;
        switch (base.getId()) {
            case TagTypes.BYTE:
                return new TagByte(((ByteTag) base).getAsByte());
            case TagTypes.SHORT:
                return new TagShort(((ShortTag) base).getAsShort());
            case TagTypes.INTEGER:
                return new TagInteger(((IntTag) base).getAsInt());
            case TagTypes.LONG:
                return new TagLong(((LongTag) base).getAsLong());
            case TagTypes.FLOAT:
                return new TagFloat(((FloatTag) base).getAsFloat());
            case TagTypes.DOUBLE:
                return new TagDouble(((DoubleTag) base).getAsDouble());
            case TagTypes.BYTE_ARRAY:
                return new TagByteArray(((ByteArrayTag) base).getAsByteArray());
            case TagTypes.STRING:
                return new TagString(base.getAsString());
            case TagTypes.LIST:
                TagList list = new TagList();
                for (Tag val : (ListTag) base)
                    list.addValue(getTagValue(val));
                return list;
            case TagTypes.COMPOUND: {
                net.quazar.offlinemanager.api.nbt.type.CompoundTag compoundTag = new net.quazar.offlinemanager.api.nbt.type.CompoundTag();
                CompoundTag compound = (CompoundTag) base;
                Tag val;
                for (String key : compound.getAllKeys()) {
                    val = compound.get(key);
                    if (val == null) continue;
                    compoundTag.set(key, getTagValue(val));
                }
                return compoundTag;
            }
            case TagTypes.INT_ARRAY:
                return new TagIntArray(((IntArrayTag) base).getAsIntArray());
            case TagTypes.LONG_ARRAY:
                return new TagLongArray(((LongArrayTag) base).getAsLongArray());
        }
        return null;
    }

    public CompoundTag getTag() {
        return tag;
    }

}


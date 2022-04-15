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

import com.google.common.collect.Maps;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.flawe.offlinemanager.api.nbt.TagValue;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;

/**
 * Compound tag class
 * @author flawe
 */
@EqualsAndHashCode
@ToString
public class CompoundTag implements TagValue<TagValue<?>> {

    private final Map<String, TagValue<?>> valueMap = Maps.newHashMap();

    @Override
    public TagValue<?> getValue() {
        return this;
    }

    /**
     * Gets tag value by specified key
     * @param key tag key
     * @return tag value by key
     */
    @Nullable
    public TagValue<?> get(String key) {
        return valueMap.get(key);
    }

    /**
     * Gets keys collection in tag compound
     * @return keys collection
     */
    public Set<String> getKeys() {
        return valueMap.keySet();
    }

    /**
     * @throws UnsupportedOperationException this operation cannot be applied to this class
     */
    @Override
    public void setValue(TagValue<?> value) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This operation cannot be applied to this class.");
    }

    /**
     * Sets string value by specific key
     * @param key tag key
     * @param value tag value
     */
    public void setString(String key, String value) {
        valueMap.put(key, new TagString(value));
    }

    /**
     * Sets integer value by specific key
     * @param key tag key
     * @param value tag value
     */
    public void setInt(String key, int value) {
        valueMap.put(key, new TagInteger(value));
    }

    /**
     * Sets double value by specific key
     * @param key tag key
     * @param value tag value
     */
    public void setDouble(String key, double value) {
        valueMap.put(key, new TagDouble(value));
    }

    /**
     * Sets short value by specific key
     * @param key tag key
     * @param value tag value
     */
    public void setShort(String key, short value) {
        valueMap.put(key, new TagShort(value));
    }

    /**
     * Sets byte value by specific key
     * @param key tag key
     * @param value tag value
     */
    public void setByte(String key, byte value) {
        valueMap.put(key, new TagByte(value));
    }

    private static final TagByte byteFalse = new TagByte((byte) 0);
    private static final TagByte byteTrue = new TagByte((byte) 1);

    /**
     * Sets boolean value by specific key
     * @param key tag key
     * @param value tag value
     */
    public void setBoolean(String key, boolean value) {
        valueMap.put(key, value ? byteTrue : byteFalse);
    }

    /**
     * Sets float value by specific key
     * @param key tag key
     * @param value tag value
     */
    public void setFloat(String key, float value) {
        valueMap.put(key, new TagFloat(value));
    }

    /**
     * Sets long value by specific key
     * @param key tag key
     * @param value tag value
     */
    public void setLong(String key, long value) {
        valueMap.put(key, new TagLong(value));
    }

    /**
     * Sets value by specific key
     * @param key tag key
     * @param value tag value
     */
    public void set(String key, TagValue<?> value) {
        valueMap.put(key, value);
    }

    /**
     * Checks if the tag compound contains key with specific type
     * @param key tag key
     * @param type tag type
     * @return true if tag compound contains key with specific type
     */
    private boolean hasKeyOfType(String key, TagType type) {
        TagValue<?> value = valueMap.get(key);
        if (value == null) return false;
        return value.getType() == type;
    }

    /**
     * Gets string value by specific key
     * @param key tag key
     * @return tag string value by key
     */
    public String getString(String key) {
        if (!hasKeyOfType(key, TagType.STRING)) return "";
        return ((TagString) valueMap.get(key)).getValue();
    }

    /**
     * Gets integer value by specific key
     * @param key tag key
     * @return tag integer value by key
     */
    public int getInt(String key) {
        if (!hasKeyOfType(key, TagType.INTEGER)) return 0;
        return ((TagInteger) valueMap.get(key)).getValue();
    }

    /**
     * Gets double value by specific key
     * @param key tag key
     * @return tag double value by key
     */
    public double getDouble(String key) {
        if (!hasKeyOfType(key, TagType.DOUBLE)) return 0.0;
        return ((TagDouble) valueMap.get(key)).getValue();
    }

    /**
     * Gets short value by specific key
     * @param key tag key
     * @return tag short value by key
     */
    public short getShort(String key) {
        if (!hasKeyOfType(key, TagType.SHORT)) return 0;
        return ((TagShort) valueMap.get(key)).getValue();
    }

    /**
     * Gets  byte value by specific key
     * @param key tag key
     * @return tag byte value by key
     */
    public byte getByte(String key) {
        if (!hasKeyOfType(key, TagType.BYTE)) return 0;
        return ((TagByte) valueMap.get(key)).getValue();
    }

    /**
     * Gets boolean value by specific key
     * @param key tag key
     * @return tag boolean value by key
     */
    public boolean getBoolean(String key) {
        if (!hasKeyOfType(key, TagType.BYTE)) return false;
        return ((TagByte) valueMap.get(key)).getValue() == 1;
    }

    /**
     * Gets float value by specific key
     * @param key tag key
     * @return tag float value by key
     */
    public float getFloat(String key) {
        if (!hasKeyOfType(key, TagType.FLOAT)) return 0.0f;
        return ((TagFloat) valueMap.get(key)).getValue();
    }

    /**
     * Gets long value by specific key
     * @param key tag key
     * @return tag long value by key
     */
    public long getLong(String key) {
        if (!hasKeyOfType(key, TagType.LONG)) return 0L;
        return ((TagLong) valueMap.get(key)).getValue();
    }

    /**
     * Gets compound tag with specified key
     * @param key compound tag key
     * @return compound tag by key
     */
    public CompoundTag getCompoundTag(String key) {
        if (!hasKeyOfType(key, TagType.COMPOUND)) return new CompoundTag();
        return (CompoundTag) valueMap.get(key);
    }

    /**
     * Gets tag list by specific key
     * @param key tag key
     * @return tag list by key
     */
    public TagList getList(String key) {
        if (!hasKeyOfType(key, TagType.LIST)) return new TagList();
        return (TagList) valueMap.get(key);
    }

    /**
     * Removes tag by specific key
     * @param key tag key
     */
    public void remove(String key) {
        valueMap.remove(key);
    }

    /**
     * Gets tag type
     * @return tag type
     */
    @Override
    public TagType getType() {
        return TagType.COMPOUND;
    }
}

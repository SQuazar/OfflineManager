package net.flawe.offlinemanager.api.nbt;

import net.flawe.offlinemanager.api.nbt.type.TagType;

/**
 * Tag value
 * @param <T> tag type
 */
public interface TagValue<T> {

    /**
     * Gets the tag value
     * @return value
     */
    T getValue();

    /**
     * Set new tag value
     * @param value new value
     */
    void setValue(T value);

    /**
     * Gets the tag type
     * @return tag type
     */
    TagType getType();
}

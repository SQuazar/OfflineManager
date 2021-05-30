package net.flawe.offlinemanager.api.nbt;

import net.flawe.offlinemanager.api.nbt.type.TagType;

public interface TagValue<T> {

    T getValue();

    void setValue(T value);

    TagType getType();
}

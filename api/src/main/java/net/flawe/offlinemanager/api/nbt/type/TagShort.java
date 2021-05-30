package net.flawe.offlinemanager.api.nbt.type;

import net.flawe.offlinemanager.api.nbt.TagValue;

public class TagShort implements TagValue<Short> {

    private short value;

    public TagShort(short value) {
        this.value = value;
    }

    @Override
    public Short getValue() {
        return value;
    }

    @Override
    public void setValue(Short value) {
        this.value = value;
    }

    @Override
    public TagType getType() {
        return TagType.SHORT;
    }
}

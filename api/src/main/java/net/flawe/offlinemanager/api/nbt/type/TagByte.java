package net.flawe.offlinemanager.api.nbt.type;

import net.flawe.offlinemanager.api.nbt.TagValue;

public class TagByte implements TagValue<Byte> {

    private byte value;

    public TagByte(byte value) {
        this.value = value;
    }

    @Override
    public Byte getValue() {
        return value;
    }

    @Override
    public void setValue(Byte value) {
        this.value = value;
    }

    @Override
    public TagType getType() {
        return TagType.BYTE;
    }
}

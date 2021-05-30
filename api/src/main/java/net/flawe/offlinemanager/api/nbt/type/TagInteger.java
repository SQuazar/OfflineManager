package net.flawe.offlinemanager.api.nbt.type;

import net.flawe.offlinemanager.api.nbt.TagValue;

public class TagInteger implements TagValue<Integer> {

    private int value;

    public TagInteger(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public TagType getType() {
        return TagType.INTEGER;
    }
}

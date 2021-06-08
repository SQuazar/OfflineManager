package net.flawe.offlinemanager.api.nbt.type;

import net.flawe.offlinemanager.api.nbt.TagValue;

public class TagFloat implements TagValue<Float> {

    private float value;

    public TagFloat(float value) {
        this.value = value;
    }

    @Override
    public Float getValue() {
        return value;
    }

    @Override
    public void setValue(Float value) {
        this.value = value;
    }

    @Override
    public TagType getType() {
        return TagType.FLOAT;
    }
}

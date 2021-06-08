package net.flawe.offlinemanager.api.nbt.type;

import net.flawe.offlinemanager.api.nbt.TagValue;

public class TagDouble implements TagValue<Double> {

    private double value;

    public TagDouble(double value) {
        this.value = value;
    }

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public TagType getType() {
        return TagType.DOUBLE;
    }
}

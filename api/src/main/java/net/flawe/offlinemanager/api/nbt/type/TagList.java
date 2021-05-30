package net.flawe.offlinemanager.api.nbt.type;

import net.flawe.offlinemanager.api.nbt.TagValue;

import java.util.ArrayList;
import java.util.List;

public class TagList implements TagValue<List<TagValue<?>>> {

    private List<TagValue<?>> value = new ArrayList<>();

    public TagList(List<TagValue<?>> values) {
        this.value.addAll(values);
    }

    public TagList() { }

    @Override
    public List<TagValue<?>> getValue() {
        return value;
    }

    @Override
    public void setValue(List<TagValue<?>> value) {
        this.value = value;
    }

    public void addValue(TagValue<?> value) {
        this.value.add(value);
    }

    public TagValue<?> getTagValue(int i) {
        return value.get(i);
    }

    @Override
    public TagType getType() {
        return TagType.LIST;
    }
}

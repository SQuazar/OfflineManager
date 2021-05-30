package net.flawe.offlinemanager.api.nbt;

public interface ITagCompound {

    TagValue<?> getTagValue(String key);

    ITagCompound getTagCompound(String key);

    void setValue(String key, TagValue<?> value);

}

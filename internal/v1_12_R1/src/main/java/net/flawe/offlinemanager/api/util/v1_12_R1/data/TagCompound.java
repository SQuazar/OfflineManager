package net.flawe.offlinemanager.api.util.v1_12_R1.data;

import net.flawe.offlinemanager.api.nbt.ITagCompound;
import net.flawe.offlinemanager.api.nbt.TagValue;
import net.flawe.offlinemanager.api.nbt.type.*;
import net.minecraft.server.v1_12_R1.*;

public class TagCompound implements ITagCompound {

    private NBTTagCompound tag;

    public TagCompound(NBTTagCompound tag) {
        this.tag = tag;
    }

    @Override
    public TagValue<?> getTagValue(String key) {
        NBTBase nbtBase = tag.get(key);
        return getTagValue(nbtBase);
    }

    @Override
    public ITagCompound getTagCompound(String key) {
        NBTBase base = tag.get(key);
        if (!(base instanceof NBTTagCompound)) return null;
        return new TagCompound((NBTTagCompound) base);
    }

    @Override
    public void setValue(String key, TagValue<?> value) {
        tag.set(key, getNBTValue(value));
    }

    private NBTBase getNBTValue(TagValue<?> value) {
        switch (value.getType()) {
            case BYTE:
                return new NBTTagByte(((TagByte) value).getValue());
            case SHORT:
                return new NBTTagShort(((TagShort) value).getValue());
            case INTEGER:
                return new NBTTagInt(((TagInteger) value).getValue());
            case LONG:
                return new NBTTagLong(((TagLong) value).getValue());
            case FLOAT:
                return new NBTTagFloat(((TagFloat) value).getValue());
            case DOUBLE:
                return new NBTTagDouble(((TagDouble) value).getValue());
            case STRING:
                return new NBTTagString(((TagString) value).getValue());
            case LIST:
                NBTTagList list = new NBTTagList();
                TagList tagList = (TagList) value;
                for (TagValue<?> val : tagList.getValue())
                    list.add(getNBTValue(val));
                return list;
        }
        return null;
    }

    private TagValue<?> getTagValue(NBTBase base) {
        if (base == null) return null;
        switch (base.getTypeId()) {
            case TagTypes.BYTE:
                return new TagByte(((NBTTagByte) base).g());
            case TagTypes.SHORT:
                return new TagShort(((NBTTagShort) base).f());
            case TagTypes.INTEGER:
                return new TagInteger(((NBTTagInt) base).e());
            case TagTypes.LONG:
                return new TagLong(((NBTTagLong) base).d());
            case TagTypes.FLOAT:
                return new TagFloat(((NBTTagFloat) base).i());
            case TagTypes.DOUBLE:
                return new TagDouble(((NBTTagDouble) base).asDouble());
            case TagTypes.STRING:
                return new TagString(((NBTTagString) base).c_());
            case TagTypes.LIST:
                TagList list = new TagList();
                NBTTagList tagList = (NBTTagList) base;
                for (int i = 0; i < tagList.size(); i++)
                    list.addValue(getTagValue(tagList.i(i)));
                return list;
        }
        return null;
    }

    public NBTTagCompound getTag() {
        return tag;
    }

}

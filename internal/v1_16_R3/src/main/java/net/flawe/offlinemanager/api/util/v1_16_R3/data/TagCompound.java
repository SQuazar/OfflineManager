package net.flawe.offlinemanager.api.util.v1_16_R3.data;

import net.flawe.offlinemanager.api.nbt.ITagCompound;
import net.flawe.offlinemanager.api.nbt.TagValue;
import net.flawe.offlinemanager.api.nbt.type.*;
import net.minecraft.server.v1_16_R3.*;

public class TagCompound implements ITagCompound {

    private final NBTTagCompound tag;

    public TagCompound(NBTTagCompound tag) {
        this.tag = tag;
    }

    @Override
    public TagValue<?> getTagValue(String key) {
        NBTBase nbtBase = tag.get(key);
/*        if (nbtBase == null) return null;
        switch (nbtBase.getTypeId()) {
            case TagTypes.BYTE:
                return new TagByte(((NBTTagByte) nbtBase).asByte());
            case TagTypes.SHORT:
                return new TagShort(((NBTTagShort) nbtBase).asShort());
            case TagTypes.INTEGER:
                return new TagInteger(((NBTTagInt) nbtBase).asInt());
            case TagTypes.LONG:
                return new TagLong(((NBTTagLong) nbtBase).asLong());
            case TagTypes.FLOAT:
                return new TagFloat(((NBTTagFloat) nbtBase).asFloat());
            case TagTypes.STRING:
                return new TagString(nbtBase.asString());
        }
        return null;*/
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
/*        switch (value.getType()) {
            case BYTE:
                tag.set(key, NBTTagByte.a(((TagByte) value).getValue()));
                break;
            case SHORT:
                tag.set(key, NBTTagShort.a(((TagShort) value).getValue()));
                break;
            case INTEGER:
                tag.set(key, NBTTagInt.a(((TagInteger) value).getValue()));
                break;
            case LONG:
                tag.set(key, NBTTagLong.a(((TagLong) value).getValue()));
                break;
            case FLOAT:
                tag.set(key, NBTTagFloat.a(((TagFloat) value).getValue()));
                break;
            case STRING:
                tag.set(key, NBTTagString.a(((TagString) value).getValue()));
                break;
            case LIST:
                NBTTagList list = new NBTTagList();
                TagList tagList = (TagList) value;
                for (TagValue<?> val : tagList.getValue()) {
                    list.add(getNBTValue(val));
                }
                tag.set(key, list);
                break;
        }*/
    }

    private NBTBase getNBTValue(TagValue<?> value) {
        switch (value.getType()) {
            case BYTE:
                return NBTTagByte.a(((TagByte) value).getValue());
            case SHORT:
                return NBTTagShort.a(((TagShort) value).getValue());
            case INTEGER:
                return NBTTagInt.a(((TagInteger) value).getValue());
            case LONG:
                return NBTTagLong.a(((TagLong) value).getValue());
            case FLOAT:
                return NBTTagFloat.a(((TagFloat) value).getValue());
            case DOUBLE:
                return NBTTagDouble.a(((TagDouble) value).getValue());
            case STRING:
                return NBTTagString.a(((TagString) value).getValue());
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
                return new TagByte(((NBTTagByte) base).asByte());
            case TagTypes.SHORT:
                return new TagShort(((NBTTagShort) base).asShort());
            case TagTypes.INTEGER:
                return new TagInteger(((NBTTagInt) base).asInt());
            case TagTypes.LONG:
                return new TagLong(((NBTTagLong) base).asLong());
            case TagTypes.FLOAT:
                return new TagFloat(((NBTTagFloat) base).asFloat());
            case TagTypes.DOUBLE:
                return new TagDouble(((NBTTagDouble) base).asDouble());
            case TagTypes.STRING:
                return new TagString(base.asString());
            case TagTypes.LIST:
                TagList list = new TagList();
                for (NBTBase val : (NBTTagList) base)
                    list.addValue(getTagValue(val));
                return list;
        }
        return null;
    }

    public NBTTagCompound getTag() {
        return tag;
    }

    private static class TagTypes {
        public static final byte BYTE = 1;
        public static final byte SHORT = 2;
        public static final byte INTEGER = 3;
        public static final byte LONG = 4;
        private static final byte FLOAT = 5;
        private static final byte DOUBLE = 6;
        public static final byte STRING = 8;
        public static final byte LIST = 9;
    }

}

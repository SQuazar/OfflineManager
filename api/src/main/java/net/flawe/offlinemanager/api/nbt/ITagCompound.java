package net.flawe.offlinemanager.api.nbt;

public interface ITagCompound {

    TagValue<?> getTagValue(String key);

    ITagCompound getTagCompound(String key);

    void setValue(String key, TagValue<?> value);

    class TagTypes {
        public static final byte BYTE = 1;
        public static final byte SHORT = 2;
        public static final byte INTEGER = 3;
        public static final byte LONG = 4;
        public static final byte FLOAT = 5;
        public static final byte DOUBLE = 6;
        public static final byte STRING = 8;
        public static final byte LIST = 9;
    }

}

package net.flawe.offlinemanager.api.nbt;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The class adapter for using nms tag compound
 * @author flawe
 */
public interface ITagCompound {

    /**
     * Gets the tag value by key
     * @param key tag key
     * @return search results
     */
    @Nullable
    TagValue<?> getTagValue(@NotNull String key);

    /**
     * Gets the tag compound by key
     * @param key tag key
     * @return search results
     */
    @Nullable
    ITagCompound getTagCompound(@NotNull String key);

    /**
     * Set value in tag compound
     * @param key new tag key
     * @param value new tag value
     */
    void setValue(@NotNull String key, @NotNull TagValue<?> value);

    /**
     * Tag types
     * @author flawe
     */
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

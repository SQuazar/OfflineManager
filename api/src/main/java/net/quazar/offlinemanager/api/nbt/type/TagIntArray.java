package net.quazar.offlinemanager.api.nbt.type;

import lombok.*;
import net.quazar.offlinemanager.api.nbt.TagValue;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TagIntArray implements TagValue<int[]> {
    private int[] value;

    @Override
    public TagType getType() {
        return TagType.INT_ARRAY;
    }
}

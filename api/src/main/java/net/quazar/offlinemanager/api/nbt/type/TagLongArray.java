package net.quazar.offlinemanager.api.nbt.type;

import lombok.*;
import net.quazar.offlinemanager.api.nbt.TagValue;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TagLongArray implements TagValue<long[]> {
    private long[] value;

    @Override
    public TagType getType() {
        return TagType.LONG_ARRAY;
    }
}

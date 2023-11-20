package net.quazar.offlinemanager.api.nbt.type;

import lombok.*;
import net.quazar.offlinemanager.api.nbt.TagValue;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TagByteArray implements TagValue<byte[]> {
    private byte[] value;

    @Override
    public TagType getType() {
        return TagType.BYTE_ARRAY;
    }
}

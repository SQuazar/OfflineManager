/*
 * Copyright (c) 2021 squazar
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.quazar.offlinemanager.api.nbt.type;

/**
 * NBT tag types
 * @author quazar
 */
public enum TagType {
    BYTE((byte) 1),
    SHORT((byte) 2),
    INTEGER((byte) 3),
    LONG((byte) 4),
    FLOAT((byte) 5),
    DOUBLE((byte) 6),
    BYTE_ARRAY((byte) 7),
    STRING((byte) 8),
    LIST((byte) 9),
    COMPOUND((byte) 10),
    INT_ARRAY((byte) 11),
    LONG_ARRAY((byte) 12);

    private final byte type;

    /**
     * TagType constructor
     * @param type tag type from byte representation
     */
    TagType(byte type) {
        this.type = type;
    }

    /**
     * Gets the tag type from its byte representation
     * @return tag type from byte representation
     */
    public byte getType() {
        return type;
    }
}

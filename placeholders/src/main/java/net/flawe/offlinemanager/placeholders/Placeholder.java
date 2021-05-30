package net.flawe.offlinemanager.placeholders;

import net.flawe.offlinemanager.api.IPlaceholder;

import java.util.Objects;

public class Placeholder implements IPlaceholder {

    private final String key;
    private final String value;

    public Placeholder(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Placeholder that = (Placeholder) o;
        return Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

}

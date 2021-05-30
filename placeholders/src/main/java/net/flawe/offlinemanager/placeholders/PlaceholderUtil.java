package net.flawe.offlinemanager.placeholders;

import net.flawe.offlinemanager.api.IPlaceholder;

import java.util.Collection;
import java.util.Map;

public class PlaceholderUtil {

    public static String fillPlaceholders(String message, Map<String, String> placeholders) {
        for (Map.Entry<String, String> entry : placeholders.entrySet())
            message = message.replace(entry.getKey(), entry.getValue());
        return message;
    }

    public static String fillPlaceholders(String message, Collection<IPlaceholder> placeholders) {
        for (IPlaceholder placeholder : placeholders)
            message = message.replace(placeholder.getKey(), placeholder.getValue());
        return message;
    }

}

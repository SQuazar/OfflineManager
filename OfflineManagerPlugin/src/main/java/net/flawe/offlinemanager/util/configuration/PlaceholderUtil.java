package net.flawe.offlinemanager.util.configuration;

import java.util.Map;

public class PlaceholderUtil {
    public static String fillPlaceholders(String s, Map<String, String> placeholders) {
        for (Map.Entry<String, String> entry : placeholders.entrySet())
            s = s.replace(entry.getKey(), entry.getValue());
        return s;
    }
}

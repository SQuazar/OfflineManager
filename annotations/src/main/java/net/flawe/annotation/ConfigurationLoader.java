package net.flawe.annotation;

import net.flawe.annotation.util.Comment;
import net.flawe.annotation.util.ConfigKey;
import net.flawe.annotation.util.Indents;
import net.flawe.offlinemanager.api.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that is used to load configuration parameters to sample configuration class
 * @author flawe
 */
public class ConfigurationLoader {

    /**
     * Load configuration parameters to class fields.
     * @param configuration Configuration class, where will added configuration parameters
     * @param fileConfiguration This class whose parameters will copied to configuration class
     */
    public static void loadConfiguration(@NotNull Configuration configuration, @NotNull FileConfiguration fileConfiguration) {
        loadConfiguration(configuration, fileConfiguration, "");
    }

    /**
     * Saving configuration file
     * @param configuration Configuration class
     * @param file File where configuration will be saved
     * @throws IllegalAccessException Extends from dump method. See method documentation for get more info
     * @throws IOException Will be thrown if there is any problem with the file or file system
     */
    public static void saveConfiguration(Configuration configuration, File file) throws IllegalAccessException, IOException {
        List<String> out = new ArrayList<>();
        dump(configuration, "", out);
        File tmp = new File(file.getParentFile() + "___tmpconfig");
        Files.write(tmp.toPath(), out, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
        Files.move(tmp.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
    }

    /**
     * Using for load configuration for saving
     * @param configuration Configuration class
     * @param skip Spaces before configuration key
     * @param out List where will be loaded configuration
     * @throws IllegalAccessException Throw exception if field cannot set public modifier or any another problem with access the file
     */
    private static void dump(Configuration configuration, String skip, List<String> out) throws IllegalAccessException {
        if (configuration.getClass().isAnnotationPresent(Comment.class)) {
            Comment comment = configuration.getClass().getAnnotation(Comment.class);
            for (String s : comment.value())
                out.add("# " + s);
        }
        for (Field field : configuration.getClass().getDeclaredFields()) {
            if (!field.isAnnotationPresent(ConfigKey.class))
                continue;
            if (!field.isAccessible())
                field.setAccessible(true);
            ConfigKey key = field.getAnnotation(ConfigKey.class);
            String keyValue = key.value();
            if (field.isAnnotationPresent(Indents.class)) {
                Indents space = field.getAnnotation(Indents.class);
                for (int i = 0; i < space.value(); i++)
                    out.add("");
            }
            if (field.isAnnotationPresent(Comment.class)) {
                Comment comment = field.getAnnotation(Comment.class);
                for (String s : comment.value())
                    out.add(skip + "# " + s);
            }
            Object value = field.get(configuration);
            if (value instanceof Configuration) {
                if (!field.isAnnotationPresent(ConfigKey.class))
                    continue;
                Configuration configuration1 = (Configuration) value;
                out.add(skip + keyValue + ":");
                dump(configuration1, "  ", out);
                continue;
            }
            if (value instanceof String) {
                value = "\"" + value + "\"";
            }
            if (value instanceof List<?>) {
                List<?> list = (List<?>) value;
                StringBuilder builder = new StringBuilder("\n");
                for (int a = 0; a < list.size(); a++) {
                    Object obj = list.get(a);
                    if (obj instanceof String)
                        obj = "'" + obj + "'";
                    builder.append(skip).append("  ").append('-').append(' ').append(obj);
                    if (a < list.size() - 1) builder.append("\n");
                }
                value = builder.toString();
            }
            out.add(skip + keyValue + ": " + value);
        }
    }

    /**
     * Load configuration parameters to class fields
     * @param configuration Configuration class, where will added configuration parameters
     * @param fileConfiguration This class whose parameters will copied to configuration class
     * @param path Configuration parameter path. Used for multi-level options
     */
    private static void loadConfiguration(Configuration configuration, FileConfiguration fileConfiguration, @NotNull String path) {
        if (fileConfiguration == null)
            return;
        for (Field field : configuration.getClass().getDeclaredFields()) {
            try {
                if (!field.isAnnotationPresent(ConfigKey.class))
                    continue;
                if (!field.isAccessible())
                    field.setAccessible(true);
                ConfigKey key = field.getAnnotation(ConfigKey.class);
                String finalPath = path.isEmpty() ? key.value() : path + "." + key.value();
                if (field.get(configuration) instanceof Configuration) {
                    loadConfiguration((Configuration) field.get(configuration), fileConfiguration, finalPath);
                    continue;
                }
                Object value = fileConfiguration.get(finalPath);
                if (value == null)
                    continue;
                removeFinal(field);
                field.set(configuration, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Removing final modifier from field
     * @param field Filed where removed final modifier
     */
    private static void removeFinal(@NotNull Field field) {
        if (!Modifier.isFinal(field.getModifiers()))
            return;
        try {
            Field modifiers = Field.class.getDeclaredField("modifiers");
            modifiers.setAccessible(true);
            modifiers.set(field, field.getModifiers() & ~Modifier.FINAL);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}

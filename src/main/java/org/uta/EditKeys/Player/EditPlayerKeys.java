package org.uta.EditKeys.Player;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class EditPlayerKeys {
    private static Plugin plugin;

    public static void initialize(Plugin pluginInstance) {
        plugin = pluginInstance;
    }

    /* ================================ Setter ================================ */

    public static void setString(Player player, String keyName, String value) {
        set(player, keyName, PersistentDataType.STRING, value);
    }

    public static void setInteger(Player player, String keyName, Integer value) {
        set(player, keyName, PersistentDataType.INTEGER, value);
    }

    public static void setDouble(Player player, String keyName, Double value) {
        set(player, keyName, PersistentDataType.DOUBLE, value);
    }

    public static void setFloat(Player player, String keyName, Float value) {
        set(player, keyName, PersistentDataType.FLOAT, value);
    }

    public static void setBoolean(Player player, String keyName, Boolean value) {
        set(player, keyName, PersistentDataType.BOOLEAN, value);
    }

    private static <T, Z> void set(Player player, String keyName, PersistentDataType<T, Z> type, Z value) {
        NamespacedKey key = new NamespacedKey(plugin, keyName);
        player.getPersistentDataContainer().set(key, type, value);
    }

    /* ================================ Getter ================================ */

    public static String getString(Player player, String keyName) {
        return get(player, keyName, PersistentDataType.STRING, "null");
    }

    public static Integer getInteger(Player player, String keyName) {
        return get(player, keyName, PersistentDataType.INTEGER, 0);
    }

    public static Double getDouble(Player player, String keyName) {
        return get(player, keyName, PersistentDataType.DOUBLE, 0.0);
    }

    public static Float getFloat(Player player, String keyName) {
        return get(player, keyName, PersistentDataType.FLOAT, 0f);
    }

    public static Boolean getBoolean(Player player, String keyName) {
        return get(player, keyName, PersistentDataType.BOOLEAN, false);
    }

    private static <T, Z> Z get(Player player, String keyName, PersistentDataType<T, Z> type, Z defaultValue) {
        NamespacedKey key = new NamespacedKey(plugin, keyName);
        PersistentDataContainer container = player.getPersistentDataContainer();
        return container.has(key, type) ? container.get(key, type) : defaultValue;
    }

    /* ============================== Generic Set ============================== */

    public static void set(Player player, String keyName, Object value) {
        if (player == null || value == null) return;

        NamespacedKey key = new NamespacedKey(plugin, keyName);
        PersistentDataContainer container = player.getPersistentDataContainer();

        switch (value) {
            case String s     -> container.set(key, PersistentDataType.STRING, s);
            case Integer i    -> container.set(key, PersistentDataType.INTEGER, i);
            case Double d     -> container.set(key, PersistentDataType.DOUBLE, d);
            case Float f      -> container.set(key, PersistentDataType.FLOAT, f);
            case Boolean b    -> container.set(key, PersistentDataType.BOOLEAN, b);
            default           -> System.out.println("Unsupported type for key: " + keyName);
        }
    }

    public static Object get(Player player, String keyName) {
        if (player == null) return null;

        NamespacedKey key = new NamespacedKey(plugin, keyName);
        PersistentDataContainer container = player.getPersistentDataContainer();

        if (container.has(key, PersistentDataType.STRING))  return container.get(key, PersistentDataType.STRING);
        if (container.has(key, PersistentDataType.INTEGER)) return container.get(key, PersistentDataType.INTEGER);
        if (container.has(key, PersistentDataType.DOUBLE))  return container.get(key, PersistentDataType.DOUBLE);
        if (container.has(key, PersistentDataType.FLOAT))   return container.get(key, PersistentDataType.FLOAT);
        if (container.has(key, PersistentDataType.BOOLEAN)) return container.get(key, PersistentDataType.BOOLEAN);

        return null;
    }

    /* ============================== Utilities ============================== */

    public static boolean has(Player player, String keyName) {
        return player.getPersistentDataContainer().getKeys().stream()
                .anyMatch(key -> key.getKey().equals(keyName));
    }

    public static void remove(Player player, String keyName) {
        NamespacedKey key = new NamespacedKey(plugin, keyName);
        player.getPersistentDataContainer().remove(key);
    }

    private static PersistentDataType<?, ?> getPersistentDataType(Object value) {
        if (value instanceof String)  return PersistentDataType.STRING;
        if (value instanceof Integer) return PersistentDataType.INTEGER;
        if (value instanceof Double)  return PersistentDataType.DOUBLE;
        if (value instanceof Float)   return PersistentDataType.FLOAT;
        if (value instanceof Boolean) return PersistentDataType.BOOLEAN;
        return null;
    }
}

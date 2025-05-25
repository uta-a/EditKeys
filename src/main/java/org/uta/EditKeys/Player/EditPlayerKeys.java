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

    /* ========================================================================================================= */

    // set str
    public static void setString(Player player, String keyName, String str) {
        NamespacedKey key = new NamespacedKey(plugin, keyName);
        player.getPersistentDataContainer().set(key, PersistentDataType.STRING, str);
    }

    // set int
    public static void setInteger(Player player, String keyName, Integer num) {
        NamespacedKey key = new NamespacedKey(plugin, keyName);
        player.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, num);
    }

    // set double
    public static void setDouble(Player player, String keyName, Double num) {
        NamespacedKey key = new NamespacedKey(plugin, keyName);
        player.getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, num);
    }

    // set float
    public static void setFloat(Player player, String keyName, Float num) {
        NamespacedKey key = new NamespacedKey(plugin, keyName);
        player.getPersistentDataContainer().set(key, PersistentDataType.FLOAT, num);
    }

    // set int
    public static void setBoolean(Player player, String keyName, Boolean bool) {
        NamespacedKey key = new NamespacedKey(plugin, keyName);
        player.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, bool);
    }

    /* ========================================================================================================= */

    // 持っているか
    public static boolean has(Player player, String keyName) {
        return player.getPersistentDataContainer().getKeys().stream()
                .anyMatch(key -> key.getKey().equals(keyName));
    }

    /* ========================================================================================================= */

    // get string
    public static String getString(Player player, String keyName) {
        NamespacedKey key = new NamespacedKey(plugin, keyName);
        PersistentDataContainer container = player.getPersistentDataContainer();
        if (!container.has(key, PersistentDataType.STRING)) return "null";
        String value = player.getPersistentDataContainer().get(key, PersistentDataType.STRING);
        return EditPlayerKeys.has(player, keyName) ? value : "null";
    }

    // get integer
    public static Integer getInteger(Player player, String keyName) {
        NamespacedKey key = new NamespacedKey(plugin, keyName);
        PersistentDataContainer container = player.getPersistentDataContainer();
        if (!container.has(key, PersistentDataType.INTEGER)) return 0;
        Integer value = player.getPersistentDataContainer().get(key, PersistentDataType.INTEGER);
        return EditPlayerKeys.has(player, keyName) ? value : Integer.valueOf(0);
    }

    // get double
    public static Double getDouble(Player player, String keyName) {
        NamespacedKey key = new NamespacedKey(plugin, keyName);
        PersistentDataContainer container = player.getPersistentDataContainer();
        if (!container.has(key, PersistentDataType.DOUBLE)) return 0d;
        Double value = player.getPersistentDataContainer().get(key, PersistentDataType.DOUBLE);
        return EditPlayerKeys.has(player, keyName) ? value : Double.valueOf(0);
    }

    // get float
    public static Float getFloat(Player player, String keyName) {
        NamespacedKey key = new NamespacedKey(plugin, keyName);
        PersistentDataContainer container = player.getPersistentDataContainer();
        if (!container.has(key, PersistentDataType.FLOAT)) return 0f;
        Float value = player.getPersistentDataContainer().get(key, PersistentDataType.FLOAT);
        return EditPlayerKeys.has(player, keyName) ? value : Float.valueOf(0);
    }

    // get boolean
    public static Boolean getBoolean(Player player, String keyName) {
        NamespacedKey key = new NamespacedKey(plugin, keyName);
        PersistentDataContainer container = player.getPersistentDataContainer();
        if (!container.has(key, PersistentDataType.BOOLEAN)) return false;
        Boolean value = player.getPersistentDataContainer().get(key, PersistentDataType.BOOLEAN);
        return EditPlayerKeys.has(player, keyName) ? value : Boolean.valueOf(false);
    }

    /* ========================================================================================================= */

    // remove
    public static void remove(Player player, String keyName) {
        NamespacedKey key = new NamespacedKey(plugin, keyName);
        player.getPersistentDataContainer().remove(key);
    }

    /* ========================================================================================================= */

    // set any
    public static void set(Player player, String keyName, Object value) {
        if (player == null || value == null) return;

        NamespacedKey key = new NamespacedKey(plugin, keyName);
        PersistentDataContainer container = player.getPersistentDataContainer();

        switch (value) {
            case String s -> container.set(key, PersistentDataType.STRING, s);
            case Integer integer -> container.set(key, PersistentDataType.INTEGER, integer);
            case Double v -> container.set(key, PersistentDataType.DOUBLE, v);
            case Float v -> container.set(key, PersistentDataType.FLOAT, v);
            case Boolean b -> container.set(key, PersistentDataType.BOOLEAN, b);
            default -> System.out.println("Unsupported type for key: " + keyName);
        }
    }

    // get any
    public static Object get(Player player, String keyName) {
        if (player == null) return null;

        NamespacedKey key = new NamespacedKey(plugin, keyName);
        PersistentDataContainer container = player.getPersistentDataContainer();

        if (container.has(key, PersistentDataType.STRING)) {
            return container.get(key, PersistentDataType.STRING);
        } else if (container.has(key, PersistentDataType.INTEGER)) {
            return container.get(key, PersistentDataType.INTEGER);
        } else if (container.has(key, PersistentDataType.DOUBLE)) {
            return container.get(key, PersistentDataType.DOUBLE);
        } else if (container.has(key, PersistentDataType.FLOAT)) {
            return container.get(key, PersistentDataType.FLOAT);
        } else if (container.has(key, PersistentDataType.BOOLEAN)) {
            return container.get(key, PersistentDataType.BOOLEAN);
        }

        return null; // 型が一致するものがない or 保存されていない
    }


    /* ========================================================================================================= */

    // Persistent 型推量
    private static PersistentDataType<?, ?> getPersistentDataType(Object value) {
        if (value instanceof String) return PersistentDataType.STRING;
        if (value instanceof Integer) return PersistentDataType.INTEGER;
        if (value instanceof Double) return PersistentDataType.DOUBLE;
        if (value instanceof Float) return PersistentDataType.FLOAT;
        if (value instanceof Boolean) return PersistentDataType.BOOLEAN;
        return null; // 未対応型
    }
}

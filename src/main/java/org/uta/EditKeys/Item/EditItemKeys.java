package org.uta.EditKeys.Item;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;


public class EditItemKeys {

    private static Plugin plugin;

    public static void initialize(Plugin pluginInstance) {
        plugin = pluginInstance;
    }

    /* =================================== Setter =================================== */

    public static ItemStack setString(ItemStack item, String keyName, String value) {
        return setTyped(item, keyName, PersistentDataType.STRING, value);
    }

    public static ItemStack setInteger(ItemStack item, String keyName, Integer value) {
        return setTyped(item, keyName, PersistentDataType.INTEGER, value);
    }

    public static ItemStack setDouble(ItemStack item, String keyName, Double value) {
        return setTyped(item, keyName, PersistentDataType.DOUBLE, value);
    }

    public static ItemStack setFloat(ItemStack item, String keyName, Float value) {
        return setTyped(item, keyName, PersistentDataType.FLOAT, value);
    }

    public static ItemStack setBoolean(ItemStack item, String keyName, Boolean value) {
        return setTyped(item, keyName, PersistentDataType.BOOLEAN, value);
    }

    private static <T, Z> ItemStack setTyped(ItemStack item, String keyName, PersistentDataType<T, Z> type, Z value) {
        NamespacedKey key = new NamespacedKey(plugin, keyName);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;
        meta.getPersistentDataContainer().set(key, type, value);
        item.setItemMeta(meta);
        return item;
    }

    /* =================================== Getter =================================== */

    public static String getString(ItemStack item, String keyName) {
        return getTyped(item, keyName, PersistentDataType.STRING, "null");
    }

    public static Integer getInteger(ItemStack item, String keyName) {
        return getTyped(item, keyName, PersistentDataType.INTEGER, 0);
    }

    public static Double getDouble(ItemStack item, String keyName) {
        return getTyped(item, keyName, PersistentDataType.DOUBLE, 0d);
    }

    public static Float getFloat(ItemStack item, String keyName) {
        return getTyped(item, keyName, PersistentDataType.FLOAT, 0f);
    }

    public static Boolean getBoolean(ItemStack item, String keyName) {
        return getTyped(item, keyName, PersistentDataType.BOOLEAN, false);
    }

    private static <T, Z> Z getTyped(ItemStack item, String keyName, PersistentDataType<T, Z> type, Z def) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return def;
        PersistentDataContainer container = meta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, keyName);
        return container.has(key, type) ? container.get(key, type) : def;
    }

    /* =================================== Utility =================================== */

    public static boolean has(ItemStack item, String keyName) {
        ItemMeta meta = item.getItemMeta();
        return meta != null && meta.getPersistentDataContainer().getKeys().stream()
                .anyMatch(key -> key.getKey().equals(keyName));
    }

    public static void remove(ItemStack item, String keyName) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        meta.getPersistentDataContainer().remove(new NamespacedKey(plugin, keyName));
        item.setItemMeta(meta);
    }

    public static void set(ItemStack item, String keyName, Object value) {
        if (item == null || value == null) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        PersistentDataType<?, ?> type = getPersistentDataType(value);
        if (type == null) return;

        PersistentDataContainer container = meta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, keyName);

        if (type == PersistentDataType.STRING) {
            container.set(key, PersistentDataType.STRING, (String) value);
        } else if (type == PersistentDataType.INTEGER) {
            container.set(key, PersistentDataType.INTEGER, (Integer) value);
        } else if (type == PersistentDataType.DOUBLE) {
            container.set(key, PersistentDataType.DOUBLE, (Double) value);
        } else if (type == PersistentDataType.FLOAT) {
            container.set(key, PersistentDataType.FLOAT, (Float) value);
        } else if (type == PersistentDataType.BOOLEAN) {
            container.set(key, PersistentDataType.BOOLEAN, (Boolean) value);
        }

        item.setItemMeta(meta);
    }

    public static Object get(ItemStack item, String keyName) {
        if (item == null || !item.hasItemMeta()) return null;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;

        PersistentDataContainer container = meta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, keyName);

        if (container.has(key, PersistentDataType.STRING)) return container.get(key, PersistentDataType.STRING);
        if (container.has(key, PersistentDataType.INTEGER)) return container.get(key, PersistentDataType.INTEGER);
        if (container.has(key, PersistentDataType.DOUBLE)) return container.get(key, PersistentDataType.DOUBLE);
        if (container.has(key, PersistentDataType.FLOAT)) return container.get(key, PersistentDataType.FLOAT);
        if (container.has(key, PersistentDataType.BOOLEAN)) return container.get(key, PersistentDataType.BOOLEAN);

        return null;
    }

    private static PersistentDataType<?, ?> getPersistentDataType(Object value) {
        if (value instanceof String) return PersistentDataType.STRING;
        if (value instanceof Integer) return PersistentDataType.INTEGER;
        if (value instanceof Double) return PersistentDataType.DOUBLE;
        if (value instanceof Float) return PersistentDataType.FLOAT;
        if (value instanceof Boolean) return PersistentDataType.BOOLEAN;
        return null;
    }
}

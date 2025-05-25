package org.uta.EditKeys.Item;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

public class EditItemKeys {
    private static Plugin plugin;

    public static void initialize(Plugin pluginInstance) {
        plugin = pluginInstance;
    }

    /* ========================================================================================================= */

    // アイテムに任意の数値(i)を代入するとき
    public static ItemStack setString(ItemStack item, String keyName, String str) {
        NamespacedKey key = new NamespacedKey(plugin, keyName);
        ItemMeta meta = item.getItemMeta();
        Objects.requireNonNull(meta).getPersistentDataContainer().set(key, PersistentDataType.STRING, str);
        item.setItemMeta(meta);
        return item;
    }

    // アイテムに任意の数値(i)を代入するとき
    public static ItemStack setInteger(ItemStack item, String keyName, Integer num) {
        NamespacedKey key = new NamespacedKey(plugin, keyName);
        ItemMeta meta = item.getItemMeta();
        Objects.requireNonNull(meta).getPersistentDataContainer().set(key, PersistentDataType.INTEGER, num);
        item.setItemMeta(meta);
        return item;
    }

    // アイテムに任意の数値(d)を代入するとき
    public static ItemStack setDouble(ItemStack item, String keyName, Double num) {
        NamespacedKey key = new NamespacedKey(plugin, keyName);
        ItemMeta meta = item.getItemMeta();
        Objects.requireNonNull(meta).getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, num);
        item.setItemMeta(meta);
        return item;
    }

    // アイテムに任意の数値(f)を代入するとき
    public static ItemStack setFloat(ItemStack item, String keyName, Float num) {
        NamespacedKey key = new NamespacedKey(plugin, keyName);
        ItemMeta meta = item.getItemMeta();
        Objects.requireNonNull(meta).getPersistentDataContainer().set(key, PersistentDataType.FLOAT, num);
        item.setItemMeta(meta);
        return item;
    }

    // アイテムに任意の数値(bool)を代入するとき
    public static ItemStack setBoolean(ItemStack item, String keyName, Boolean bool) {
        NamespacedKey key = new NamespacedKey(plugin, keyName);
        ItemMeta meta = item.getItemMeta();
        Objects.requireNonNull(meta).getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, bool);
        item.setItemMeta(meta);
        return item;
    }

    /* ========================================================================================================= */

    // 単純なアイテム識別 HAS
    public static Boolean has(ItemStack item, String keyName) {
        ItemMeta meta = item.getItemMeta();
        return Objects.requireNonNull(meta).getPersistentDataContainer().getKeys().stream()
                .anyMatch(key -> key.getKey().equals(keyName));
    }

    /* ========================================================================================================= */

    // 取得 (str) GET
    public static String getString(ItemStack item, String keyName) {
        ItemMeta meta = item.getItemMeta();
        NamespacedKey key = new NamespacedKey(plugin, keyName);
        PersistentDataContainer container = Objects.requireNonNull(meta).getPersistentDataContainer();
        if (!container.has(key, PersistentDataType.STRING)) return "null";
        String value = container.get(key, PersistentDataType.STRING);
        return EditItemKeys.has(item, keyName) ? value : "null";
    }

    // 取得 (i) GET
    public static Integer getInteger(ItemStack item, String keyName) {
        ItemMeta meta = item.getItemMeta();
        NamespacedKey key = new NamespacedKey(plugin, keyName);
        PersistentDataContainer container = Objects.requireNonNull(meta).getPersistentDataContainer();
        if (!container.has(key, PersistentDataType.INTEGER)) return 0;
        Integer value = container.get(key, PersistentDataType.INTEGER);
        return EditItemKeys.has(item, keyName) ? value : Integer.valueOf(0);
    }

    // 取得 (d) GET
    public static Double getDouble(ItemStack item, String keyName) {
        ItemMeta meta = item.getItemMeta();
        NamespacedKey key = new NamespacedKey(plugin, keyName);
        PersistentDataContainer container = Objects.requireNonNull(meta).getPersistentDataContainer();
        if (!container.has(key, PersistentDataType.DOUBLE)) return 0d;
        Double value = container.get(key, PersistentDataType.DOUBLE);
        return EditItemKeys.has(item, keyName) ? value : Double.valueOf(0);
    }

    // 取得 (f) GET
    public static Float getFloat(ItemStack item, String keyName) {
        ItemMeta meta = item.getItemMeta();
        NamespacedKey key = new NamespacedKey(plugin, keyName);
        PersistentDataContainer container = Objects.requireNonNull(meta).getPersistentDataContainer();
        if (!container.has(key, PersistentDataType.FLOAT)) return 0f;
        Float value = container.get(key, PersistentDataType.FLOAT);
        return EditItemKeys.has(item, keyName) ? value : Float.valueOf(0);
    }

    // 取得 (bool) GET
    public static Boolean getBoolean(ItemStack item, String keyName) {
        ItemMeta meta = item.getItemMeta();
        NamespacedKey key = new NamespacedKey(plugin, keyName);PersistentDataContainer container = Objects.requireNonNull(meta).getPersistentDataContainer();
        if (!container.has(key, PersistentDataType.BOOLEAN)) return false;
        Boolean value = container.get(key, PersistentDataType.BOOLEAN);
        return EditItemKeys.has(item, keyName) ? value : Boolean.valueOf(false);
    }

    /* ========================================================================================================= */

    // 削除
    public static void remove(ItemStack item, String keyName) {
        ItemMeta meta = item.getItemMeta();
        NamespacedKey key = new NamespacedKey(plugin, keyName);
        Objects.requireNonNull(meta).getPersistentDataContainer().remove(key);
    }

    /* ========================================================================================================= */

    // set any
    public static void set(ItemStack item, String keyName, Object value) {
        NamespacedKey key = new NamespacedKey(plugin, keyName);
        ItemMeta meta = item.getItemMeta();
        if (meta == null || value == null) return;

        PersistentDataContainer container = meta.getPersistentDataContainer();
        PersistentDataType<?, ?> type = getPersistentDataType(value);
        if (type == null) return;

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

    // get any
    public static Object get(ItemStack item, String keyName) {
        if (item == null || !item.hasItemMeta()) return null;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;

        PersistentDataContainer container = meta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, keyName);

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

        return null;
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
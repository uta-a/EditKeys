package org.uta.EditKeys.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.uta.EditKeys.Item.EditItemKeys;
import org.uta.EditKeys.Player.EditPlayerKeys;


public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return false;
        if (args.length < 4) {
            player.sendMessage(ChatColor.RED + "引数が足りません。使用例: /editkeys [item/player] [set/get/remove] [type] [key] [value]");
            return false;
        }

        String target = args[0].toLowerCase();
        String action = args[1].toLowerCase();
        String type = args[2].toLowerCase();
        String key = args[3];
        String value = args.length >= 5 ? args[4] : null;

        if (action.equals("set") && value == null) {
            player.sendMessage(ChatColor.RED + "set を使う場合は値も指定してください。");
            return false;
        }

        switch (target) {
            case "item" -> handleItemAction(player, action, type, key, value);
            default -> {
                Player targetPlayer = Bukkit.getPlayer(target);
                if (targetPlayer == null) {
                    player.sendMessage(prefix("EditKeys") + ChatColor.RED + "対象のプレイヤーが見つかりません。");
                    return false;
                }
                handlePlayerAction(targetPlayer, player, action, type, key, value);
            }
        }

        return true;
    }

    // item 処理
    private void handleItemAction(Player player, String action, String type, String key, String value) {
        ItemStack item = player.getInventory().getItemInMainHand();

        switch (action) {
            case "set" -> {
                Object parsed = parseValue(type, value);
                if (parsed == null) {
                    player.sendMessage("§c値が型に変換できません: " + value);
                    return;
                }
                switch (type) {
                    case "string" -> EditItemKeys.setString(item, key, (String) parsed);
                    case "integer" -> EditItemKeys.setInteger(item, key, (Integer) parsed);
                    case "double" -> EditItemKeys.setDouble(item, key, (Double) parsed);
                    case "float" -> EditItemKeys.setFloat(item, key, (Float) parsed);
                    case "boolean" -> EditItemKeys.setBoolean(item, key, (Boolean) parsed);
                    default -> {
                        player.sendMessage(ChatColor.RED + "不明な型: " + type);
                        return;
                    }
                }
                player.sendMessage(prefix("EditKeys") + surround(type) + surround(key) + surround(value));
            }
            case "get" -> {
                Object result = switch (type) {
                    case "string" -> EditItemKeys.getString(item, key);
                    case "integer" -> EditItemKeys.getInteger(item, key);
                    case "double" -> EditItemKeys.getDouble(item, key);
                    case "float" -> EditItemKeys.getFloat(item, key);
                    case "boolean" -> EditItemKeys.getBoolean(item, key);
                    default -> null;
                };
                if (result == null) {
                    player.sendMessage("§c指定されたキーまたは型が見つかりません。");
                    return;
                }
                player.sendMessage(prefix("EditKeys") + surround(type) + surround(key) + surround(result.toString()));
            }
            case "remove" -> {
                EditItemKeys.remove(item, key);
                player.sendMessage(prefix("EditKeys") + surround(key) + ChatColor.WHITE + "removed");
            }
            default -> player.sendMessage(ChatColor.RED + "アクションは 'set', 'get', 'remove' のいずれかを指定してください。");
        }
    }

    // player 処理
    private void handlePlayerAction(Player target, Player executor, String action, String type, String key, String value) {
        switch (action) {
            case "set" -> {
                Object parsed = parseValue(type, value);
                if (parsed == null) {
                    executor.sendMessage("§c値が型に変換できません: " + value);
                    return;
                }
                switch (type) {
                    case "string" -> EditPlayerKeys.setString(target, key, (String) parsed);
                    case "integer" -> EditPlayerKeys.setInteger(target, key, (Integer) parsed);
                    case "double" -> EditPlayerKeys.setDouble(target, key, (Double) parsed);
                    case "float" -> EditPlayerKeys.setFloat(target, key, (Float) parsed);
                    case "boolean" -> EditPlayerKeys.setBoolean(target, key, (Boolean) parsed);
                    default -> {
                        executor.sendMessage(ChatColor.RED + "不明な型: " + type);
                        return;
                    }
                }
                executor.sendMessage(prefix("EditKeys") + surround(type) + surround(key) + surround(value));
            }
            case "get" -> {
                Object result = switch (type) {
                    case "string" -> EditPlayerKeys.getString(target, key);
                    case "integer" -> EditPlayerKeys.getInteger(target, key);
                    case "double" -> EditPlayerKeys.getDouble(target, key);
                    case "float" -> EditPlayerKeys.getFloat(target, key);
                    case "boolean" -> EditPlayerKeys.getBoolean(target, key);
                    default -> null;
                };
                if (result == null) {
                    executor.sendMessage("§c指定されたキーまたは型が見つかりません。");
                    return;
                }
                executor.sendMessage(prefix("EditKeys") + surround(type) + surround(key) + surround(result.toString()));
            }
            case "remove" -> {
                EditPlayerKeys.remove(target, key);
                executor.sendMessage(prefix("EditKeys") + surround(key) + ChatColor.WHITE + "removed");
            }
            default -> executor.sendMessage(ChatColor.RED + "アクションは 'set', 'get', 'remove' のいずれかを指定してください。");
        }
    }

    private @NotNull String prefix(String key) {
        return ChatColor.GOLD + "[" + ChatColor.WHITE + key + ChatColor.GOLD + "]" + ChatColor.GRAY + ": ";
    }

    private @NotNull String surround(String key) {
        return ChatColor.GRAY + "[" + ChatColor.WHITE + key + ChatColor.GRAY + "]" + ChatColor.WHITE + " ";
    }

    // 型パース共通処理
    private Object parseValue(String type, String value) {
        try {
            return switch (type) {
                case "string" -> value;
                case "integer" -> Integer.parseInt(value);
                case "double" -> Double.parseDouble(value);
                case "float" -> Float.parseFloat(value);
                case "boolean" -> Boolean.parseBoolean(value);
                default -> null;
            };
        } catch (Exception e) {
            return null;
        }
    }
}

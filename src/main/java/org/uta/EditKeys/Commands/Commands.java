package org.uta.EditKeys.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.uta.EditKeys.Item.EditItemKeys;
import org.uta.EditKeys.Player.EditPlayerKeys;

public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        /*
            /editkeys [item/player] [set/get/remove] [String/Integer/Double/Float] [key] [value]
        */


        if (!(sender instanceof Player)) return false;
        if (args.length < 4) return false;

        String target = args[0].toLowerCase();  // [item/player]
        String action = args[1].toLowerCase();  // [set/get/remove]
        String type = args[2].toLowerCase();    // [string/integer/double/float/boolean]
        String key = args[3].toLowerCase();     // [key]
        String value = args.length >= 5 ? args[4].toLowerCase() : null; // [value]



        if (target.equals("item")) {
            handleItemAction((Player) sender, action, type, key, value);
        } else {
            Player player = Bukkit.getPlayer(target);
            if (player == null) sender.sendMessage(prefix("EditKeys") + ChatColor.RED + "対象のプレイヤーはいません");
            handlePlayerAction(player, action, type, key, value);
        }

        return true;
    }

    // item
    private void handleItemAction(Player player, String action, String type, String key, String value) {

        // メインハンドのアイテム
        ItemStack item = player.getInventory().getItemInMainHand();

        switch (action) {
            case "set" -> {
                try {
                    switch (type) {
                        case "string" -> EditItemKeys.setString(item, key, value);
                        case "integer" -> EditItemKeys.setInteger(item, key, Integer.valueOf(value));
                        case "double" -> EditItemKeys.setDouble(item, key, Double.valueOf(value));
                        case "float" -> EditItemKeys.setFloat(item, key, Float.valueOf(value));
                        case "boolean" -> EditItemKeys.setBoolean(item, key, Boolean.valueOf(value));
                    }
                } catch (NullPointerException e) {
                    player.sendMessage("§c値が型に変換できません: " + value);
                }
                player.sendMessage(prefix("EditKeys") + surround(ChatColor.GRAY, type) + surround(ChatColor.GRAY, key) + surround(ChatColor.GRAY, value));
            }
            case "get" -> {
                Object res = null;
                try {
                    switch (type) {
                        case "string" -> res = EditItemKeys.getString(item, key);
                        case "integer" -> res = EditItemKeys.getInteger(item, key);
                        case "double" -> res = EditItemKeys.getDouble(item, key);
                        case "float" -> res = EditItemKeys.getFloat(item, key);
                        case "boolean" -> res = EditItemKeys.getBoolean(item, key);
                    }
                } catch (NullPointerException e) {
                    player.sendMessage("§c値が型に変換できません: " + value);
                }

                player.sendMessage(prefix("EditKeys") + surround(ChatColor.GRAY, type) + surround(ChatColor.GRAY, key) + surround(ChatColor.GRAY, res.toString()));
            }
            case "remove" -> {
                EditItemKeys.remove(item, key);
                player.sendMessage(prefix("EditKeys") + surround(ChatColor.GRAY, key) + ChatColor.WHITE+"removed");
            }
            default -> {
                player.sendMessage(ChatColor.RED + "2番目の引数は 'set', 'get', 'remove' のいずれかである必要があります。");
            }
        }
    }

    // player
    private void handlePlayerAction(Player player, String action, String type, String key, String value) {
        switch (action) {
            case "set" -> {
                try {
                    switch (type) {
                        case "string" -> EditPlayerKeys.setString(player, key, value);
                        case "integer" -> EditPlayerKeys.setInteger(player, key, Integer.valueOf(value));
                        case "double" -> EditPlayerKeys.setDouble(player, key, Double.valueOf(value));
                        case "float" -> EditPlayerKeys.setFloat(player, key, Float.valueOf(value));
                        case "boolean" -> EditPlayerKeys.setBoolean(player, key, Boolean.valueOf(value));
                    }
                } catch (NullPointerException e) {
                    player.sendMessage("§c値が型に変換できません: " + value);
                }
                player.sendMessage(prefix("EditKeys") + surround(ChatColor.GRAY, type) + surround(ChatColor.GRAY, key) + surround(ChatColor.GRAY, value));
            }
            case "get" -> {
                Object res = null;
                try {
                    switch (type) {
                        case "string" -> res = EditPlayerKeys.getString(player, key);
                        case "integer" -> res = EditPlayerKeys.getInteger(player, key);
                        case "double" -> res = EditPlayerKeys.getDouble(player, key);
                        case "float" -> res = EditPlayerKeys.getFloat(player, key);
                        case "boolean" -> res = EditPlayerKeys.getBoolean(player, key);
                    }
                } catch (NullPointerException e) {
                    player.sendMessage("§c値が型に変換できません: " + value);
                }

                player.sendMessage(prefix("EditKeys") + surround(ChatColor.GRAY, type) + surround(ChatColor.GRAY, key) + surround(ChatColor.GRAY, res.toString()));
            }
            case "remove" -> {
                EditPlayerKeys.remove(player, key);
                player.sendMessage(prefix("EditKeys" + surround(ChatColor.GRAY, key)) + ChatColor.WHITE+"removed");
            }
            default -> {
                player.sendMessage(ChatColor.RED + "2番目の引数は 'set', 'get', 'remove' のいずれかである必要があります。");
            }
        }
    }

    private String prefix(String key) {
        return ChatColor.GOLD+"[" + ChatColor.WHITE+key + ChatColor.GOLD+"]" + ChatColor.GRAY+": ";
    }

    private String surround(ChatColor color, String key) {
        return color+"[" + ChatColor.WHITE+key + color+"]" + ChatColor.WHITE+" ";
    }

}

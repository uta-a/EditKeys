package org.uta.EditKeys.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TabComp implements TabCompleter {

    private static final List<String> ACTIONS = List.of("set", "get", "remove");
    private static final List<String> TYPES = List.of("string", "integer", "double", "float", "boolean");

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {

        if (args.length == 1) {
            List<String> options = new ArrayList<>();
            options.add("item");
            options.addAll(Bukkit.getOnlinePlayers().stream().map(Player::getName).toList());
            return partialMatch(args[0], options);
        }

        if (args.length == 2) {
            return partialMatch(args[1], ACTIONS);
        }

        if (args.length == 3) {
            return partialMatch(args[2], TYPES);
        }

        if (args.length == 4) {
            // key名の提案。ここではプレースホルダとして空リストを返す（必要に応じて記憶機能などに接続）
            return Collections.emptyList();
        }

        if (args.length == 5) {
            // value の提案（型に応じたサンプルを返しても良い）
            return switch (args[2].toLowerCase()) {
                case "boolean" -> List.of("true", "false");
                default -> Collections.emptyList();
            };
        }

        return Collections.emptyList();
    }

    // 入力補完用ユーティリティ（前方一致）
    private List<String> partialMatch(String input, List<String> options) {
        return options.stream()
                .filter(opt -> opt.toLowerCase().startsWith(input.toLowerCase()))
                .collect(Collectors.toList());
    }
}

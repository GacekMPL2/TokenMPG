package me.gacekmpl2.essentials;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TabCompleter implements org.bukkit.command.TabCompleter {
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> tokenTab = new ArrayList<>();
        if (args.length == 1) {
            tokenTab.add("give");
            tokenTab.add("take");
            tokenTab.add("set");
            tokenTab.add("check");
        } else if (args.length == 2) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                String playerName = onlinePlayer.getName();
                tokenTab.add(playerName);
            }
        }
        return tokenTab;
    }
}
package me.gacekmpl2.commands;

import me.gacekmpl2.manger.TokenManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

import static me.gacekmpl2.manger.TokenManager.*;


public class TokenCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Ta komenda może być wykonana tylko przez gracza.");
            return true;
        }
        Player player = (Player) sender;
        switch (args.length) {
            case 0:
                String playerName = player.getName();
                try {
                    addPlayer(playerName);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                int tokens = getTokens(playerName);
                player.sendMessage("Posiadasz " + tokens + " tokenów.");
                break;
            case 2:
                if (args[0].equalsIgnoreCase("dodaj")) {
                    try {
                        playerName = args[1];
                        addTokens(playerName, 1);
                        player.sendMessage("Dodano 1 token dla gracza " + playerName + ".");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else if (args[0].equalsIgnoreCase("usun")) {
                    try {
                        playerName = args[1];
                        removeTokens(playerName, 1);
                        player.sendMessage("Usunięto 1 token dla gracza " + playerName + ".");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    player.sendMessage("Nieznana komenda. Użyj /token [dodaj|usun] <nick>");
                }
                break;
            case 3:
                if (args[0].equalsIgnoreCase("dodaj")) {
                    try {
                        playerName = args[1];
                        int amount = Integer.parseInt(args[2]);
                        addTokens(playerName, amount);
                        player.sendMessage("Dodano " + amount + " tokenów dla gracza " + playerName + ".");
                    } catch (NumberFormatException e) {
                        player.sendMessage("Nieprawidłowa ilość tokenów. Użyj /token dodaj <nick> <ilość>");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else if (args[0].equalsIgnoreCase("usun")) {
                    try {
                        playerName = args[1];
                        int amount = Integer.parseInt(args[2]);
                        removeTokens(playerName, amount);
                        player.sendMessage("Usunięto " + amount + " tokenów dla gracza " + playerName + ".");
                    } catch (NumberFormatException e) {
                        player.sendMessage("Nieprawidłowa ilość tokenów. Użyj /token usun <nick> <ilość>");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    player.sendMessage("Nieznana komenda. Użyj /token [dodaj|usun] <nick> <ilość>");
                }
                break;
            case 4:
                if (args[0].equalsIgnoreCase("ustaw")) {
                    try {
                        playerName = args[1];
                        int amount = Integer.parseInt(args[2]);
                        setTokens(playerName, amount);
                        player.sendMessage("Ustawiono " + amount + " tokenów dla gracza " + playerName + ".");
                    } catch (NumberFormatException e) {
                        player.sendMessage("Nieprawidłowa ilość tokenów. Użyj /token ustaw <nick> <ilość>");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    player.sendMessage("Nieznana komenda. Użyj /token ustaw <nick> <ilość>");
                }
                break;
            default:
                player.sendMessage("Nieprawidłowe użycie komendy. Użyj /token [dodaj|usun|ustaw] [ilość]");
                break;
        }
        return true;
    }
}
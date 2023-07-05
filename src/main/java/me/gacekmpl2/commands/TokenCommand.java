package me.gacekmpl2.commands;

import me.gacekmpl2.essentials.ChatUtils;
import me.gacekmpl2.manger.TokenManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;


public class TokenCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player) && !(sender instanceof ConsoleCommandSender)) {
            sender.sendMessage("Ta komenda może być wykonana tylko przez gracza lub konsolę.");
            return true;
        }

        if (sender instanceof ConsoleCommandSender) {
            if (args.length < 2) {
                ChatUtils.sendMessage(sender, "&cNieprawidłowe użycie komendy. &2Użyj /token give/take/set <nick> <ilość>");
                return true;
            }

            String playerName = args[1];
            Player targetPlayer = Bukkit.getPlayer(playerName);
            if (targetPlayer == null) {
                ChatUtils.sendMessage(sender, "Nieznaleziono gracza o nicku " + playerName + ".");
                return true;
            }

            if (args[0].equalsIgnoreCase("check")) {
                int tokens = TokenManager.getTokens(playerName);
                ChatUtils.sendMessage(sender, "Gracz " + playerName + " posiada " + tokens + " tokenów.");
                return true;
            }

            int amount;
            try {
                amount = Integer.parseInt(args[2]);
                if (amount < 0) {
                    ChatUtils.sendMessage(sender, "&cIlość tokenów musi być większa lub równa 0.");
                    return true;
                }
            } catch (NumberFormatException e) {
                ChatUtils.sendMessage(sender, "&cNieprawidłowa ilość tokenów. &2Użyj /token give/take/set <nick> <ilość>");
                return true;
            }

            int currentTokens = TokenManager.getTokens(targetPlayer.getName());
            if (args[0].equalsIgnoreCase("give")) {
                try {
                    TokenManager.addTokens(targetPlayer.getName(), amount);
                    ChatUtils.sendMessage(sender, "Dodano " + amount + " tokenów dla gracza " + targetPlayer.getName() + ".");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (args[0].equalsIgnoreCase("take")) {
                if (amount > currentTokens) {
                    ChatUtils.sendMessage(sender, "&cGracz " + targetPlayer.getName() + " nie posiada tyle tokenów. Obecne saldo: " + currentTokens);
                    return true;
                }

                try {
                    TokenManager.removeTokens(targetPlayer.getName(), amount);
                    ChatUtils.sendMessage(sender, "Usunięto " + amount + " tokenów dla gracza " + targetPlayer.getName() + ".");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (args[0].equalsIgnoreCase("set")) {
                try {
                    TokenManager.setTokens(targetPlayer.getName(), amount);
                    ChatUtils.sendMessage(sender, "Ustawiono " + amount + " tokenów dla gracza " + targetPlayer.getName() + ".");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                ChatUtils.sendMessage(sender, "&cNieznana komenda. &2Użyj /token give/take/set <nick> <ilość>");
            }
        } else if (sender instanceof Player) {
            Player player = (Player) sender;
            String playerName = player.getName();

            switch (args.length) {
                case 0:
                    try {
                        TokenManager.addPlayer(playerName);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    int tokens = TokenManager.getTokens(playerName);
                    ChatUtils.sendMessage(player, "&f&lPosiadasz " + tokens + " tokenów.");
                    break;
                case 2:
                    if (args[0].equalsIgnoreCase("give")) {
                        try {
                            playerName = args[1];
                            TokenManager.addTokens(playerName, 1);
                            ChatUtils.sendMessage(player, "Dodano 1 token dla gracza " + playerName + ".");
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else if (args[0].equalsIgnoreCase("take")) {
                        try {
                            playerName = args[1];
                            int amount = Integer.parseInt(args[2]);
                            if (amount < 0) {
                                ChatUtils.sendMessage(player, "&c&LIlość tokenów musi być większa lub równa 0.");
                                return true;
                            }

                            int currentTokens = TokenManager.getTokens(playerName);
                            if (amount > currentTokens) {
                                ChatUtils.sendMessage(player, "&cGracz " + playerName + " nie posiada tyle tokenów. Obecne saldo: " + currentTokens);
                                return true;
                            }

                            TokenManager.removeTokens(playerName, amount);
                            ChatUtils.sendMessage(player, "Usunięto " + amount + " tokenów dla gracza " + playerName + ".");
                        } catch (NumberFormatException e) {
                            ChatUtils.sendMessage(player, "&cNieprawidłowa ilość tokenów. &2Użyj /token set <nick> <ilość>");
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else if (args[0].equalsIgnoreCase("set")) {
                        try {
                            playerName = args[1];
                            int amount = Integer.parseInt(args[2]);
                            if (amount < 0) {
                                ChatUtils.sendMessage(player, "&c&LIlość tokenów musi być większa lub równa 0.");
                                return true;
                            }
                            TokenManager.setTokens(playerName, amount);
                            ChatUtils.sendMessage(player, "Ustawiono " + amount + " tokenów dla gracza " + playerName + ".");
                        } catch (NumberFormatException e) {
                            ChatUtils.sendMessage(player, "&cNieprawidłowa ilość tokenów. &2Użyj /token set <nick> <ilość>");
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        ChatUtils.sendMessage(player, "&cNieznana komenda. &2Użyj /token give/take/set <nick>");
                    }
                    break;
                case 3:
                    if (args[0].equalsIgnoreCase("give")) {
                        try {
                            playerName = args[1];
                            int amount = Integer.parseInt(args[2]);
                            if (amount < 0) {
                                ChatUtils.sendMessage(player, "&c&LIlość tokenów musi być większa lub równa 0.");
                                return true;
                            }
                            TokenManager.addTokens(playerName, amount);
                            ChatUtils.sendMessage(player, "Dodano " + amount + " tokenów dla gracza " + playerName + ".");
                        } catch (NumberFormatException e) {
                            ChatUtils.sendMessage(player, "&cNieprawidłowa ilość tokenów. &2Użyj /token give <nick> <ilość>");
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else if (args[0].equalsIgnoreCase("take")) {
                        try {
                            playerName = args[1];
                            int amount = Integer.parseInt(args[2]);
                            if (amount < 0) {
                                ChatUtils.sendMessage(player, "&cIlość tokenów musi być większa lub równa 0.");
                                return true;
                            }

                            int currentTokens = TokenManager.getTokens(playerName);
                            if (amount > currentTokens) {
                                ChatUtils.sendMessage(player, "&cGracz " + playerName + " nie posiada tyle tokenów. Obecne saldo: " + currentTokens);
                                return true;
                            }

                            TokenManager.removeTokens(playerName, amount);
                            ChatUtils.sendMessage(player, "Usunięto " + amount + " tokenów dla gracza " + playerName + ".");
                        } catch (NumberFormatException e) {
                            ChatUtils.sendMessage(player, "&cNieprawidłowa ilość tokenów. &2Użyj /token take <nick> <ilość>");
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else if (args[0].equalsIgnoreCase("set")) {
                        try {
                            playerName = args[1];
                            int amount = Integer.parseInt(args[2]);
                            if (amount < 0) {
                                ChatUtils.sendMessage(player, "&cIlość tokenów musi być większa lub równa 0.");
                                return true;
                            }
                            TokenManager.setTokens(playerName, amount);
                            ChatUtils.sendMessage(player, "Ustawiono " + amount + " tokenów dla gracza " + playerName + ".");
                        } catch (NumberFormatException e) {
                            ChatUtils.sendMessage(player, "&cNieprawidłowa ilość tokenów. &2Użyj /token set <nick> <ilość>");
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        ChatUtils.sendMessage(player, "&cNieznana komenda. &2Użyj /token give/take/set <nick> <ilość>");
                    }
                    break;
            }
        }
        return true;
    }
}
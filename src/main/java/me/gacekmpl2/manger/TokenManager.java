package me.gacekmpl2.manger;

import me.gacekmpl2.TokenMPG;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TokenManager {

    private static Connection connection;

    public static void setConnection(Connection connection) {
        TokenManager.connection = connection;
    }

    public static void addPlayer(String playerName) throws SQLException {
        if (!isPlayerInDatabase(playerName)) {
            DatabaseManager databaseManager = TokenMPG.getInstance().getDatabaseManager();
            String sql = "INSERT INTO token (player_name, tokens) VALUES (?, ?)";
            databaseManager.update(sql, playerName, 0);
        }
    }

    public static void addTokens(String playerName, int amount) throws SQLException {
        DatabaseManager databaseManager = TokenMPG.getInstance().getDatabaseManager();
        String sql = "UPDATE token SET tokens = tokens + ? WHERE player_name = ?";
        databaseManager.update(sql, amount, playerName);
    }

    public static void removeTokens(String playerName, int amount) throws SQLException {
        DatabaseManager databaseManager = TokenMPG.getInstance().getDatabaseManager();
        String sql = "UPDATE token SET tokens = tokens - ? WHERE player_name = ?";
        databaseManager.update(sql, amount, playerName);
    }

    public static void setTokens(String playerName, int amount) throws SQLException {
        DatabaseManager databaseManager = TokenMPG.getInstance().getDatabaseManager();
        String sql = "UPDATE token SET tokens = ? WHERE player_name = ?";
        databaseManager.update(sql, amount, playerName);
    }

    public static int getTokens(String playerName) {
        int tokens = 0;
        try {
            DatabaseManager databaseManager = TokenMPG.getInstance().getDatabaseManager();
            String sql = "SELECT tokens FROM token WHERE player_name = ?";
            try (ResultSet results = databaseManager.query(sql, playerName)) {
                if (results.next()) {
                    tokens = results.getInt("tokens");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tokens;
    }

    private static boolean isPlayerInDatabase(String playerName) throws SQLException {
        DatabaseManager databaseManager = TokenMPG.getInstance().getDatabaseManager();
        String sql = "SELECT player_name FROM token WHERE player_name = ?";
        try (ResultSet result = databaseManager.query(sql, playerName)) {
            return result.next();
        }
    }
}
package me.gacekmpl2;

import me.gacekmpl2.commands.TokenCommand;
import me.gacekmpl2.manger.DatabaseManager;
import me.gacekmpl2.manger.TokenManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;


public class TokenMPG extends JavaPlugin {

    private static TokenMPG instance;
    private static DatabaseManager databaseManager;

    @Override
    public void onEnable() {
        instance = this;
        databaseManager = new DatabaseManager(
                "localhost",
                "3306",
                "luckperms",
                "root",
                ""
        );

        getCommand("token").setExecutor(new TokenCommand());
        getLogger().info("Plugin enabled.");

        prepareTables();
    }

    @Override
    public void onDisable() {
        if (databaseManager != null) {
        }
    }

    private void prepareTables() {
        String query = "CREATE TABLE IF NOT EXISTS token (" +
                "player_name VARCHAR(32) NOT NULL PRIMARY KEY," +
                "tokens INT NOT NULL)";
        databaseManager.update(query);
    }

    public static TokenMPG getInstance() {
        return instance;
    }

    public static DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

}
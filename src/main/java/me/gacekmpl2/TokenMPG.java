package me.gacekmpl2;

import me.gacekmpl2.commands.TokenCommand;
import me.gacekmpl2.essentials.TabCompleter;
import me.gacekmpl2.manger.DatabaseManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;


public class TokenMPG extends JavaPlugin {

    private static TokenMPG instance;
    private static DatabaseManager databaseManager;

    @Override
    public void onEnable() {
        instance = this;
        getCommand("token").setExecutor(new TokenCommand());
        Objects.requireNonNull(getCommand("token")).setTabCompleter(new TabCompleter());
        getLogger().info("Plugin enabled.");
        this.databaseManager = new DatabaseManager(
                "localhost",
                "3306",
                "luckperms",
                "luckperms",
                "sperma"
        );

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
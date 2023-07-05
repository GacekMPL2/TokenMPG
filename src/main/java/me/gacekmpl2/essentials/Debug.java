package me.gacekmpl2.essentials;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

public class Debug {

    private static final ConsoleCommandSender console = Bukkit.getConsoleSender();
    private static final String prefix = "[TokenMPG] ";

    public static void log(String a) {
        console.sendMessage(prefix + a.replace("&", "§"));
    }
}

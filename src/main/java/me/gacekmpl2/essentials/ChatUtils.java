package me.gacekmpl2.essentials;

import org.bukkit.command.CommandSender;

public class ChatUtils {

    public static String fixColor(String message) {
        return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void sendMessage(CommandSender player, String message){
        player.sendMessage(fixColor("&f&lToken&5&lMPG" + " " + message));
    }
}

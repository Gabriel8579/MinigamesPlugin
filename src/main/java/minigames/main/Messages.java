package minigames.main;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Messages {


    public static void sendMessage(Player p, MessageType MessageType, String msg){

        if (MessageType == Messages.MessageType.WORLD) {
            p.sendMessage(ChatColor.DARK_BLUE+"["+ChatColor.BLUE+"Mundos"+ChatColor.DARK_BLUE+"] > "+ChatColor.GREEN+msg);
        }
        if (MessageType == Messages.MessageType.ROOM) {
            p.sendMessage(ChatColor.DARK_BLUE + "[" + ChatColor.BLUE + "Sala" + ChatColor.DARK_BLUE + "] > " + ChatColor.GREEN + msg);
        }
        if (MessageType == Messages.MessageType.ROLE) {
            p.sendMessage(ChatColor.DARK_BLUE + "[" + ChatColor.BLUE + "Cargo" + ChatColor.DARK_BLUE + "] > " + ChatColor.GREEN + msg);
        }
        if (MessageType == Messages.MessageType.TNT) {
            p.sendMessage(ChatColor.DARK_BLUE + "[" + ChatColor.BLUE + "TntWars" + ChatColor.DARK_BLUE + "] > " + ChatColor.GREEN + msg);
        }

    }


    public enum MessageType {
        WORLD, ROOM, ROLE, TNT
    }



}

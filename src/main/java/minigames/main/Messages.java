package minigames.main;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Messages {


    public static enum MessageType{
        WORLD;
    }


    public static void sendMessage(Player p, MessageType MessageType, String msg){

        if(MessageType == MessageType.WORLD){
            p.sendMessage(ChatColor.DARK_BLUE+"["+ChatColor.BLUE+"Mundos"+ChatColor.DARK_BLUE+"] > "+ChatColor.GREEN+msg);
        }

    }



}

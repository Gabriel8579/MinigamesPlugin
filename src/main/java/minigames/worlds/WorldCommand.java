package minigames.worlds;

import minigames.date.Date;
import minigames.main.Main;
import minigames.main.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class WorldCommand implements CommandExecutor {



    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(command.getName().equalsIgnoreCase("mundo")){
            Player p = ((Player)sender).getPlayer();
            if(p.isOp()){
            if(args.length == 0){
                Messages.sendMessage(p, Messages.MessageType.WORLD, "Você pode usar /mundo <nome> para editar ou criar um novo! Lista de mundos:");
                Statement st = null;
                try {
                    st = Main.c.createStatement();
                    ResultSet res = st.executeQuery("SELECT * FROM worldsData;");
                    String mundos = "";
                    while(res.next()){
                        if(res.getBoolean("Online")) {
                            mundos = mundos + ChatColor.GOLD + res.getString("Name") + ", ";
                            TextComponent t;
                            t = new TextComponent( ChatColor.GOLD + res.getString("Name") + ", ");

                        }else{
                            mundos = mundos + ChatColor.GRAY + res.getString("Name") + ", ";
                        }
                    }
                    Messages.sendMessage(p, Messages.MessageType.WORLD, mundos);
                    res.close();
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else{
                WorldGui.OpenWorldCreatorGui(p, args[0]);
            }
            }else{
                Messages.sendMessage(p, Messages.MessageType.WORLD, "Você não pode usar esse comando!");
            }


        }



        return false;


    }




}

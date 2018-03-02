package minigames.worlds;

import minigames.main.Main;
import minigames.main.Messages;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
                Statement st;
                try {
                    st = Main.c.createStatement();
                    ResultSet res = st.executeQuery("SELECT * FROM worldsData;");
                    while(res.next()){
                        if(res.getBoolean("Online")) {
                            TextComponent t = new TextComponent(ChatColor.GOLD + res.getString("Name") + ", ");
                            ClickEvent cv = new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/mundo " + res.getString("Name"));
                            HoverEvent hv = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.WHITE + "Clique aqui para editar este mundo").create());
                            t.setClickEvent(cv);
                            t.setHoverEvent(hv);
                            p.spigot().sendMessage(t);
                        }else{
                            TextComponent t = new TextComponent(ChatColor.GRAY + res.getString("Name") + ", ");
                            ClickEvent cv = new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/mundo " + res.getString("Name"));
                            HoverEvent hv = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.WHITE + "Clique aqui para editar este mundo").create());
                            t.setClickEvent(cv);
                            t.setHoverEvent(hv);
                            p.spigot().sendMessage(t);
                        }
                    }
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

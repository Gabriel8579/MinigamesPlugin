package minigames.worlds;


import minigames.date.Date;
import minigames.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class WorldGui implements Listener {


    public static String invNameCreator = ChatColor.DARK_GREEN+"Criador de mundos";

    @EventHandler
    public void cick(InventoryClickEvent event){

    }
    public static void OpenWorldCreatorGui(Player p, String worldName){
        Inventory inv = Bukkit.createInventory(null, 27, invNameCreator);
        p.openInventory(inv);
        try {
            Statement s = Main.c.createStatement();
            ResultSet res = s.executeQuery("SELECT * FROM worldsData WHERE Name='" + worldName + "';");
            if(res.next()) {
                ItemStack grass = new ItemStack(Material.GRASS);
                ItemMeta grassMeta = grass.getItemMeta();
                ArrayList<String> grassLore = new ArrayList<String>();
                grassLore.add(ChatColor.AQUA+"Nome: "+worldName);
                grassLore.add(ChatColor.AQUA+"Data de criação: " + Date.getLongToDateString(res.getLong("CreationDate")));
                grassLore.add(ChatColor.AQUA+"Criado por: " + res.getString("CreationBy"));
                grassLore.add(ChatColor.AQUA+"Minigame: " + res.getString("Minigame"));
                grassLore.add(ChatColor.AQUA+"Online: " + res.getBoolean("Online"));
                grassMeta.setLore(grassLore);
                grassMeta.setDisplayName(ChatColor.GREEN+worldName);
                grass.setItemMeta(grassMeta);

                p.getOpenInventory().setItem(4, grass);
            } else {

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

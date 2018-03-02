package minigames.worlds;


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

import java.util.ArrayList;

public class WorldGui implements Listener {


    public static String invNameCreator = ChatColor.DARK_GREEN+"Criador de mundos";

    @EventHandler
    public void cick(InventoryClickEvent event){




    }



    public static void OpenWorldCreatorGui(Player p, String worldName){



        Inventory inv = Bukkit.createInventory(null, 27, invNameCreator);

        p.openInventory(inv);

        ItemStack grass = new ItemStack(Material.GRASS);
        ItemMeta grassMeta = grass.getItemMeta();
        ArrayList<String> grassLore = new ArrayList<String>();
        grassLore.add(ChatColor.AQUA+"Nome: "+worldName);
        grassLore.add(ChatColor.AQUA+"Data de criação: 00/00/0000 - 00:00:00");
        grassLore.add(ChatColor.AQUA+"Criado por: "+p.getDisplayName());
        grassLore.add(ChatColor.AQUA+"Minigame: Nenhum");
        grassLore.add(ChatColor.AQUA+"Online: false");
        grassMeta.setLore(grassLore);
        grassMeta.setDisplayName(ChatColor.GREEN+worldName);
        grass.setItemMeta(grassMeta);

        p.getOpenInventory().setItem(4, grass);



    }



}

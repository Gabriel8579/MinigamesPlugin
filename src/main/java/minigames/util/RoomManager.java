package minigames.util;

import minigames.date.Date;
import minigames.main.Main;
import minigames.main.Messages;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RoomManager {

    public static Map<Player, String> rooms = new HashMap<Player, String>();

    public static String getRoom(Player p) {
        if (rooms.containsKey(p)) {
            return rooms.get(p);
        }
        try {
            Statement s = Main.c.createStatement();
            ResultSet res = s.executeQuery("SELECT Name FROM roomsData WHERE World='" + p.getWorld().getName() + "';");
            if (res.next()) {
                rooms.put(p, res.getString("Name"));
                return res.getString("Name");
            }
            res.close();
            s.close();
            return "???";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "???";
    }

    public static boolean isOpen(String r) throws SQLException {
        Statement s = Main.c.createStatement();
        ResultSet res = s.executeQuery("SELECT Open FROM roomsData WHERE Name='" + r + "';");
        if (res.next()) {
            return res.getBoolean("Open");
        }
        res.close();
        s.close();
        return false;
    }

    public static boolean isCreated(String r) throws SQLException {
        Statement s = Main.c.createStatement();
        ResultSet res = s.executeQuery("SELECT * FROM roomsData WHERE Name='" + r + "';");
        if (res.next()) {
            return true;
        }
        res.close();
        s.close();
        return false;
    }

    public static void deleteRoom(Player p, String r) throws SQLException {
        if (!isCreated(r)) {
            Messages.sendMessage(p, Messages.MessageType.ROOM, ChatColor.RED + "Sala não encontrada!");
            return;
        }
        Statement s = Main.c.createStatement();
        ResultSet res = s.executeQuery("SELECT * FROM roomsData WHERE Name='" + r + "';");
        if (!res.next()) {
            return;
        }
        World w = Bukkit.getWorld(res.getString("World") + "-" + r);
        s.execute("DELETE FROM roomsData WHERE Name='" + r + "';");
        s.execute("DELETE FROM worldsData WHERE Name='" + w.getName() + "-" + r + "';");
        for (Player s2 : Bukkit.getOnlinePlayers()) {
            if (getRoom(s2).equalsIgnoreCase(r)) {
                String[] sla = r.split("-");
                String bb = getBestRoom(p, sla[0]);
                if (bb != null) {
                    setRoom(s2, r, bb);
                    Messages.sendMessage(s2, Messages.MessageType.ROOM, "A sala que você estava agora esta reiniciando. Você foi movido para outra sala.");
                } else {
                    setRoom(s2, r, getBestRoom(s2, getBestRoom(p, "Hub")));
                    Messages.sendMessage(s2, Messages.MessageType.ROOM, "A sala que você estava agora esta reiniciando. Você foi movido para outra sala.");
                }
            }
        }
        unloadWorld(w);
        File wfile = getWorldFile(w.getName());
        deleteWorld(getWorldFile(w.getName()));
        res.close();
        s.close();
        Messages.sendMessage(p, Messages.MessageType.ROOM, "Sala excluida com sucesso");
    }

    public static void closeRoom(Player p, String r) throws SQLException {
        if (!isOpen(r)) {
            Messages.sendMessage(p, Messages.MessageType.ROOM, ChatColor.RED + "Esta sala já está fechada!");
            return;
        }
        Statement s = Main.c.createStatement();
        s.execute("UPDATE roomsData SET Open=0 WHERE Name='" + r + "';");
        s.close();
        if (p != null) {
            Messages.sendMessage(p, Messages.MessageType.ROOM, "Sala fechada com sucesso");
        }
    }

    public static void openRoom(Player p, String r) throws SQLException {
        if (isOpen(r)) {
            Messages.sendMessage(p, Messages.MessageType.ROOM, ChatColor.RED + "Esta sala já está aberta!");
            return;
        }
        Statement s = Main.c.createStatement();
        s.execute("UPDATE roomsData SET Open=1 WHERE Name='" + r + "';");
        s.close();
        TextComponent t = new TextComponent("§1[§9Sala§1] > §7A sala " + r + " acabou de abrir. Clique aqui para entrar.");
        HoverEvent hv = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§fClique aqui para entrar em §b" + r).create());
        ClickEvent cv = new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/sala " + r);
        t.setHoverEvent(hv);
        t.setClickEvent(cv);
        for (Player s2 : Bukkit.getOnlinePlayers()) {
            s2.spigot().sendMessage(t);
        }
        if (p != null) {
            Messages.sendMessage(p, Messages.MessageType.ROOM, "Sala fechada com sucesso");
        }
    }

    public static boolean canJoin(Player p, String r) throws SQLException {
        Statement s = Main.c.createStatement();
        ResultSet res = s.executeQuery("SELECT Role FROM roomsData WHERE Name='" + r + "';");
        if (res.next()) {
            if (RankManager.containsRole(p, res.getString("Role"))) {
                return true;
            }
        }
        res.close();
        s.close();
        return false;
    }

    public static String getBestRoom(Player p, String r) throws SQLException {
        Statement s = Main.c.createStatement();
        ResultSet res = s.executeQuery("SELECT * FROM roomsData WHERE Name LIKE '%" + r + "%';");
        while (res.next()) {
            if (isOpen(res.getString("Name"))) {
                if (canJoin(p, res.getString("Name"))) {
                    return res.getString("Name");
                }
            }
        }
        return null;
    }

    public static boolean alreadyConnected(Player p, String r) {
        if (r.equalsIgnoreCase(getRoom(p))) {
            Messages.sendMessage(p, Messages.MessageType.ROOM, ChatColor.RED + "Já conectado a esta sala");
            return true;
        }
        return false;
    }


    public static void setRoom(Player p, String r1, String r2) throws SQLException {
        if (alreadyConnected(p, r2)) {
            return;
        }
        if (rooms.containsKey(p)) {
            rooms.remove(p);
        }
        Statement s = Main.c.createStatement();
        ResultSet res = s.executeQuery("SELECT * FROM roomsData WHERE Name='" + r2 + "';");
        if (!res.next()) {
            return;
        }
        ResultSet res2 = s.executeQuery("SELECT * FROM worldsData WHERE Name='" + res.getString("World") + "';");
        if (!res2.next()) {
            return;
        }
        rooms.put(p, r2);
        World w = Bukkit.getWorld(res2.getString("Name") + "-" + r2);
        p.teleport(new Location(w, res2.getDouble("X"), res2.getDouble("Y"), res2.getDouble("Z")));
        Messages.sendMessage(p, Messages.MessageType.ROOM, "Você mudou de " + r1 + " para " + r2);
        res.close();
        res2.close();
        s.close();
        updateVanish(p, r2);
        return;
    }

    public static void restartRoom(Player p, String r) {
        try {
            if (isCreated(r)) {
                closeRoom(null, r);
                for (Player s : Bukkit.getOnlinePlayers()) {
                    if (getRoom(s).equalsIgnoreCase(r)) {
                        String[] sla = r.split("-");
                        String bb = getBestRoom(p, sla[0]);
                        if (bb != null) {
                            setRoom(s, r, bb);
                            Messages.sendMessage(s, Messages.MessageType.ROOM, "A sala que você estava agora esta reiniciando. Você foi movido para outra sala.");
                        } else {
                            setRoom(s, r, getBestRoom(s, getBestRoom(p, "Hub")));
                            Messages.sendMessage(s, Messages.MessageType.ROOM, "A sala que você estava agora esta reiniciando. Você foi movido para outra sala.");
                        }
                    }
                }
                Statement s = Main.c.createStatement();
                ResultSet res = s.executeQuery("SELECT World FROM roomsData WHERE Name='" + r + "';");
                if (res.next()) {
                    File worldpath = getWorldFile(res.getString("World"));
                    File wfile = getWorldFile(res.getString("World") + "-" + r);
                    unloadWorld(Bukkit.getWorld(res.getString("World") + "-" + r));
                    deleteWorld(wfile);
                    copyWorld(worldpath, wfile);
                    loadWorld(res.getString("World") + "-" + r);
                    openRoom(null, r);
                    return;
                }
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void createRoom(Player p, String rn, String wn, String role, boolean op) {
        int a = 0;
        if (op) {
            a = 1;
        }
        try {
            if (!isCreated(rn)) {
                Statement s = Main.c.createStatement();
                World w = Bukkit.getWorld(wn);
                if (w == null) {
                    Messages.sendMessage(p, Messages.MessageType.ROOM, ChatColor.RED + "Mundo não encontrado!");
                    return;
                }
                s.execute("INSERT INTO roomsData (id, Name, World, Open, Role) VALUES (NULL, '" + rn + "','" + wn + "'," + a + ",'" + role + "');");
                s.execute("INSERT INTO worldsData (id,Name,CreationDate,CreationBy,Minigame,X,Y,Z,Online) VALUES (NULL,'" + wn + "-" + rn + "','" + Date.getLongDate() + "','" + p.getDisplayName() + "','Nenhum','" + w.getSpawnLocation().getX() + "', '" + w.getSpawnLocation().getY() + "','" + w.getSpawnLocation().getZ() + "', 1);");
                s.close();
                File dir = getWorldFile(wn);
                File roomd = getWorldFile(wn + "-" + rn);
                copyWorld(dir, roomd);
                loadWorld(wn + "-" + rn);
            } else {
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (op) {
            TextComponent t = new TextComponent("§1[§9Sala§1] > §7A sala " + rn + " acabou de abrir. Clique aqui para entrar.");
            HoverEvent hv = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§fClique aqui para entrar em §b" + rn).create());
            ClickEvent cv = new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/sala " + rn);
            t.setHoverEvent(hv);
            t.setClickEvent(cv);
            for (Player s : Bukkit.getOnlinePlayers()) {
                s.spigot().sendMessage(t);
            }
        }
        Messages.sendMessage(p, Messages.MessageType.ROOM, "Sala criada com sucesso!");
        return;
    }

    public static void updateVanish(Player p, String r) {
        for (Player s : Bukkit.getOnlinePlayers()) {
            if (getRoom(s).equalsIgnoreCase(r)) {
                p.showPlayer(s);
                s.showPlayer(p);
            } else {
                p.hidePlayer(s);
                s.hidePlayer(p);
            }
        }
        return;
    }

    public static void loadWorld(String nw) {
        Bukkit.getServer().createWorld(new WorldCreator(nw));
    }

    private static File getWorldFile(String wn) {
        System.out.println(Bukkit.getWorldContainer().getAbsolutePath().replace("\\", "/").replace("/./", "/") + "/" + wn);
        return new File(Bukkit.getWorldContainer().getAbsolutePath().replace("\\", "/").replace("/./", "/") + "/" + wn);
    }

    private static void unloadWorld(World world) {
        if (!world.equals(null)) {
            Bukkit.getServer().unloadWorld(world, false);
            System.out.println(world.getName() + " unloaded!");
        }
    }

    private static boolean deleteWorld(File path) {
        if (path.exists()) {
            File files[] = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteWorld(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return (path.delete());
    }

    private static void copyWorld(File source, File target) {
        try {
            ArrayList<String> ignore = new ArrayList<String>(Arrays.asList("uid.dat", "session.dat"));
            if (!ignore.contains(source.getName())) {
                if (source.isDirectory()) {
                    if (!target.exists())
                        target.mkdirs();
                    String files[] = source.list();
                    for (String file : files) {
                        File srcFile = new File(source, file);
                        File destFile = new File(target, file);
                        copyWorld(srcFile, destFile);
                    }
                } else {
                    InputStream in = new FileInputStream(source);
                    OutputStream out = new FileOutputStream(target);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = in.read(buffer)) > 0)
                        out.write(buffer, 0, length);
                    in.close();
                    out.close();
                }
            }
        } catch (IOException e) {

        }
    }
}
package minigames.util;

import minigames.main.Main;
import minigames.main.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        if (res.next()) {
            if (isOpen(res.getString("Name"))) {
                if (canJoin(p, res.getString("Name"))) {
                    return res.getString("Name");
                }
                return null;
            }
            return null;

        }
        return null;
    }


    public static void setRoom(Player p, String r1, String r2) throws SQLException {
        if (rooms.containsKey(p)) {
            rooms.remove(p);
        }
        Statement s = Main.c.createStatement();
        ResultSet res = s.executeQuery("SELECT World FROM roomsData WHERE Name='" + r1 + "';");
        if (!res.next()) {
            return;
        }
        ResultSet res2 = s.executeQuery("SELECT * FROM worldsData WHERE Name='" + res.getString("World") + "';");
        if (!res2.next()) {
            return;
        }
        rooms.put(p, r2);
        p.teleport(new Location(Bukkit.getWorld(res2.getString("Name")), res2.getDouble("X"), res2.getDouble("Y"), res2.getDouble("Z")));
        Messages.sendMessage(p, Messages.MessageType.ROOM, "Você mudou de " + r1 + " para " + r2);
        for (Player s2 : Bukkit.getOnlinePlayers()) {
            if (getRoom(s2).equalsIgnoreCase(r1)) {
                s2.hidePlayer(p);
                Messages.sendMessage(p, Messages.MessageType.ROOM, "O jogador " + p.getDisplayName() + " deixou a sala.");
            }
        }
        res.close();
        res2.close();
        s.close();
        updateVanish(p, r2);
        return;
    }

    public static void createRoom(Player p, String rn, String wn, String role, boolean op) {
        int a = 0;
        if (op) {
            a = 1;
        }
        try {
            if (!isCreated(rn)) {
                Statement s = Main.c.createStatement();
                s.execute("INSERT INTO roomsData (id, Name, World, Open, Role) VALUES (NULL, '" + rn + "','" + wn + "'," + a + ",'" + role + "');");
                s.close();
                return;
            }
            return;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return;
    }

    public static void updateVanish(Player p, String r) {
        for (Player s : Bukkit.getOnlinePlayers()) {
            if (getRoom(s).equalsIgnoreCase(r)) {
                p.showPlayer(s);
                Messages.sendMessage(s, Messages.MessageType.ROOM, "O jogador " + p.getDisplayName() + " entrou na sala.");
            } else {
                p.hidePlayer(s);
            }
        }
        return;
    }
}

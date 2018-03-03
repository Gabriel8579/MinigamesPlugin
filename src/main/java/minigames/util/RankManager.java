package minigames.util;

import minigames.main.Main;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RankManager {
    public static String getRoles(Player p) throws SQLException {
        Statement s = Main.c.createStatement();
        ResultSet res = s.executeQuery("SELECT Roles FROM playersData WHERE UUID='" + p.getUniqueId() + "';");
        if (res.next()) {
            return res.getString("Roles");
        }
        res.close();
        s.close();
        return null;
    }

    public static boolean containsRole(Player p, String role) {
        try {
            if (getRoles(p) == null) {
                return false;
            }
            if (getRoles(p).toLowerCase().contains(role.toLowerCase())) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void giveRole(Player p, String role) throws SQLException {
        if (!containsRole(p, role)) {
            Statement s = Main.c.createStatement();
            ResultSet res = s.executeQuery("SELECT Roles FROM playersData WHERE UUID='" + p.getUniqueId() + "';");
            String r = res.getString("Roles");
            r += "," + role;
            s.executeUpdate("UPDATE playersData SET Roles='" + r + "' WHERE UUID='" + p.getUniqueId() + "';");
            res.close();
            s.close();
            return;
        }
    }

    public static void setRole(Player p, String role) throws SQLException {
        Statement s = Main.c.createStatement();
        s.executeUpdate("UPDATE playersData SET Roles='" + role + "' WHERE UUID='" + p.getUniqueId() + "';");
        s.close();
        return;
    }

    public static void removeRole(Player p, String role) throws SQLException {
        Statement s = Main.c.createStatement();
        ResultSet res = s.executeQuery("SELECT Roles FROM playersData WHERE UUID='" + p.getUniqueId() + "';");
        if (containsRole(p, role)) {
            String r = res.getString("Roles");
            r.replaceAll("," + role.toLowerCase() + ",", ",").replaceAll(role + ",", ",").replaceAll("," + role, ",");
            s.executeUpdate("UPDATE playersData SET Roles='" + r + "' WHERE UUID='" + p.getUniqueId() + "';");
        }
        res.close();
        s.close();
        return;
    }
}

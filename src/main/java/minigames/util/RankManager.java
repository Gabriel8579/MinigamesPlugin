package minigames.util;

import minigames.main.Main;
import minigames.main.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RankManager {
    public static String getRoles(String p) throws SQLException {
        Statement s = Main.c.createStatement();
        ResultSet res = s.executeQuery("SELECT Roles FROM playersData WHERE Nick='" + p + "';");
        if (res.next()) {
            return res.getString("Roles");
        }
        res.close();
        s.close();
        return null;
    }

    public static boolean containsRole(String p, String role) {
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


    public static boolean giveRole(String p, String role) throws SQLException {
        if (!containsRole(p, role)) {
            Statement s = Main.c.createStatement();
            ResultSet res = s.executeQuery("SELECT Roles FROM playersData WHERE Nick='" + p + "';");
            String r = res.getString("Roles");
            r += "," + role;
            s.executeUpdate("UPDATE playersData SET Roles='" + r + "' WHERE Nick='" + p + "';");
            res.close();
            s.close();
            if (Bukkit.getPlayer(p) instanceof Player) {
                Messages.sendMessage(Bukkit.getPlayer(p), Messages.MessageType.ROLE, "Você recebeu o cargo: §e" + role.toUpperCase());
            }
            return true;
        }
        return false;
    }

    public static boolean setRole(String p, String role) throws SQLException {
        Statement s = Main.c.createStatement();
        s.executeUpdate("UPDATE playersData SET Roles='" + role + "' WHERE Nick='" + p + "';");
        s.close();
        if (Bukkit.getPlayer(p) instanceof Player) {
            Messages.sendMessage(Bukkit.getPlayer(p), Messages.MessageType.ROLE, "Seu cargo foi alterado para: §e" + role.toUpperCase());
        }
        return true;
    }

    public static boolean removeRole(String p, String role) throws SQLException {
        Statement s = Main.c.createStatement();
        ResultSet res = s.executeQuery("SELECT Roles FROM playersData WHERE Nick='" + p + "';");
        if (containsRole(p, role)) {
            String r = res.getString("Roles");
            r.replaceAll("," + role.toLowerCase() + ",", ",").replaceAll(role + ",", ",").replaceAll("," + role, ",");
            s.executeUpdate("UPDATE playersData SET Roles='" + r + "' WHERE Nick='" + p + "';");
        }
        res.close();
        s.close();
        if (Bukkit.getPlayer(p) instanceof Player) {
            Messages.sendMessage(Bukkit.getPlayer(p), Messages.MessageType.ROLE, "Você perdeu o cargo: §e" + role.toUpperCase());
        }
        return true;
    }
}

package minigames.util;

import minigames.main.Main;
import minigames.tntwars.TntWars;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Scoreboard {
    public static void updateBoard(Player p) throws SQLException {
        String title = null;
        Statement st = Main.c.createStatement();
        if (RoomManager.getRoom(p).toLowerCase().contains(Main.phub)) {
            title = ChatColor.YELLOW + "TCN MiniGames";
        }
        if (RoomManager.getRoom(p).toLowerCase().contains(Main.ptnt)) {
            title = ChatColor.RED + "TCN TntWars";
        }
        SimpleScoreBoard s = new SimpleScoreBoard(title);
        s.blankLine();
        if (RoomManager.getRoom(p).toLowerCase().contains(Main.phub)) {
            s.add("§6Bem vindo a rede TCN");
            s.add("§6Clique em alguma placa para jogar");
            s.blankLine();
            s.add("§6Games disponíveis:");
            s.add("§e-TntWars");
            s.build();
            s.send(p);
        }
        if (RoomManager.getRoom(p).toLowerCase().contains(Main.ptnt)) {
            if (TntWars.getState(RoomManager.getRoom(p)).equals(TntWars.State.WAITING)) {
                ResultSet res = st.executeQuery("SELECT Max FROM tntwarsData WHERE Room='" + RoomManager.getRoom(p) + "';");
                res.next();
                int tot = RoomManager.getPlayersInRoom(RoomManager.getRoom(p));
                int need = res.getInt("Max");
                int wt = need - tot;
                if (tot < need) {
                    s.add("§eAguardando " + wt + " jogadores.");
                }
            }

        }

    }

}

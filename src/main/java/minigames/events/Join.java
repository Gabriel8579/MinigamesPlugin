package minigames.events;

import minigames.main.Main;
import minigames.util.RankManager;
import minigames.util.RoomManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Join implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) throws SQLException {
        Player p = e.getPlayer();
        Statement s = Main.c.createStatement();
        ResultSet res = s.executeQuery("SELECT * FROM playersData WHERE UUID='" + p.getUniqueId() + "';");
        if (!res.next()) {
            s.execute("INSERT INTO playersData (id,Nick,UUID,IP,Email,Roles) VALUES (NULL,'" + p.getDisplayName() + "','" + p.getUniqueId() + "','" + p.getAddress().getHostString() + "', NULL, NULL);");
        }
        res.close();
        s.close();
        if (!RankManager.containsRole(p, "Normal")) {
            RankManager.setRole(p, "Normal");
        }
        String br = RoomManager.getBestRoom(p, "Hub");
        if (br != null) {
            RoomManager.setRoom(p, "???", br);
        }

    }

}
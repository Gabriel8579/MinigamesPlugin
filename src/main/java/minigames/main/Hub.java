package minigames.main;

import minigames.util.RoomManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class Hub implements Listener {

    @EventHandler
    public void cairDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (RoomManager.getRoom(p).contains(Main.phub)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void interagir(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        if (RoomManager.getRoom(p).contains(Main.phub)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void fome(FoodLevelChangeEvent e) {
        Player p = (Player) e.getEntity();
        if (RoomManager.getRoom(p).contains(Main.phub)) {
            e.setFoodLevel(20);
            e.setCancelled(true);
        }
    }

}

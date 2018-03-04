package minigames.tntwars;

import minigames.main.Main;
import minigames.main.Messages;
import minigames.util.RoomManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TntWars {
    public static int time = 30;
    private static State state;

    public static void createTntWars(String room, Player p) throws SQLException {
        if (!RoomManager.isCreated(room) && !RoomManager.isOpen(room) && !room.toLowerCase().contains(Main.ptnt) && !isCreated(room)) {
            if (p instanceof Player) {
                Messages.sendMessage(p, Messages.MessageType.TNT, ChatColor.RED + "Esta sala não está disponível!");
                return;
            }
            System.out.println("§1[§9TntWars§1] > §cEsta sala nao esta disponivel!");
            return;
        }
        Statement s = Main.c.createStatement();
        s.execute("INSERT INTO tntwarsData (id, Name, Room, Min, Max) VALUES (NULL, '" + room + "', '" + room + "', 2, 12;");
        Main.tntwars.put(room, State.WAITING);
        TextComponent t = new TextComponent("§1[§9Sala§1] > §7A sala " + room + " acabou de abrir. Clique aqui para entrar e jogar TntWars.");
        HoverEvent hv = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§fClique aqui para entrar em §b" + room).create());
        ClickEvent cv = new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/sala " + room);
        t.setHoverEvent(hv);
        t.setClickEvent(cv);
        for (Player s2 : Bukkit.getOnlinePlayers()) {
            s2.spigot().sendMessage(t);
        }
        if (p instanceof Player) {
            Messages.sendMessage(p, Messages.MessageType.TNT, "Arena criada com sucesso.");
        }
    }

    public static void startCount(String room) {
        try {
            if (isCreated(room)) {
                for (int i = 30; i != 0; i--) {
                    time = i;
                    if (i == 30) {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (RoomManager.getRoom(p).equalsIgnoreCase(room)) {
                                Messages.sendMessage(p, Messages.MessageType.TNT, "O jogo iniciará em: " + time + " segundos");
                            }
                        }
                    }
                    if (i == 10) {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (RoomManager.getRoom(p).equalsIgnoreCase(room)) {
                                Messages.sendMessage(p, Messages.MessageType.TNT, "O jogo iniciará em: " + time + " segundos");
                            }
                        }
                    }
                    if (i <= 5) {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (RoomManager.getRoom(p).equalsIgnoreCase(room)) {
                                Messages.sendMessage(p, Messages.MessageType.TNT, "O jogo iniciará em: " + time + " segundos");
                            }
                        }
                    }
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isCreated(String room) throws SQLException {
        Statement s = Main.c.createStatement();
        ResultSet res = s.executeQuery("SELECT * FROM tntwarsData WHERE Room='" + room + "';");
        return res.next();
    }

    public static void setState(State s) {
        state = s;
    }

    public static State getState(String room) {
        State state = null;
        if (Main.tntwars.containsKey(room)) {
            return Main.tntwars.get(room);
        }
        return state;
    }

    public enum State {
        WAITING, STARTING, PLAYING, STOPPING, RESTARTING
    }
}

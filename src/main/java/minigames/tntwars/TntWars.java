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
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class TntWars {
    public static int time = 30;

    private static State state;
    public static Map<Player, Team> playerteam = new HashMap<Player, Team>();
    public static Map<Player, Integer> playerliferemain = new HashMap<Player, Integer>();
    private static Team team;

    public static void createTntWars(String room, Player p) throws SQLException, IOException {
        if (!RoomManager.isCreated(room) && !RoomManager.isOpen(room) && !room.toLowerCase().contains(Main.ptnt) && !isCreated(room)) {
            if (p instanceof Player) {
                Messages.sendMessage(p, Messages.MessageType.TNT, ChatColor.RED + "Esta sala não está disponível!");
                return;
            }
            System.out.println("§1[§9TntWars§1] > §cEsta sala nao esta disponivel!");
            return;
        }
        Statement s = Main.c.createStatement();
        s.execute("INSERT INTO tntwarsData (id, Name, Room, Min, Max) VALUES (NULL, '" + room + "', '" + room + "', 2, 12);");
        Main.tntwars.put(room, State.WAITING);
        RoomManager.closeRoom(null, room);
        TextComponent t = new TextComponent("§1[§9Sala§1] > §7A sala " + room + " acabou de abrir. Clique aqui para entrar e jogar TntWars.");
        HoverEvent hv = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§fClique aqui para entrar em §b" + room).create());
        ClickEvent cv = new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/sala " + room);
        t.setHoverEvent(hv);
        t.setClickEvent(cv);
        Main.getConfigTnt().set("arenas." + room + ".spawns.red.w", "");
        Main.getConfigTnt().set("arenas." + room + ".spawns.red.x", "");
        Main.getConfigTnt().set("arenas." + room + ".spawns.red.y", "");
        Main.getConfigTnt().set("arenas." + room + ".spawns.red.z", "");
        Main.getConfigTnt().set("arenas." + room + ".spawns.blue.w", "");
        Main.getConfigTnt().set("arenas." + room + ".spawns.blue.x", "");
        Main.getConfigTnt().set("arenas." + room + ".spawns.blue.y", "");
        Main.getConfigTnt().set("arenas." + room + ".spawns.blue.z", "");
        Main.getConfigTnt().set("arenas." + room + ".spawns.lobby.w", "");
        Main.getConfigTnt().set("arenas." + room + ".spawns.lobby.x", "");
        Main.getConfigTnt().set("arenas." + room + ".spawns.lobby.y", "");
        Main.getConfigTnt().set("arenas." + room + ".spawns.lobby.z", "");
        Main.getConfigTnt().save(Main.getFileTnt());

        for (Player s2 : Bukkit.getOnlinePlayers()) {
            s2.spigot().sendMessage(t);
        }
        if (p instanceof Player) {
            Messages.sendMessage(p, Messages.MessageType.TNT, "Arena criada com sucesso.");
        }
    }

    public static void open(Player p, String r) throws SQLException {
        if (!isCreated(r)) {
            if (p != null) {
                Messages.sendMessage(p, Messages.MessageType.TNT, "§cEsta arena ainda não foi criada!");
                return;
            }
            System.out.println("Esta arena não foi criada!");
            return;
        }
        if ((Main.getConfigTnt().getString("arenas." + r + ".spawns.red.w") != null) &&
                (Main.getConfigTnt().getString("arenas." + r + ".spawns.red.x") != null) && (Main.getConfigTnt().getString("arenas." + r + ".spawns.red.y") != null) && (Main.getConfigTnt().getString("arenas." + r + ".spawns.red.z") != null) &&
                (Main.getConfigTnt().getString("arenas." + r + ".spawns.blue.w") != null) &&
                (Main.getConfigTnt().getString("arenas." + r + ".spawns.blue.x") != null) &&
                (Main.getConfigTnt().getString("arenas." + r + ".spawns.blue.y") != null) &&
                (Main.getConfigTnt().getString("arenas." + r + ".spawns.blue.z") != null) &&
                (Main.getConfigTnt().getString("arenas." + r + ".spawns.lobby.w") != null) &&
                (Main.getConfigTnt().getString("arenas." + r + ".spawns.lobby.x") != null) &&
                (Main.getConfigTnt().getString("arenas." + r + ".spawns.lobby.y") != null) &&
                (Main.getConfigTnt().getString("arenas." + r + ".spawns.lobby.z") != null)) {
            RoomManager.openRoom(p, r);
        } else {
            if (p != null) {
                Messages.sendMessage(p, Messages.MessageType.TNT, "§cEsta arena ainda não teve seus parâmetros setados!");
                return;
            }
            System.out.println("Esta arena ainda não teve seus parâmetros setados!");
            return;
        }
    }

    public static void setRedSpawn(Player p, String r) throws SQLException, IOException {
        if (isCreated(r)) {
            Main.getConfigTnt().set("arenas." + r + ".spawns.red.w", p.getWorld().getName());
            Main.getConfigTnt().set("arenas." + r + ".spawns.red.x", p.getLocation().getX());
            Main.getConfigTnt().set("arenas." + r + ".spawns.red.y", p.getLocation().getY());
            Main.getConfigTnt().set("arenas." + r + ".spawns.red.z", p.getLocation().getZ());
            Main.getConfigTnt().save(Main.getFileTnt());
            Messages.sendMessage(p, Messages.MessageType.TNT, "Posição do time vermelho setada com sucesso!");
            return;
        }
        Messages.sendMessage(p, Messages.MessageType.TNT, "§cArena não encontrada!");
    }

    public static void setBlueSpawn(Player p, String r) throws SQLException, IOException {
        if (isCreated(r)) {
            Main.getConfigTnt().set("arenas." + r + ".spawns.blue.w", p.getWorld().getName());
            Main.getConfigTnt().set("arenas." + r + ".spawns.blue.x", p.getLocation().getX());
            Main.getConfigTnt().set("arenas." + r + ".spawns.blue.y", p.getLocation().getY());
            Main.getConfigTnt().set("arenas." + r + ".spawns.blue.z", p.getLocation().getZ());
            Main.getConfigTnt().save(Main.getFileTnt());
            Messages.sendMessage(p, Messages.MessageType.TNT, "Posição do time azul setada com sucesso!");
            return;
        }
        Messages.sendMessage(p, Messages.MessageType.TNT, "§cArena não encontrada!");
    }

    public static void setLobbySpawn(Player p, String r) throws SQLException, IOException {
        if (isCreated(r)) {
            Main.getConfigTnt().set("arenas." + r + ".spawns.lobby.w", p.getWorld().getName());
            Main.getConfigTnt().set("arenas." + r + ".spawns.lobby.x", p.getLocation().getX());
            Main.getConfigTnt().set("arenas." + r + ".spawns.lobby.y", p.getLocation().getY());
            Main.getConfigTnt().set("arenas." + r + ".spawns.lobby.z", p.getLocation().getZ());
            Main.getConfigTnt().save(Main.getFileTnt());
            Messages.sendMessage(p, Messages.MessageType.TNT, "Posição do lobby setada com sucesso!");
            return;
        }
        Messages.sendMessage(p, Messages.MessageType.TNT, "§cArena não encontrada!");
    }

    public static void start(String room) throws SQLException {
        boolean red = false;
        if (isCreated(room)) {
            setState(State.PLAYING, room);
            for (Player s : Bukkit.getOnlinePlayers()) {
                if (RoomManager.getRoom(s).equalsIgnoreCase(room)) {
                    if (!red) {
                        String w = Main.getConfigTnt().getString("arenas." + room + ".spawns.red.w");
                        World wo = Bukkit.getWorld(w);
                        Double x = Main.getConfigTnt().getDouble("arenas." + room + ".spawns.red.x");
                        Double y = Main.getConfigTnt().getDouble("arenas." + room + ".spawns.red.y");
                        Double z = Main.getConfigTnt().getDouble("arenas." + room + ".spawns.red.z");
                        Location loc = new Location(wo, x, y, z);
                        playerteam.put(s, Team.RED);
                        playerliferemain.put(s, 3);
                        s.teleport(loc);
                        red = !red;
                    }
                    if (red) {
                        String w = Main.getConfigTnt().getString("arenas." + room + ".spawns.blue.w");
                        World wo = Bukkit.getWorld(w);
                        Double x = Main.getConfigTnt().getDouble("arenas." + room + ".spawns.blue.x");
                        Double y = Main.getConfigTnt().getDouble("arenas." + room + ".spawns.blue.y");
                        Double z = Main.getConfigTnt().getDouble("arenas." + room + ".spawns.blue.z");
                        Location loc = new Location(wo, x, y, z);
                        playerteam.put(s, Team.BLUE);
                        playerliferemain.put(s, 3);
                        s.teleport(loc);
                        red = !red;
                    }
                }
            }
            RoomManager.closeRoom(null, room);
            return;
        }
    }

    public static void startCount(String room) {
        try {
            if (isCreated(room)) {
                setState(State.STARTING, room);
                Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.pl, new Runnable() {
                    @Override
                    public void run() {
                        time--;
                        if (time == 30) {
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                if (RoomManager.getRoom(p).equalsIgnoreCase(room)) {
                                    Messages.sendMessage(p, Messages.MessageType.TNT, "O jogo iniciará em: " + time + " segundos");
                                }
                            }
                        }
                        if (time == 10) {
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                if (RoomManager.getRoom(p).equalsIgnoreCase(room)) {
                                    Messages.sendMessage(p, Messages.MessageType.TNT, "O jogo iniciará em: " + time + " segundos");
                                }
                            }
                        }
                        if (time <= 5) {
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                if (RoomManager.getRoom(p).equalsIgnoreCase(room)) {
                                    Messages.sendMessage(p, Messages.MessageType.TNT, "O jogo iniciará em: " + time + " segundos");
                                }
                            }
                        }
                    }
                }, 20L, 600L);


                start(room);

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

    public static void setState(State s, String room) {
        if (Main.tntwars.containsKey(room)) {
            Main.tntwars.remove(room);
            Main.tntwars.put(room, s);
        }
        Main.tntwars.put(room, s);
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

    public enum Team {
        RED, BLUE, SPECTATOR
    }
}

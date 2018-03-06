package minigames.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Team;

public class Scoreboard {
    public org.bukkit.scoreboard.Scoreboard sb;
    public Team t1;
    public Team t2;
    public Team t3;
    public Team t4;
    public Team t5;
    public FastOfflinePlayer f1;
    public FastOfflinePlayer f2;
    public FastOfflinePlayer f3;
    public FastOfflinePlayer f4;
    public FastOfflinePlayer f5;
    private Player p;

    public Scoreboard(Player p) {
        this.sb = p.getScoreboard();
        this.p = p;
    }

    public void build() {
        sb = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = sb.registerNewObjective("ScoreBoard", "dummy");
        obj.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + "TCN Minigames");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        t1 = sb.registerNewTeam("t1");
        t2 = sb.registerNewTeam("t2");
        t3 = sb.registerNewTeam("t3");
        t4 = sb.registerNewTeam("t4");
        t5 = sb.registerNewTeam("t5");
        p.setScoreboard(sb);
    }

    public void update() {
        Objective obj = sb.getObjective("ScoreBoard");
        f5 = new FastOfflinePlayer("§2");
        t5.addPlayer(f5);
        obj.getScore(f5).setScore(3);
        f4 = new FastOfflinePlayer(": §f");
        t4.setPrefix("§fObrigado por jogar");
        t4.setSuffix(" §fem nossos servidores");
        t4.addPlayer(f4);
        obj.getScore(f4).setScore(2);
        f3 = new FastOfflinePlayer("§fVIP");
        t3.setPrefix("§bAjude-nos comprando");
        t3.setSuffix(" §9VIP");
        t3.addPlayer(f3);
        obj.getScore(f3).setScore(1);
    }


        /*//String title = null;
        Statement st = Main.c.createStatement();
        /*if (RoomManager.getRoom(p).toLowerCase().startsWith(Main.phub)) {
            title = ChatColor.YELLOW + "TCN MiniGames";
        }
        if (RoomManager.getRoom(p).toLowerCase().startsWith(Main.ptnt)) {
            title = ChatColor.RED + "TCN TntWars";
        }*/
    // SimpleScoreBoard s = new SimpleScoreBoard(ChatColor.YELLOW + "TCN MiniGames");
    // s.blankLine();
    // s.add("Teste");
    // s.add("Teste2");
    // s.build();
    // s.send(p);
        /*if (RoomManager.getRoom(p).toLowerCase().startsWith(Main.phub)) {
            s.add("§6Bem vindo a rede TCN");
            s.add("§6Clique em alguma placa para jogar");
            s.blankLine();
            s.add("§6Games disponíveis:");
            s.add("§e-TntWars");
            s.build();
            s.send(p);
            return;
        }
        if (RoomManager.getRoom(p).toLowerCase().startsWith(Main.ptnt)) {
            if(!TntWars.isCreated(RoomManager.getRoom(p))) {
                s.add("§6Bem vindo a rede TCN");
                s.add("§6Clique em alguma placa para jogar");
                s.blankLine();
                s.add("§6Games disponíveis:");
                s.add("§e-TntWars");
                s.build();
                s.send(p);
                return;
            }
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
*/
}

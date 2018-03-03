package minigames.main;

import minigames.date.Date;
import minigames.events.Join;
import minigames.util.AdmCommand;
import minigames.util.RoomCommand;
import minigames.worlds.WorldCommand;
import minigames.worlds.WorldGui;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main extends JavaPlugin {

    public static Connection c;
    public static Main pl = null;
    @Override
    public void onEnable() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new WorldGui(), this);
        pl = this;
        pm.registerEvents(new Join(), this);
        c = DataBase.getConnection();
        DataBase.createDb();
        DataBase.createTable();
        getCommand("mundo").setExecutor(new WorldCommand());
        getCommand("sala").setExecutor(new RoomCommand());
        getCommand("adm").setExecutor(new AdmCommand());
        for(World w : Bukkit.getWorlds()){
            if(!w.getName().contains("_nether") && !w.getName().contains("_end")) {
                Statement st = null;
                try {
                    st = Main.c.createStatement();
                    ResultSet res = st.executeQuery("SELECT * FROM worldsData WHERE Name='" + w.getName() + "';");
                    boolean mainWorld = false;
                    if (res.next()) {
                            mainWorld = true;
                    }
                    if (mainWorld == false) {
                        String codeSql = "INSERT INTO worldsData VALUES (null,'" + w.getName() + "','" + Date.getLongDate() + "','Server','Nenhum', '" + w.getSpawnLocation().getX() + "','" + (w.getSpawnLocation().getY() + 1) + "','" + w.getSpawnLocation().getZ() + "',1);";
                        Statement ps = c.createStatement();
                        ps.execute(codeSql);
                    }
                    res.close();
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        Statement st = null;
        try {
            st = Main.c.createStatement();
            ResultSet res = st.executeQuery("SELECT * FROM worldsData;");
            while (res.next()) {
                String n = res.getString("Name");
                Bukkit.createWorld(new WorldCreator(n));
                System.out.println("Mundo com o ID #" + res.getInt("id") + " foi carregado!");
            }
            res.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.onEnable();
    }


    @Override
    public void onDisable() {
        pl = null;
        try {
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.onDisable();
    }
}

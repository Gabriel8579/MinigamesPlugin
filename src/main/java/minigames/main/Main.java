package minigames.main;

import minigames.date.Date;
import minigames.worlds.WorldCommand;
import minigames.worlds.WorldGui;
import org.bukkit.*;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.*;

public class Main extends JavaPlugin {

    public static Connection c;
    @Override
    public void onEnable() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new WorldGui(), this);
        DataBase.createDb();
        c =  DataBase.getConnection();
        getCommand("mundo").setExecutor(new WorldCommand());
        for(World w : Bukkit.getWorlds()){
            if(!w.getName().contains("_nether") && !w.getName().contains("_end")) {
                Statement st = null;
                try {
                    st = Main.c.createStatement();
                    ResultSet res = st.executeQuery("SELECT * FROM worldsData;");
                    boolean mainWorld = false;
                    while (res.next()) {
                        if (res.getString("Name").equals(w.getName())) {
                            mainWorld = true;
                        }
                    }
                    if (mainWorld == false) {
                        String codeSql = "INSERT INTO worldsData VALUES (null,'" + w.getName() + "','" + Date.getLongDate() + "','Server','Nenhum',1);";
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
        try {
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.onDisable();
    }
}

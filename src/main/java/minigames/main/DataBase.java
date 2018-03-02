package minigames.main;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.bukkit.Bukkit;

public class DataBase
{
    public static String url = "jdbc:sqlite:" + Bukkit.getWorldContainer().getAbsolutePath().replace("\\", "/") + "/database/db.db";

    public static void createDb()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        File dir = new File(Bukkit.getWorldContainer().getAbsolutePath().replace("\\", "/") + "/database");
        if (!dir.exists()) {
            dir.mkdir();
        }
        File db = new File(Bukkit.getWorldContainer().getAbsolutePath().replace("\\", "/") + "/database/db.db");
        if (!db.exists()) {
            try
            {
                db.createNewFile();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        try
        {
            Main.c = DriverManager.getConnection(url);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        createTable();
    }

    public static void createTable()
    {
        String sql = "CREATE TABLE IF NOT EXISTS playersData (\r\n" +
                "	id	INTEGER PRIMARY KEY AUTOINCREMENT,\r\n" +
                "	Nick	VARCHAR ( 16 ) NOT NULL,\r\n" +
                "	UUID	VARCHAR ( 36 ),\r\n" +
                "	IP	VARCHAR ( 15 ),\r\n" +
                "	Email	VARCHAR ( 64 ),\r\n" +
                "   Roles    VARCHAR ( 64 )\r\n"+
                ");";

        String sql2 = "CREATE TABLE IF NOT EXISTS worldsData (\r\n" +
                "	id	INTEGER PRIMARY KEY AUTOINCREMENT,\r\n" +
                "	Name	VARCHAR ( 64 ) NOT NULL,\r\n" +
                "	CreationDate	VARCHAR ( 64 ),\r\n" +
                "	CreationBy	VARCHAR ( 64 ),\r\n" +
                "	Minigame	VARCHAR ( 64 ),\r\n" +
                "   Online VARCHAR(64)\r\n" +
                ");";
        try


        {
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection(url);
            Statement st = con.createStatement();
            st.execute(sql);
            st.execute(sql2);
            st.close();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static Connection getConnection()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(url);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}

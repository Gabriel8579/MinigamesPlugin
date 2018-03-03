package minigames.main;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase
{
    public static String url = "jdbc:sqlite:" + Main.pl.getDataFolder().getAbsolutePath().replace("\\", "/") + "/database/db.db";

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
        File dir = new File(Main.pl.getDataFolder() + "/database");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File db = new File(Main.pl.getDataFolder() + "/database/db.db");
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
            Connection c = DriverManager.getConnection(url);
            if (c != null) {
                c.close();
                c = null;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public static void createTable()
    {
        String sql = "CREATE TABLE IF NOT EXISTS playersData (\r\n" +
                "	id	INTEGER PRIMARY KEY AUTOINCREMENT,\r\n" +
                "	Nick	VARCHAR ( 16 ) NOT NULL,\r\n" +
                "	UUID	VARCHAR ( 36 ),\r\n" +
                "	IP	VARCHAR ( 15 ),\r\n" +
                "	Email	VARCHAR ( 64 ),\r\n" +
                "   Roles    VARCHAR ( 500 )\r\n" +
                ");";

        String sql2 = "CREATE TABLE IF NOT EXISTS worldsData (\r\n" +
                "	id	INTEGER PRIMARY KEY AUTOINCREMENT,\r\n" +
                "	Name	VARCHAR ( 64 ) NOT NULL,\r\n" +
                "	CreationDate	BIGINT ( 64 ),\r\n" +
                "	CreationBy	VARCHAR ( 64 ),\r\n" +
                "	Minigame	VARCHAR ( 64 ),\r\n" +
                "   X DOUBLE(20)," +
                "   Y DOUBLE(20)," +
                "   Z DOUBLE(20)," +
                "   Online TINYINT(1)\r\n" +
                ");";
        String sql3 = "CREATE TABLE IF NOT EXISTS roomsData (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " Name VARCHAR (36) NOT NULL," +
                " World VARCHAR (64) NOT NULL," +
                " Open TINYINT (1)," +
                " Role VARCHAR(64));";

        try
        {
            Statement st = Main.c.createStatement();
            st.execute(sql);
            st.execute(sql2);
            st.execute(sql3);
            st.close();
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

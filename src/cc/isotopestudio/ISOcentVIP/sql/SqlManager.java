package cc.isotopestudio.ISOcentVIP.sql;

import cc.isotopestudio.ISOcentVIP.ISOcentVIP;

import java.sql.SQLException;

import static cc.isotopestudio.ISOcentVIP.ISOcentVIP.plugin;

/**
 * Created by Mars on 5/28/2016.
 * Copyright ISOTOPE Studio
 */
public class SqlManager {
    public static boolean connectMySQL() {
        String host = plugin.getConfig().getString("mySQL.host");
        String port = plugin.getConfig().getString("mySQL.port");
        String user = plugin.getConfig().getString("mySQL.user");
        String pw = plugin.getConfig().getString("mySQL.password");
        String db = plugin.getConfig().getString("mySQL.database");
        ISOcentVIP.mySQL = new MySQL(host, port, db, user, pw);
        ISOcentVIP.c = null;
        try {
            ISOcentVIP.c = ISOcentVIP.mySQL.openConnection();
        } catch (ClassNotFoundException e) {
            plugin.getLogger().info(ISOcentVIP.pluginName + "数据库出错 Error1");
            return false;
        } catch (SQLException e) {
            plugin.getLogger().info(ISOcentVIP.pluginName + "数据库出错 Error2");
            return false;
        }
        try {
            ISOcentVIP.statement = ISOcentVIP.c.createStatement();
        } catch (SQLException e1) {
            plugin.getLogger().info(ISOcentVIP.pluginName + "数据库出错 Error3");
            return false;
        }
        return true;
    }

    public static boolean createTables() {
        try {
            ISOcentVIP.statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS vip(" +
                            " player CHAR(20) NOT NULL PRIMARY KEY," +
                            " points INT NOT NULL," +
                            " days INT NOT NULL," +
                            " type TEXT NOT NULL" +
                            " );");
            ISOcentVIP.statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS info(" +
                            " k CHAR(20) NOT NULL PRIMARY KEY," +
                            " v DATE NOT NULL" +
                            " );");
        } catch (SQLException e) {
            e.printStackTrace();
            plugin.getLogger().info(ISOcentVIP.pluginName + "数据库出错 Error4");
            return false;
        }
        return true;
    }

}

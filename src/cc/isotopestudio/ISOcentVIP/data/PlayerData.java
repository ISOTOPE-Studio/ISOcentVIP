package cc.isotopestudio.ISOcentVIP.data;

import cc.isotopestudio.ISOcentVIP.type.VIPType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static cc.isotopestudio.ISOcentVIP.ISOcentVIP.c;
import static cc.isotopestudio.ISOcentVIP.ISOcentVIP.statement;

/**
 * Created by Mars on 5/27/2016.
 * Copyright ISOTOPE Studio
 */
public class PlayerData {

    public static void addDays(String playerName, int days) {

    }

    public static int getPoints(String playerName) {
        ResultSet res;
        try {
            res = statement.executeQuery("select * from vip where player=" + "\"" + playerName + "\"" + ";");
            if (!res.next())
                return 0;
            return res.getInt("points");
        } catch (SQLException e) {
            return 0;
        }
    }

    public static void setPoints(String playerName, int points) {
        try {
            ResultSet res = statement.executeQuery("SELECT * FROM vip WHERE player=" + "\"" + playerName + "\"" + ";");
            PreparedStatement statement;
            if (!res.next()) {
                statement = c.prepareStatement("INSERT INTO vip VALUES(?, ?, ?, ?);");
                statement.setString(1, playerName);
                statement.setString(2, points + "");
                statement.setString(3, "0");
                statement.setString(4, "NONE");
            } else {
                statement = c.prepareStatement("UPDATE vip SET points=? WHERE player=?;");
                statement.setString(1, points + "");
                statement.setString(2, playerName);
            }
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getLvl(String playerName) {
        return -1;
    }

    public static int getLvlReqPoints(String playerName) {
        return -1;
    }

    public static int getLvlReqPoints(int lvl) {
        return -1;
    }

    public static int getCurrentPoints(String playerName) {
        return -1;
    }

    public static int getNextLvlPoints(String playerName) {
        return -1;
    }

    public static int getRemainDays(String playerName) {
        ResultSet res;
        try {
            res = statement.executeQuery("select * from vip where player=" + "\"" + playerName + "\"" + ";");
            if (!res.next())
                return 0;
            return res.getInt("days");
        } catch (SQLException e) {
            return 0;
        }
    }

    public static String getExpireDate(String playerName) {
        return "";
    }

    public static VIPType getVIPType(String playerName) {
        return VIPType.NONE;
    }

    public static List<String> getRank(int count) {
        return null;
    }
}

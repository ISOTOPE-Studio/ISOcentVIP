package cc.isotopestudio.ISOcentVIP.data;

import cc.isotopestudio.ISOcentVIP.type.VIPType;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static cc.isotopestudio.ISOcentVIP.ISOcentVIP.*;

/**
 * Created by Mars on 5/27/2016.
 * Copyright ISOTOPE Studio
 */
public class PlayerData {
    private final static SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");

    /*
    true: become year VIP
    false: other conditions
    */
    public static boolean addDays(String playerName, int days) {
        int remains = getRemainDays(playerName);
        setRemainDays(playerName, remains + days);
        if (remains + days > 365) {
            setVIPType(playerName, VIPType.yVIP);
            if (remains < 365) {
                addPoints(playerName, Settings.yVIPGift);
                return true;
            }
        } else
            setVIPType(playerName, VIPType.mVIP);
        return false;
    }

    public static Permission perms = null;

    public static void addPoints(String playerName, int points) {
        RegisteredServiceProvider<Permission> rsp = plugin.getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        int lvl = getLvl(playerName);
        setPoints(playerName, getPoints(playerName) + points);
        if (getLvl(playerName) != lvl) {
            perms.playerAddGroup(playerName, "world", "group");
        }
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
                statement.setString(4, VIPType.NONE.toString());
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
        int points = getPoints(playerName);
        int lvl = 0;
        while (Settings.level.size() > lvl && points >= Settings.level.get(lvl)) {
            lvl++;
        }
        return lvl;
    }

    public static int getLvlReqPoints(String playerName) {
        int lvl = getLvl(playerName);
        if (Settings.level.size() <= lvl)
            return -1;
        return Settings.level.get(lvl + 1) - getPoints(playerName);
    }

    public static int getNextLvlPoints(String playerName) {
        int lvl = getLvl(playerName);
        if (Settings.level.size() <= lvl)
            return -1;
        return Settings.level.get(lvl + 1);
    }

    public static void setRemainDays(String playerName, int days) {
        try {
            ResultSet res = statement.executeQuery("SELECT * FROM vip WHERE player=" + "\"" + playerName + "\"" + ";");
            PreparedStatement statement;
            if (!res.next()) {
                statement = c.prepareStatement("INSERT INTO vip VALUES(?, ?, ?, ?);");
                statement.setString(1, playerName);
                statement.setString(2, 0 + "");
                statement.setString(3, days + "");
                statement.setString(4, VIPType.NONE.toString());
            } else {
                statement = c.prepareStatement("UPDATE vip SET days=? WHERE player=?;");
                statement.setString(1, days + "");
                statement.setString(2, playerName);
            }
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        int remains = getRemainDays(playerName);
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_YEAR, remains);
        Date expire = now.getTime();
        return format.format(expire);
    }

    public static void setVIPType(String playerName, VIPType type) {
        try {
            ResultSet res = statement.executeQuery("SELECT * FROM vip WHERE player=" + "\"" + playerName + "\"" + ";");
            PreparedStatement statement;
            if (!res.next()) {
                statement = c.prepareStatement("INSERT INTO vip VALUES(?, ?, ?, ?);");
                statement.setString(1, playerName);
                statement.setString(2, 0 + "");
                statement.setString(3, "0");
                statement.setString(4, type.toString());
            } else {
                statement = c.prepareStatement("UPDATE vip SET type=? WHERE player=?;");
                statement.setString(1, type.toString() + "");
                statement.setString(2, playerName);
            }
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static VIPType getVIPType(String playerName) {
        ResultSet res;
        try {
            res = statement.executeQuery("select * from vip where player=" + "\"" + playerName + "\"" + ";");
            if (!res.next())
                return VIPType.NONE;
            ;
            return VIPType.valueOf(res.getString("type"));
        } catch (SQLException e) {
            return VIPType.NONE;
        }
    }

    public static List<String> getVIPs(VIPType type) {
        ResultSet res = null;
        List<String> result = new ArrayList<>();
        try {
            switch (type) {
                case mVIP: {
                    res = statement.executeQuery("SELECT * FROM vip WHERE type=\"mVIP\";");
                    break;
                }
                case yVIP: {
                    res = statement.executeQuery("SELECT * FROM vip WHERE type=\"yVIP\";");
                    break;
                }
                case NONE:
                    res = statement.executeQuery("SELECT * FROM vip WHERE type=\"NONE\";");
                    break;
            }
            while (res.next()) {
                result.add(res.getString("player"));
            }
        } catch (SQLException e) {
        }
        return result;
    }


    public static List<String> getRank(int count) {
        return null;
    }
}

package cc.isotopestudio.ISOcentVIP.data;

import cc.isotopestudio.ISOcentVIP.type.VIPType;
import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static cc.isotopestudio.ISOcentVIP.ISOcentVIP.c;
import static cc.isotopestudio.ISOcentVIP.ISOcentVIP.statement;

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
        if (remains + days >= 365) {
            setVIPType(playerName, VIPType.yVIP);
            if (days >= 365) {
                addPoints(playerName, Settings.yVIPGift * (days / 365));
                return true;
            }
        } else
            setVIPType(playerName, VIPType.mVIP);
        return false;
    }

    public static void addPoints(String playerName, int points) {
        setPoints(playerName, getPoints(playerName) + points);
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
        int lvl = getLvl(playerName);
        try {
            ResultSet res = statement.executeQuery("SELECT * FROM vip WHERE player=" + "\"" + playerName + "\"" + ";");
            PreparedStatement statement;
            if (!res.next()) {
                statement = c.prepareStatement("INSERT INTO vip VALUES(?, ?, ?, ?, ?);");
                statement.setString(1, playerName);
                statement.setString(2, points + "");
                statement.setString(3, "0");
                statement.setString(4, VIPType.NONE.toString());
                statement.setString(5, "false");
            } else {
                statement = c.prepareStatement("UPDATE vip SET points=? WHERE player=?;");
                statement.setString(1, points + "");
                statement.setString(2, playerName);
            }
            statement.execute();
        } catch (SQLException ignored) {
        }
        if (getLvl(playerName) != lvl) {
            switch (getVIPType(playerName)) {
                case mVIP: {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                            "manuadd " + playerName + " " + Settings.mVIPGroup.get(getLvl(playerName) - 1));
                    //perms.playerAddGroup(playerName, "world", Settings.mVIPGroup.get(getLvl(playerName)));
                    System.out.print("move player to " + Settings.mVIPGroup.get(getLvl(playerName) - 1));
                    break;
                }
                case yVIP: {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                            "manuadd " + playerName + " " + Settings.yVIPGroup.get(getLvl(playerName) - 1));
                    //perms.playerAddGroup(playerName, "world", Settings.yVIPGroup.get(getLvl(playerName)));
                    System.out.print("move player to " + Settings.yVIPGroup.get(getLvl(playerName) - 1));
                    break;
                }
                case NONE: {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                            "manuadd " + playerName + " " + Settings.defaultgroup);
                    //perms.playerAddGroup(playerName, "world", Settings.defaultgroup);
                    break;
                }
            }
        }
    }

    public static int getLvl(String playerName) {
        int points = getPoints(playerName);
        if (points == 0)
            return 0;
        int lvl = -1;
        int maxLvl = Settings.level.size();

        if (points > Settings.level.get(maxLvl - 1))
            return maxLvl;

        int pointReq = Settings.level.get(0);
        while (++lvl < maxLvl && points >= pointReq) {
            pointReq = Settings.level.get(lvl + 1);
        }
        return lvl;
    }

    public static int getLvlReqPoints(String playerName) {
        int nextLvlPoints = getNextLvlPoints(playerName);
        if (nextLvlPoints == -1)
            return 0;
        return nextLvlPoints - getPoints(playerName);
    }

    public static int getNextLvlPoints(String playerName) {
        int lvl = getLvl(playerName);
        if (Settings.level.size() < lvl + 1)
            return -1;
        return Settings.level.get(lvl);
    }

    public static void setRemainDays(String playerName, int days) {
        try {
            ResultSet res = statement.executeQuery("SELECT * FROM vip WHERE player=" + "\"" + playerName + "\"" + ";");
            PreparedStatement statement;
            if (!res.next()) {
                statement = c.prepareStatement("INSERT INTO vip VALUES(?, ?, ?, ?, ?);");
                statement.setString(1, playerName);
                statement.setString(2, 0 + "");
                statement.setString(3, days + "");
                statement.setString(4, VIPType.NONE.toString());
                statement.setString(5, "0");
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
                statement = c.prepareStatement("INSERT INTO vip VALUES(?, ?, ?, ?, ?);");
                statement.setString(1, playerName);
                statement.setString(2, 0 + "");
                statement.setString(3, "0");
                statement.setString(4, type.toString());
                statement.setString(5, "0");
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
            res = statement.executeQuery("SELECT * FROM vip WHERE player=" + "\"" + playerName + "\"" + ";");
            if (!res.next())
                return VIPType.NONE;
            return VIPType.valueOf(res.getString("type"));
        } catch (SQLException e) {
            return VIPType.NONE;
        }
    }

    public static void setChecked(String playerName, boolean value) {
        try {
            ResultSet res = statement.executeQuery("SELECT * FROM vip WHERE player=" + "\"" + playerName + "\"" + ";");
            PreparedStatement statement;
            if (!res.next()) {
                return;
            } else {
                statement = c.prepareStatement("UPDATE vip SET checked=? WHERE player=?;");
                if (value)
                    statement.setString(1, "1");
                else
                    statement.setString(1, "0");
                statement.setString(2, playerName);
            }
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean ifChecked(String playerName) {
        ResultSet res;
        try {
            res = statement.executeQuery("SELECT * FROM vip WHERE player=" + "\"" + playerName + "\"" + ";");
            return res.next() && res.getBoolean("checked");
        } catch (SQLException e) {
            return false;
        }
    }

    public static void delete(String playerName) {
        try {
            PreparedStatement statement = c.prepareStatement("DELETE FROM vip WHERE player=?;");
            statement.setString(1, playerName);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
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
        } catch (SQLException ignored) {
        }
        return result;
    }


    public static List<String> getRank(int count) {
        ResultSet res;
        List<String> result = new ArrayList<>();
        int i = 0;
        try {
            res = statement.executeQuery("SELECT * FROM vip ORDER BY points desc;");
            while (res.next() && i < count) {
                result.add(res.getString("player"));
                i++;
            }
        } catch (SQLException ignored) {
        }
        return result;
    }
}

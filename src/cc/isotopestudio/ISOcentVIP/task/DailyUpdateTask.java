package cc.isotopestudio.ISOcentVIP.task;

import cc.isotopestudio.ISOcentVIP.data.PlayerData;
import cc.isotopestudio.ISOcentVIP.data.Settings;
import cc.isotopestudio.ISOcentVIP.type.VIPType;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import static cc.isotopestudio.ISOcentVIP.ISOcentVIP.statement;

/**
 * Created by Mars on 6/5/2016.
 * Copyright ISOTOPE Studio
 */
public class DailyUpdateTask extends BukkitRunnable {
    @Override
    public void run() {
        java.util.Date todayDate = new java.util.Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String today = format.format(todayDate);
        boolean isOutdated = false;
        ResultSet res;
        try {
            res = statement.executeQuery("SELECT * FROM info;");
            if (!res.next())
                isOutdated = true;
            else {
                java.util.Date dayDate = new java.util.Date((res.getDate("v").getTime()));
                String day = format.format(dayDate);
                if (!day.equals(today)) {
                    isOutdated = true;
                }
            }
        } catch (SQLException e) {
            isOutdated = true;
        }
        if (isOutdated) {
            try {
                statement.executeUpdate("DROP TABLE info;");
                statement.executeUpdate(
                        "CREATE TABLE info(" +
                                " k CHAR(20) NOT NULL PRIMARY KEY," +
                                " v DATE NOT NULL" +
                                " );");
                statement.executeUpdate(
                        "INSERT INTO info VALUES (\"today\",\"" + today + "\");");
                for (String player : PlayerData.getVIPs(VIPType.mVIP)) {
                    if (!PlayerData.ifChecked(player)) {
                        PlayerData.addPoints(player, Settings.mVIPPoints);
                        PlayerData.setChecked(player);
                    }
                    int days = PlayerData.getRemainDays(player);
                    days--;
                    if (days <= 0) {
                        PlayerData.setRemainDays(player, 0);
                        PlayerData.setVIPType(player, VIPType.NONE);
                    } else
                        PlayerData.setRemainDays(player, days);
                }
                for (String player : PlayerData.getVIPs(VIPType.yVIP)) {
                    if (!PlayerData.ifChecked(player)) {
                        PlayerData.addPoints(player, Settings.yVIPPoints);
                        PlayerData.setChecked(player);
                    }
                    int days = PlayerData.getRemainDays(player);
                    days--;
                    if (days <= 0) {
                        PlayerData.setRemainDays(player, 0);
                        PlayerData.setVIPType(player, VIPType.NONE);
                    } else
                        PlayerData.setRemainDays(player, days);
                }
                for (String player : PlayerData.getVIPs(VIPType.NONE)) {
                    PlayerData.addPoints(player, -Settings.decrease);
                    if (PlayerData.getPoints(player) < 0) {
                        PlayerData.setPoints(player, 0);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

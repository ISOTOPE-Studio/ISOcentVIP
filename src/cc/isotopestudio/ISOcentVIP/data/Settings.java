package cc.isotopestudio.ISOcentVIP.data;

import java.util.ArrayList;
import java.util.List;

import static cc.isotopestudio.ISOcentVIP.ISOcentVIP.plugin;

/**
 * Created by Mars on 5/27/2016.
 * Copyright ISOTOPE Studio
 */
public class Settings {

    public static int mVIPPrice;
    public static int mVIPPoints;
    public static int yVIPPrice;
    public static int yVIPPoints;
    public static int yVIPGift;
    public static int decrease;
    public static int pointsPrice;
    public static int points;
    public static String defaultgroup;
    public static List<Integer> level;
    public static List<String> mVIPGroup;
    public static List<String> yVIPGroup;

    public static String info() {
        return "Settings{" + "mVIPPrice=" + mVIPPrice +
                ", mVIPPoints=" + mVIPPoints +
                ", yVIPPrice=" + yVIPPrice +
                ", yVIPPoints=" + yVIPPoints +
                ", yVIPGift=" + yVIPGift +
                ", decrease=" + decrease +
                ", pointsPrice=" + pointsPrice +
                ", points=" + points +
                ", defaultgroup='" + defaultgroup + '\'' +
                ", level=" + level +
                ", mVIPGroup=" + mVIPGroup +
                ", yVIPGroup=" + yVIPGroup +
                '}';
    }

    public static void update() {
        mVIPPrice = plugin.getConfig().getInt("mvip.price", 10);
        yVIPPrice = plugin.getConfig().getInt("yvip.price", 100);
        mVIPPoints = plugin.getConfig().getInt("mvip.points", 5);
        yVIPPoints = plugin.getConfig().getInt("yvip.points", 10);
        yVIPGift = plugin.getConfig().getInt("yvip.gift", 50);
        decrease = plugin.getConfig().getInt("decrease", 2);
        pointsPrice = plugin.getConfig().getInt("pointsprice", 0);
        points = plugin.getConfig().getInt("points", 0);
        defaultgroup = plugin.getConfig().getString("defaultgroup");
        level = new ArrayList<>();
        mVIPGroup = plugin.getConfig().getStringList("mvip.group");
        yVIPGroup = plugin.getConfig().getStringList("yvip.group");
        for (String line : plugin.getConfig().getStringList("level")) {
            try {
                level.add(Integer.parseInt(line));
            } catch (NumberFormatException ignored) {
            }
        }

        System.out.print(info());
    }


}

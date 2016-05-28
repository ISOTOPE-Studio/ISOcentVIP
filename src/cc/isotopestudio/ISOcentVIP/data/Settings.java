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
    public static List<Integer> level;

    public static void update() {
        mVIPPrice = plugin.getConfig().getInt("mvip.price", 10);
        yVIPPrice = plugin.getConfig().getInt("yvip.price", 100);
        mVIPPoints = plugin.getConfig().getInt("mvip.points", 5);
        yVIPPoints = plugin.getConfig().getInt("yvip.points", 10);
        yVIPGift = plugin.getConfig().getInt("yvip.gift", 50);
        decrease = plugin.getConfig().getInt("decrease", 2);
        level = new ArrayList<>();
        level.add(0);
        for(String line:plugin.getConfig().getStringList("level")){
            try {
                level.add(Integer.parseInt(line));
            } catch (NumberFormatException ignored) {
            }
        }
    }
}

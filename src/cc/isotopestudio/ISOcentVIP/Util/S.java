package cc.isotopestudio.ISOcentVIP.util;

import cc.isotopestudio.ISOcentVIP.ISOcentVIP;
import org.bukkit.ChatColor;

public class S {

    public static String toGreen(String s) {
        return String.valueOf(ChatColor.GREEN) + s + ChatColor.RESET;
    }

    public static String toAqua(String s) {
        return String.valueOf(ChatColor.AQUA) + s + ChatColor.RESET;
    }

    public static String toGray(String s) {
        return String.valueOf(ChatColor.GRAY) + s + ChatColor.RESET;
    }

    public static String toGold(String s) {
        return String.valueOf(ChatColor.GOLD) + s + ChatColor.RESET;
    }

    public static String toPrefixRed(String s) {
        return ISOcentVIP.prefix + ChatColor.RED + s + ChatColor.RESET;
    }

    public static String toPrefixGreen(String s) {
        return ISOcentVIP.prefix + ChatColor.GREEN + s + ChatColor.RESET;
    }

    public static String toPrefixYellow(String s) {
        return ISOcentVIP.prefix + ChatColor.YELLOW + s + ChatColor.RESET;
    }
}

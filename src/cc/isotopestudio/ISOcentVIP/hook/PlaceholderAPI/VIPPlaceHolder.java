package cc.isotopestudio.ISOcentVIP.hook.PlaceholderAPI;

import cc.isotopestudio.ISOcentVIP.ISOcentVIP;
import cc.isotopestudio.ISOcentVIP.data.PlayerData;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;

/**
 * Created by Mars on 6/7/2016.
 * Copyright ISOTOPE Studio
 */
public class VIPPlaceHolder extends EZPlaceholderHook {

    public VIPPlaceHolder() {
        // this is the plugin that is registering the placeholder and the identifier for our placeholder.
        // the format for placeholders is this:
        // %<placeholder identifier>_<anything you define as an identifier in your method below>%
        // the placeholder identifier can be anything you want as long as it is not already taken by another
        // registered placeholder.
        super(ISOcentVIP.plugin, "vip");
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (player == null)
            return null;
        if (identifier.equals("points"))
            return String.valueOf(PlayerData.getPoints(player.getName()));
        if (identifier.equals("days"))
            return String.valueOf(PlayerData.getRemainDays(player.getName()));
        if (identifier.equals("expiredate"))
            return String.valueOf(PlayerData.getExpireDate(player.getName()));
        if (identifier.equals("level"))
            return String.valueOf(PlayerData.getLvl(player.getName()));
        if (identifier.equals("type"))
            return PlayerData.getVIPType(player.getName()).getName();
        if (identifier.equals("nextlvlpoints"))
            return String.valueOf(PlayerData.getNextLvlPoints(player.getName()));
        if (identifier.startsWith("rank")) {
            try {
                int count = Integer.parseInt(identifier.substring(4));
                return String.valueOf(PlayerData.getRank(count).get(count - 1));
            } catch (Exception e) {
                return "无法排名, 格式错误";
            }
        }
        return null;
    }
}

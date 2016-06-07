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
            return "" + PlayerData.getPoints(player.getName());
        if (identifier.equals("days"))
            return "" + PlayerData.getRemainDays(player.getName());
        if (identifier.equals("expiredate"))
            return "" + PlayerData.getExpireDate(player.getName());
        return null;
    }
}

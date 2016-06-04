package cc.isotopestudio.ISOcentVIP.command;

import cc.isotopestudio.ISOcentVIP.Util.S;
import cc.isotopestudio.ISOcentVIP.data.PlayerData;
import cc.isotopestudio.ISOcentVIP.data.Settings;
import cc.isotopestudio.ISOcentVIP.type.VIPType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static cc.isotopestudio.ISOcentVIP.ISOcentVIP.plugin;

/**
 * Created by Mars on 5/27/2016.
 * Copyright ISOTOPE Studio
 */
public class VIPCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("vip")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(S.toPrefixRed("必须要玩家才能执行"));
                return true;
            }
            /*
            if (!sender.hasPermission("vip.user")) {
                sender.sendMessage(S.toPrefixRed("你没有权限"));
                return true;
            }
            */
            Player player = (Player) sender;
            if (args.length < 1) {
                sendInfo(player, label);
                return true;
            }
            if (args[0].equalsIgnoreCase("rank")) {
                // TO-DO
                return true;
            }
            if (args[0].equalsIgnoreCase("buy")) {
                if (args.length > 1 && args[1].equals("month")) {
                    int month = 1;
                    if (args.length > 2) {
                        try {
                            month = Integer.parseInt(args[2]);
                        } catch (NumberFormatException ignored) {
                        }
                    }
                    if (plugin.playerPoints.getAPI().take(player.getName(), Settings.mVIPPrice)) {
                        PlayerData.addDays(player.getName(), 30 * month);
                        player.sendMessage(S.toPrefixGreen("成功购买"));
                    } else {
                        player.sendMessage(S.toPrefixGreen("余额不足"));
                    }
                    return true;
                }
                if (args.length > 1 && args[1].equals("year")) {
                    int year = 1;
                    if (args.length > 2) {
                        try {
                            year = Integer.parseInt(args[2]);
                        } catch (NumberFormatException ignored) {
                        }
                    }
                    if (plugin.playerPoints.getAPI().take(player.getName(), Settings.yVIPPrice)) {
                        PlayerData.addDays(player.getName(), 365 * year);
                        player.sendMessage(S.toPrefixGreen("成功购买"));
                    } else {
                        player.sendMessage(S.toPrefixRed("余额不足"));
                    }
                    return true;
                }
                player.sendMessage(S.toBoldGreen("/" + label + " buy month [月数]") + S.toGray(" - ") + S.toGold("设置玩家成长值"));
                player.sendMessage(S.toBoldGreen("/" + label + " buy year [年数]") + S.toGray(" - ") + S.toGold("设置玩家成长值"));
                return true;
            }
            sendInfo(player, label);
            return true;
        }
        return false;
    }

    private void sendInfo(Player player, String label) {
        player.sendMessage(S.toPrefixGreen("VIP帮助菜单 第 1 页"));
        player.sendMessage(S.toAqua("VIP: " + PlayerData.getVIPType(player.getName()).getName()));
        player.sendMessage(S.toAqua("成长值: " + PlayerData.getPoints(player.getName())));
        player.sendMessage(S.toAqua("等级: " + PlayerData.getLvl(player.getName())));
        if (PlayerData.getVIPType(player.getName()) != VIPType.NONE) {
            player.sendMessage(S.toAqua("过期: " + PlayerData.getExpireDate(player.getName())));
            player.sendMessage(S.toAqua("剩余天数: " + PlayerData.getRemainDays(player.getName())));
        }
        player.sendMessage(VIPType.yVIP.getName() + S.toGreen("(365天)价格: " + Settings.yVIPPrice));
        player.sendMessage(VIPType.mVIP.getName() + S.toGreen("(30)价格: " + Settings.mVIPPrice));
        player.sendMessage(S.toBoldGreen("/" + label + " buy month [月数]") + S.toGray(" - ") + S.toGold("设置玩家成长值"));
        player.sendMessage(S.toBoldGreen("/" + label + " buy year [年数]") + S.toGray(" - ") + S.toGold("设置玩家成长值"));
    }
}
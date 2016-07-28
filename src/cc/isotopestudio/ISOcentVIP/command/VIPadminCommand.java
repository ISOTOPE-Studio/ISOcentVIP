package cc.isotopestudio.ISOcentVIP.command;

import cc.isotopestudio.ISOcentVIP.util.S;
import cc.isotopestudio.ISOcentVIP.data.PlayerData;
import cc.isotopestudio.ISOcentVIP.data.Settings;
import cc.isotopestudio.ISOcentVIP.type.VIPType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Mars on 5/27/2016.
 * Copyright ISOTOPE Studio
 */
public class VIPadminCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("vipadmin")) {
            /* if (!(sender instanceof Player)) {
                sender.sendMessage(S.toPrefixRed("必须要玩家才能执行"));
                return true;
            }*/
            if (!sender.hasPermission("vip.admin")) {
                sender.sendMessage(S.toPrefixRed("你没有权限"));
                return true;
            }
            if (args.length < 1) {
                sendHelpPage1(sender, label);
                return true;
            }
            if (args[0].equalsIgnoreCase("info") && args.length > 1) {
                sender.sendMessage(S.toPrefixGreen("玩家 " + args[1] + " 信息"));
                sender.sendMessage(S.toAqua("成长值: " + PlayerData.getPoints(args[1])));
                sender.sendMessage(S.toAqua("VIP类型: " + PlayerData.getVIPType(args[1]).getName()));
                sender.sendMessage(S.toAqua("等级: " + PlayerData.getLvl(args[1])));
                if (PlayerData.getVIPType(args[1]) != VIPType.NONE) {
                    sender.sendMessage(S.toAqua("过期: " + PlayerData.getExpireDate(args[1])));
                    sender.sendMessage(S.toAqua("剩余天数: " + PlayerData.getRemainDays(args[1])));
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("adddays") && args.length > 2) {
                int points = Integer.parseInt(args[2]);
                PlayerData.addDays(args[1], points);
                return true;
            }
            if (args[0].equalsIgnoreCase("setpoints") && args.length > 2) {
                int points = Integer.parseInt(args[2]);
                PlayerData.setPoints(args[1], points);
                return true;
            }
            if (args[0].equalsIgnoreCase("info")) {
                sender.sendMessage(Settings.info());
                return true;
            }
            sendHelpPage1(sender, label);
            return true;
        }
        return false;
    }

    private void sendHelpPage1(CommandSender player, String label) {
        player.sendMessage(S.toPrefixGreen("帮助菜单 第 1 页"));
        player.sendMessage(S.toGreen("/" + label + " info <玩家名字>") + S.toGray(" - ") + S.toGold("查看玩家信息"));
        player.sendMessage(S.toGreen("/" + label + " adddays <玩家名字> <天数>") + S.toGray(" - ") + S.toGold("设置玩家成长值"));
        player.sendMessage(S.toGreen("/" + label + " setpoints <玩家名字> <成长点>") + S.toGray(" - ") + S.toGold("设置玩家成长值"));
    }
}
package cc.isotopestudio.ISOcentVIP.command;

import cc.isotopestudio.ISOcentVIP.Util.S;
import cc.isotopestudio.ISOcentVIP.data.PlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
                sender.sendMessage(S.toAqua("剩余天数: " + PlayerData.getRemainDays(args[1])));
                return true;
            }
            if (args[0].equalsIgnoreCase("set") && args.length > 2) {
                int points = Integer.parseInt(args[2]);
                PlayerData.setPoints(args[1], points);
                return true;
            }
            sendHelpPage1(sender, label);
        }
        return false;
    }

    private void sendHelpPage1(CommandSender player, String label) {
        player.sendMessage(S.toPrefixGreen("帮助菜单 第 1 页"));
        player.sendMessage(S.toBoldGreen("/" + label + " info <玩家名字>") + S.toGray(" - ") + S.toGold("查看玩家信息"));
        player.sendMessage(S.toBoldGreen("/" + label + " set <玩家名字> <成长点>") + S.toGray(" - ") + S.toGold("设置玩家成长值"));
    }
}
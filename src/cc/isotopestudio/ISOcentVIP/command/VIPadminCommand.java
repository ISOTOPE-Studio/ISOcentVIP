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
                sender.sendMessage(S.toPrefixRed("����Ҫ��Ҳ���ִ��"));
                return true;
            }*/
            if (!sender.hasPermission("vip.admin")) {
                sender.sendMessage(S.toPrefixRed("��û��Ȩ��"));
                return true;
            }
            if (args.length < 1) {
                sendHelpPage1(sender, label);
                return true;
            }
            if (args[0].equalsIgnoreCase("info") && args.length > 1) {
                sender.sendMessage(S.toPrefixGreen("��� " + args[1] + " ��Ϣ"));
                sender.sendMessage(S.toAqua("�ɳ�ֵ: " + PlayerData.getPoints(args[1])));
                sender.sendMessage(S.toAqua("ʣ������: " + PlayerData.getRemainDays(args[1])));
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
        player.sendMessage(S.toPrefixGreen("�����˵� �� 1 ҳ"));
        player.sendMessage(S.toBoldGreen("/" + label + " info <�������>") + S.toGray(" - ") + S.toGold("�鿴�����Ϣ"));
        player.sendMessage(S.toBoldGreen("/" + label + " set <�������> <�ɳ���>") + S.toGray(" - ") + S.toGold("������ҳɳ�ֵ"));
    }
}
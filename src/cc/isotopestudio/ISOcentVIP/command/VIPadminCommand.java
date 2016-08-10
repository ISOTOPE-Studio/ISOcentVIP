package cc.isotopestudio.ISOcentVIP.command;

import cc.isotopestudio.ISOcentVIP.data.PlayerData;
import cc.isotopestudio.ISOcentVIP.data.Settings;
import cc.isotopestudio.ISOcentVIP.type.VIPType;
import cc.isotopestudio.ISOcentVIP.util.S;
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
                sender.sendMessage(S.toRed("����Ҫ��Ҳ���ִ��"));
                return true;
            }*/
            if (!sender.hasPermission("vip.admin")) {
                sender.sendMessage(S.toRed("��û��Ȩ��"));
                return true;
            }
            if (args.length < 1) {
                sendHelpPage1(sender, label);
                return true;
            }
            if (args[0].equalsIgnoreCase("info") && args.length > 1) {
                sender.sendMessage(S.toGreen("��� " + args[1] + " ��Ϣ"));
                sender.sendMessage(S.toAqua("�ɳ�ֵ: " + PlayerData.getPoints(args[1])));
                sender.sendMessage(S.toAqua("VIP����: " + PlayerData.getVIPType(args[1]).getName()));
                sender.sendMessage(S.toAqua("�ȼ�: " + PlayerData.getLvl(args[1])));
                if (PlayerData.getVIPType(args[1]) != VIPType.NONE) {
                    sender.sendMessage(S.toAqua("����: " + PlayerData.getExpireDate(args[1])));
                    sender.sendMessage(S.toAqua("ʣ������: " + PlayerData.getRemainDays(args[1])));
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("adddays") && args.length > 2) {
                int points = Integer.parseInt(args[2]);
                PlayerData.addDays(args[1], points);
                switch (PlayerData.getVIPType(args[1])) {
                    case mVIP:
                        if (!PlayerData.ifChecked(args[1])) {
                            PlayerData.addPoints(args[1], Settings.mVIPPoints);
                            PlayerData.setChecked(args[1]);
                        }
                        break;
                    case yVIP:
                        if (!PlayerData.ifChecked(args[1])) {
                            PlayerData.addPoints(args[1], Settings.yVIPPoints);
                            PlayerData.setChecked(args[1]);
                        }
                        break;
                    case NONE:
                        break;
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("addpoints") && args.length > 2) {
                int points = Integer.parseInt(args[2]);
                PlayerData.addPoints(args[1], points);
                return true;
            }
            if (args[0].equalsIgnoreCase("info")) {
                sender.sendMessage(Settings.info());
                return true;
            }
            if (args[0].equalsIgnoreCase("delete")) {
                PlayerData.delete(args[1]);
                sender.sendMessage(S.toRed("�ɹ�ɾ��"));
                return true;
            }
            sendHelpPage1(sender, label);
            return true;
        }
        return false;
    }

    private void sendHelpPage1(CommandSender player, String label) {
        player.sendMessage(S.toGreen("�����˵� �� 1 ҳ"));
        player.sendMessage(S.toGreen("/" + label + " info <�������>") + S.toGray(" - ") + S.toGold("�鿴�����Ϣ"));
        player.sendMessage(S.toGreen("/" + label + " adddays <�������> <����>") + S.toGray(" - ") + S.toGold("������ʣ������"));
        player.sendMessage(S.toGreen("/" + label + " addpoints <�������> <�ɳ���>") + S.toGray(" - ") + S.toGold("�����ҳɳ�ֵ"));
        player.sendMessage(S.toGreen("/" + label + " delete <�������>") + S.toGray(" - ") + S.toGold("����������"));
    }
}
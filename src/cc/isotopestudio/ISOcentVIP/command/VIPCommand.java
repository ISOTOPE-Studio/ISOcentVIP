package cc.isotopestudio.ISOcentVIP.command;

import cc.isotopestudio.ISOcentVIP.data.PlayerData;
import cc.isotopestudio.ISOcentVIP.data.Settings;
import cc.isotopestudio.ISOcentVIP.type.VIPType;
import cc.isotopestudio.ISOcentVIP.util.S;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

import static cc.isotopestudio.ISOcentVIP.ISOcentVIP.plugin;

/**
 * Created by Mars on 5/27/2016.
 * Copyright ISOTOPE Studio
 */
public class VIPCommand implements CommandExecutor {
    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("vip")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(S.toRed("����Ҫ��Ҳ���ִ��"));
                return true;
            }
            /*
            if (!sender.hasPermission("vip.user")) {
                sender.sendMessage(S.toRed("��û��Ȩ��"));
                return true;
            }

            */
            Player player = (Player) sender;
            if (args.length < 1) {
                sendInfo(player);
                return true;
            }
            if (args[0].equalsIgnoreCase("help")) {
                sendHelp(player, label);
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
                        if (PlayerData.addDays(player.getName(), 30 * month)) {
                            player.sendMessage(S.toGreen("�������VIP"));
                            player.sendMessage(S.toGreen("���" + Settings.yVIPGift + "�ɳ�ֵ����"));
                        }
                        player.sendMessage(S.toGreen("�ɹ�����"));
                    } else {
                        player.sendMessage(S.toGreen("����"));
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
                        player.sendMessage(S.toGreen("���" + Settings.yVIPGift + "�ɳ�ֵ����"));
                        player.sendMessage(S.toGreen("�ɹ�����"));
                    } else {
                        player.sendMessage(S.toRed("����"));
                    }
                    return true;
                }
                if (args.length > 1 && args[1].equals("point")) {
                    int a = 1;
                    if (args.length > 2) {
                        try {
                            a = Integer.parseInt(args[2]);
                        } catch (NumberFormatException ignored) {
                        }
                    }
                    if (plugin.playerPoints.getAPI().take(player.getName(), a * Settings.pointsPrice)) {
                        PlayerData.addPoints(player.getName(), a * Settings.points);
                        player.sendMessage(S.toGreen("�ɹ����� " + a * Settings.points + " �ɳ�ֵ"));
                    } else {
                        player.sendMessage(S.toRed("����"));
                    }
                    return true;
                }
                player.sendMessage(S.toGreen("/" + label + " buy month [����]") + S.toGray(" - ") + S.toGold("�����¸�VIP"));
                player.sendMessage(S.toGreen("/" + label + " buy year [����]") + S.toGray(" - ") + S.toGold("�����긶VIP"));
                player.sendMessage(S.toGreen("/" + label + " buy point") + S.toGray(" - ") + S.toGold("����ɳ�ֵ"));
                player.sendMessage(S.toGreen("/" + label + " rank") + S.toGray(" - ") + S.toGold("�鿴����"));
                return true;
            }
            if (args[0].equals("rank")) {
                List<String> result = PlayerData.getRank(10);
                player.sendMessage(S.toYellow("�ɳ�ֵ����"));
                for (int i = 0; i < 10; i++) {
                    if (result.size() <= i) break;
                    player.sendMessage(S.toGreen(result.get(i) + ": "
                            + PlayerData.getPoints(result.get(i)) + "��"
                            + "�� lvl " + PlayerData.getLvl(result.get(i))));
                }
                return true;
            }
            sendInfo(player);
            return true;
        }
        return false;
    }

    private void sendHelp(Player player, String label) {
        player.sendMessage(S.toGreen("VIP�����˵�"));
        player.sendMessage(VIPType.yVIP.getName() + S.toGreen("(365��)�۸�: " + Settings.yVIPPrice));
        player.sendMessage(VIPType.mVIP.getName() + S.toGreen("(30)�۸�: " + Settings.mVIPPrice));
        player.sendMessage(S.toGreen("�ɳ�ֵ(" + Settings.points + ")�۸�: " + Settings.pointsPrice));
        player.sendMessage(S.toGreen("/" + label + " buy month [����]") + S.toGray(" - ") + S.toGold("������ҳɳ�ֵ"));
        player.sendMessage(S.toGreen("/" + label + " buy year [����]") + S.toGray(" - ") + S.toGold("������ҳɳ�ֵ"));
        player.sendMessage(S.toGreen("/" + label + " buy point <����>") + S.toGray(" - ") + S.toGold("����ɳ�ֵ(1��" + Settings.points + "��)"));
        player.sendMessage(S.toGreen("/" + label + " rank") + S.toGray(" - ") + S.toGold("�鿴����"));

    }

    private void sendInfo(Player player) {
        player.sendMessage(S.toAqua("VIP: " + PlayerData.getVIPType(player.getName()).getName()));
        player.sendMessage(S.toAqua("�ɳ�ֵ: " + PlayerData.getPoints(player.getName())));
        player.sendMessage(S.toAqua("�ȼ�: " + PlayerData.getLvl(player.getName())));
        if (PlayerData.getVIPType(player.getName()) != VIPType.NONE) {
            player.sendMessage(S.toAqua("����: " + PlayerData.getExpireDate(player.getName())));
            player.sendMessage(S.toAqua("ʣ������: " + PlayerData.getRemainDays(player.getName())));
            player.sendMessage(S.toAqua("��������: " + PlayerData.getLvlReqPoints(player.getName())));
        }
        player.sendMessage(S.toGreen("���� /vip help �鿴�����˵�"));
    }
}
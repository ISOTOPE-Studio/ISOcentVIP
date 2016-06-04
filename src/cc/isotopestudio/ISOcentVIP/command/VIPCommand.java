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
                sender.sendMessage(S.toPrefixRed("����Ҫ��Ҳ���ִ��"));
                return true;
            }
            /*
            if (!sender.hasPermission("vip.user")) {
                sender.sendMessage(S.toPrefixRed("��û��Ȩ��"));
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
                        player.sendMessage(S.toPrefixGreen("�ɹ�����"));
                    } else {
                        player.sendMessage(S.toPrefixGreen("����"));
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
                        player.sendMessage(S.toPrefixGreen("�ɹ�����"));
                    } else {
                        player.sendMessage(S.toPrefixRed("����"));
                    }
                    return true;
                }
                player.sendMessage(S.toBoldGreen("/" + label + " buy month [����]") + S.toGray(" - ") + S.toGold("������ҳɳ�ֵ"));
                player.sendMessage(S.toBoldGreen("/" + label + " buy year [����]") + S.toGray(" - ") + S.toGold("������ҳɳ�ֵ"));
                return true;
            }
            sendInfo(player, label);
            return true;
        }
        return false;
    }

    private void sendInfo(Player player, String label) {
        player.sendMessage(S.toPrefixGreen("VIP�����˵� �� 1 ҳ"));
        player.sendMessage(S.toAqua("VIP: " + PlayerData.getVIPType(player.getName()).getName()));
        player.sendMessage(S.toAqua("�ɳ�ֵ: " + PlayerData.getPoints(player.getName())));
        player.sendMessage(S.toAqua("�ȼ�: " + PlayerData.getLvl(player.getName())));
        if (PlayerData.getVIPType(player.getName()) != VIPType.NONE) {
            player.sendMessage(S.toAqua("����: " + PlayerData.getExpireDate(player.getName())));
            player.sendMessage(S.toAqua("ʣ������: " + PlayerData.getRemainDays(player.getName())));
        }
        player.sendMessage(VIPType.yVIP.getName() + S.toGreen("(365��)�۸�: " + Settings.yVIPPrice));
        player.sendMessage(VIPType.mVIP.getName() + S.toGreen("(30)�۸�: " + Settings.mVIPPrice));
        player.sendMessage(S.toBoldGreen("/" + label + " buy month [����]") + S.toGray(" - ") + S.toGold("������ҳɳ�ֵ"));
        player.sendMessage(S.toBoldGreen("/" + label + " buy year [����]") + S.toGray(" - ") + S.toGold("������ҳɳ�ֵ"));
    }
}
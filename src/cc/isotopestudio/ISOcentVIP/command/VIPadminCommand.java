package cc.isotopestudio.ISOcentVIP.command;

import cc.isotopestudio.ISOcentVIP.Util.S;
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
            if (!(sender instanceof Player)) {
                sender.sendMessage(S.toPrefixRed("必须要玩家才能执行"));
                return true;
            }
            Player player = (Player) sender;
            if (!player.hasPermission("crack.admin")) {
                sender.sendMessage(S.toPrefixRed("你没有权限"));
                return true;
            }

        }
        return false;
    }
}
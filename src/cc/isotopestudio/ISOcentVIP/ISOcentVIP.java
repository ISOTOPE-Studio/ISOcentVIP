package cc.isotopestudio.ISOcentVIP;

import cc.isotopestudio.ISOcentVIP.command.VIPCommand;
import cc.isotopestudio.ISOcentVIP.command.VIPadminCommand;
import cc.isotopestudio.ISOcentVIP.data.Settings;
import cc.isotopestudio.ISOcentVIP.sql.MySQL;
import cc.isotopestudio.ISOcentVIP.sql.SqlManager;
import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;


/**
 * Created by Mars on 5/27/2016.
 * Copyright ISOTOPE Studio
 */
public class ISOcentVIP extends JavaPlugin {
    public static final String prefix = (new StringBuilder()).append(ChatColor.GOLD).append(ChatColor.BOLD).append("[")
            .append("VIP").append("]").append(ChatColor.GREEN).toString();
    public static final String pluginName = "ISOcentVIP";
    public static ISOcentVIP plugin;


    // mySQL
    public static MySQL mySQL;
    public static Connection c;
    public static Statement statement;

    @Override
    public void onEnable() {
        plugin = this;
        if (!hookPlayerPoints()) {
            getLogger().info("PlayerPoints �������");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        if (!SqlManager.connectMySQL()) {
            getLogger().severe(pluginName + "�޷�����!");
            getLogger().severe("���ݿ��޷����ӣ�");
            this.getPluginLoader().disablePlugin(this);
        }
        if (!SqlManager.createTables()) {
            getLogger().severe(pluginName + "�޷�����!");
            getLogger().severe("���ݿⴴ��ʧ�ܣ�");
            this.getPluginLoader().disablePlugin(this);
        }

        getLogger().info("�����ļ�...");
        plugin = this;
        File file;
        file = new File(getDataFolder(), "config.yml");
        if (!file.exists()) {
            saveDefaultConfig();
        }
        try {
            getPlayerData().save(playerDataFile);
        } catch (IOException e) {
            getLogger().info("�ļ�����");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        Settings.update();

        this.getCommand("vipadmin").setExecutor(new VIPadminCommand());
        this.getCommand("vip").setExecutor(new VIPCommand());

        getLogger().info(pluginName + "�ɹ�����!");
        getLogger().info(pluginName + "��ISOTOPE Studio����!");
        getLogger().info("http://isotopestudio.cc");
    }

    @Override
    public void onDisable() {
        getLogger().info(pluginName + "�ɹ�ж��!");
    }

    // APIs
    public PlayerPoints playerPoints;

    private boolean hookPlayerPoints() {
        final Plugin plugin = this.getServer().getPluginManager().getPlugin("PlayerPoints");
        playerPoints = PlayerPoints.class.cast(plugin);
        return playerPoints != null;
    }

    public PlayerPoints getPlayerPoints() {
        return playerPoints;
    }

    // Files

    private File playerDataFile = null;
    private FileConfiguration playerData = null;

    public void reloadPlayerData() {
        if (playerDataFile == null) {
            playerDataFile = new File(getDataFolder(), "players.yml");
        }
        playerData = YamlConfiguration.loadConfiguration(playerDataFile);
    }

    public FileConfiguration getPlayerData() {
        if (playerData == null) {
            reloadPlayerData();
        }
        return playerData;
    }

    public void savePlayerData() {
        if (playerData == null || playerDataFile == null) {
            return;
        }
        try {
            getPlayerData().save(playerDataFile);
        } catch (IOException ex) {
            getLogger().info("����ļ�����ʧ�ܣ�");
        }
    }
}

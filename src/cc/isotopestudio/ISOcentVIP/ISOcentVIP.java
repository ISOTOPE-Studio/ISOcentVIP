package cc.isotopestudio.ISOcentVIP;

import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;


/**
 * Created by Mars on 5/27/2016.
 * Copyright ISOTOPE Studio
 */
public class ISOcentVIP extends JavaPlugin {
    public static final String prefix = (new StringBuilder()).append(ChatColor.GOLD).append(ChatColor.BOLD).append("[")
            .append("副本房间").append("]").append(ChatColor.GREEN).toString();
    private static final String pluginName = "ISOcentVIP";
    public static ISOcentVIP plugin;

    @Override
    public void onEnable() {
        if (!hookPlayerPoints()) {
            getLogger().info( "PlayerPoints 插件出错！");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        getLogger().info("加载文件...");
        plugin = this;
        File file;
        file = new File(getDataFolder(), "config.yml");
        if (!file.exists()) {
            saveDefaultConfig();
        }
        try {
            getPlayerData().save(playerDataFile);
        } catch (IOException e) {
            getLogger().info("文件出错！");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getLogger().info(pluginName + "成功加载!");
        getLogger().info(pluginName + "由ISOTOPE Studio制作!");
        getLogger().info("http://isotopestudio.cc");
    }

    @Override
    public void onDisable() {
        getLogger().info(pluginName + "成功卸载!");
    }

    // APIs
    private PlayerPoints playerPoints;

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
            getLogger().info("玩家文件保存失败！");
        }
    }
}

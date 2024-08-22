package me.legit.survival.Utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

    private static Map<String, FileConfiguration> configs = new HashMap<>();

    public static void loadAllConfigs(JavaPlugin plugin) {
        loadConfig(plugin, "config.yml");
        loadConfig(plugin, "scoreboard.yml");
    }

    public static void saveAllConfigs() {
        for (Map.Entry<String, FileConfiguration> entry : configs.entrySet()) {
            saveConfig(entry.getKey(), entry.getValue());
        }
    }

    public static void reloadAllConfigs(JavaPlugin plugin) {
        configs.clear();
        loadAllConfigs(plugin);
    }

    public static FileConfiguration getConfig(String fileName) {
        return configs.get(fileName);
    }

    private static void loadConfig(JavaPlugin plugin, String fileName) {
        File configFile = new File(plugin.getDataFolder(), fileName);
        if (!configFile.exists()) {
            plugin.saveResource(fileName, false);
        }
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        configs.put(fileName, config);
    }

    private static void saveConfig(String fileName, FileConfiguration config) {
        File configFile = new File("plugins/Survival Core", fileName);
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getErrorPrefix(){
        return getConfig("en.yml").getString("error-prefix");
    }

    public static String getMainPrefix(){
        return getConfig("en.yml").getString("main-prefix");
    }

    public static String getNoPermission(){
        return getConfig("en.yml").getString("no-permission");
    }

    public static String getConsoleNoPermission(){
        return getConfig("en.yml").getString("console-no-permission");
    }
}
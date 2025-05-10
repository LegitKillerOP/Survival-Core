package me.legit.survival.Utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

    private static final Map<String, FileConfiguration> configs = new HashMap<>();
    private static JavaPlugin plugin;

    public static void loadAllConfigs(JavaPlugin pluginInstance) {
        plugin = pluginInstance;
        loadConfig("config.yml");
        loadConfig("scoreboard.yml");
        loadConfig("en.yml");
        loadConfig("homes.yml");
        loadConfig("warps.yml");
    }

    public static void saveAllConfigs() {
        for (Map.Entry<String, FileConfiguration> entry : configs.entrySet()) {
            saveConfig(entry.getKey(), entry.getValue());
        }
    }

    public static void reloadAllConfigs() {
        configs.clear();
        loadAllConfigs(plugin);
    }

    public static FileConfiguration getConfig(String fileName) {
        return configs.get(fileName);
    }

    private static void loadConfig(String fileName) {
        File configFile = new File(plugin.getDataFolder(), fileName);
        if (!configFile.exists()) {
            plugin.saveResource(fileName, false);
        }
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        configs.put(fileName, config);
    }

    private static void saveConfig(String fileName, FileConfiguration config) {
        File configFile = new File(plugin.getDataFolder(), fileName); // ✅ Correctly inside plugin folder
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Utility methods with null safety

    public static String getErrorPrefix() {
        FileConfiguration config = getConfig("en.yml");
        if (config == null) return "[Error] ";
        return config.getString("error-prefix", "[Error] ");
    }

    public static String getMainPrefix() {
        FileConfiguration config = getConfig("en.yml");
        if (config == null) return "[Survival] ";
        return config.getString("main-prefix", "[Survival] ");
    }

    public static String getNoPermission() {
        FileConfiguration config = getConfig("en.yml");
        if (config == null) return "You do not have permission.";
        return config.getString("no-permission", "You do not have permission.");
    }

    public static String getConsoleNoPermission() {
        FileConfiguration config = getConfig("en.yml");
        if (config == null) return "Only players can execute this command.";
        return config.getString("console-no-permission", "Only players can execute this command.");
    }
}

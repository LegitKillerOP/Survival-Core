package me.legit.survival.Utils;

import me.legit.survival.Survival;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class WarpManager {

    private static final FileConfiguration warpsConfig = ConfigManager.getConfig("warps.yml");

    public static void setWarp(String name, Location loc) {
        String path = "warps." + name.toLowerCase();
        warpsConfig.set(path + ".world", loc.getWorld().getName());
        warpsConfig.set(path + ".x", loc.getX());
        warpsConfig.set(path + ".y", loc.getY());
        warpsConfig.set(path + ".z", loc.getZ());
        warpsConfig.set(path + ".yaw", loc.getYaw());
        warpsConfig.set(path + ".pitch", loc.getPitch());
        ConfigManager.saveAllConfigs();
    }

    public static Location getWarp(String name) {
        String path = "warps." + name.toLowerCase();
        if (!warpsConfig.contains(path)) {
            return null;
        }
        try {
            return new Location(
                    Bukkit.getWorld(warpsConfig.getString(path + ".world")),
                    warpsConfig.getDouble(path + ".x"),
                    warpsConfig.getDouble(path + ".y"),
                    warpsConfig.getDouble(path + ".z"),
                    (float) warpsConfig.getDouble(path + ".yaw"),
                    (float) warpsConfig.getDouble(path + ".pitch")
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void deleteWarp(String name) {
        warpsConfig.set("warps." + name.toLowerCase(), null);
        ConfigManager.saveAllConfigs();
    }
}

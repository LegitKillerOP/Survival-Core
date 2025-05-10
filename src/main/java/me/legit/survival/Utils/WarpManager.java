package me.legit.survival.Utils;

import me.legit.survival.Survival;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WarpManager {

    private static final Map<String, Location> warps = new HashMap<>();
    private static final Map<String, String> warpPermissions = new HashMap<>();

    private static FileConfiguration config;

    public static void loadWarps() {
        config = ConfigManager.getConfig("warps.yml");
        if (config == null) {
            Survival.getPlugin().saveResource("warps.yml", false);
            config = ConfigManager.getConfig("warps.yml");
        }

        warps.clear();
        warpPermissions.clear();

        if (config.getConfigurationSection("warps") != null) {
            for (String warpName : config.getConfigurationSection("warps").getKeys(false)) {
                Location loc = getLocationFromConfig("warps." + warpName + ".location");
                if (loc != null) {
                    warps.put(warpName, loc);
                    if (config.contains("warps." + warpName + ".permission")) {
                        warpPermissions.put(warpName, config.getString("warps." + warpName + ".permission"));
                    }
                }
            }
        }
    }

    public static void saveWarps() {
        if (config == null) return;

        config.set("warps", null); // Clear old warps
        for (Map.Entry<String, Location> entry : warps.entrySet()) {
            String warpName = entry.getKey();
            Location loc = entry.getValue();
            saveLocationToConfig("warps." + warpName + ".location", loc);

            if (warpPermissions.containsKey(warpName)) {
                config.set("warps." + warpName + ".permission", warpPermissions.get(warpName));
            }
        }
        ConfigManager.saveAllConfigs();
    }

    public static void setWarp(String name, Location loc, String permission) {
        warps.put(name, loc);
        if (permission != null && !permission.isEmpty()) {
            warpPermissions.put(name, permission);
        }
        saveWarps();
    }

    public static void removeWarp(String name) {
        warps.remove(name);
        warpPermissions.remove(name);
        saveWarps();
    }

    public static Location getWarp(String name) {
        return warps.get(name);
    }

    public static String getWarpPermission(String name) {
        return warpPermissions.get(name);
    }

    public static Set<String> getAllWarpNames() {
        return warps.keySet();
    }

    // --- Internal Utilities ---

    private static Location getLocationFromConfig(String path) {
        if (!config.contains(path + ".world")) return null;

        String world = config.getString(path + ".world");
        double x = config.getDouble(path + ".x");
        double y = config.getDouble(path + ".y");
        double z = config.getDouble(path + ".z");
        float yaw = (float) config.getDouble(path + ".yaw");
        float pitch = (float) config.getDouble(path + ".pitch");

        if (Bukkit.getWorld(world) == null) return null;

        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }

    private static void saveLocationToConfig(String path, Location loc) {
        config.set(path + ".world", loc.getWorld().getName());
        config.set(path + ".x", loc.getX());
        config.set(path + ".y", loc.getY());
        config.set(path + ".z", loc.getZ());
        config.set(path + ".yaw", loc.getYaw());
        config.set(path + ".pitch", loc.getPitch());
    }
}

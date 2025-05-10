package me.legit.survival.Utils;

import me.legit.survival.Survival;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class HomeManager {

    private static final FileConfiguration homesConfig = ConfigManager.getConfig("homes.yml");

    public static void setHome(Player player) {
        Location loc = player.getLocation();
        String path = "homes." + player.getUniqueId();
        homesConfig.set(path + ".world", loc.getWorld().getName());
        homesConfig.set(path + ".x", loc.getX());
        homesConfig.set(path + ".y", loc.getY());
        homesConfig.set(path + ".z", loc.getZ());
        homesConfig.set(path + ".yaw", loc.getYaw());
        homesConfig.set(path + ".pitch", loc.getPitch());
        ConfigManager.saveAllConfigs();
    }

    public static Location getHome(Player player) {
        String path = "homes." + player.getUniqueId();
        if (!homesConfig.contains(path)) {
            return null;
        }
        try {
            return new Location(
                    Survival.getPlugin().getServer().getWorld(homesConfig.getString(path + ".world")),
                    homesConfig.getDouble(path + ".x"),
                    homesConfig.getDouble(path + ".y"),
                    homesConfig.getDouble(path + ".z"),
                    (float) homesConfig.getDouble(path + ".yaw"),
                    (float) homesConfig.getDouble(path + ".pitch")
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void deleteHome(Player player) {
        String path = "homes." + player.getUniqueId();
        homesConfig.set(path, null);
        ConfigManager.saveAllConfigs();
    }
}

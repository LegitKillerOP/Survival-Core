package me.legit.survival.Commands;

import me.legit.survival.Survival;
import me.legit.survival.Utils.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    private final Survival plugin;

    public SpawnCommand(Survival plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ConfigManager.getConsoleNoPermission());
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("rank.default")) {
            sender.sendMessage(ConfigManager.getNoPermission());
            return true;
        }

        boolean spawnAtLobby = plugin.getConfig().getBoolean("spawn-at-lobby", true);

        if (spawnAtLobby) {
            if (!plugin.getConfig().contains("hub.world")) {
                player.sendMessage(Survival.getPlugin().colorize(ConfigManager.getErrorPrefix() + "&cThe lobby location is not set."));
                return true;
            }

            Location lobbyLocation = new Location(
                    Bukkit.getWorld(plugin.getConfig().getString("hub.world")),
                    plugin.getConfig().getDouble("hub.x"),
                    plugin.getConfig().getDouble("hub.y"),
                    plugin.getConfig().getDouble("hub.z"),
                    (float) plugin.getConfig().getDouble("hub.yaw"),
                    (float) plugin.getConfig().getDouble("hub.pitch")
            );

            player.teleport(lobbyLocation);
            player.sendMessage(Survival.getPlugin().colorize(ConfigManager.getMainPrefix() +"&aTeleported to the lobby!"));
        } else {
            String worldName = player.getWorld().getName();
            if (!plugin.getConfig().contains("spawns." + worldName + ".world")) {
                player.sendMessage(Survival.plugin.colorize(ConfigManager.getErrorPrefix() + "&cThe spawn location for this world is not set."));
                return true;
            }

            Location spawnLocation = new Location(
                    Bukkit.getWorld(plugin.getConfig().getString("spawns." + worldName + ".world")),
                    plugin.getConfig().getDouble("spawns." + worldName + ".x"),
                    plugin.getConfig().getDouble("spawns." + worldName + ".y"),
                    plugin.getConfig().getDouble("spawns." + worldName + ".z"),
                    (float) plugin.getConfig().getDouble("spawns." + worldName + ".yaw"),
                    (float) plugin.getConfig().getDouble("spawns." + worldName + ".pitch")
            );

            player.teleport(spawnLocation);
            player.sendMessage(Survival.getPlugin().colorize( ConfigManager.getMainPrefix() + "&aTeleported to the world spawn!"));
        }

        return true;
    }
}
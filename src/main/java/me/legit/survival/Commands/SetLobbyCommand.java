package me.legit.survival.Commands;

import me.legit.survival.Survival;
import me.legit.survival.Utils.ConfigManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetLobbyCommand implements CommandExecutor {

    private final Survival plugin;

    public SetLobbyCommand(Survival plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ConfigManager.getConsoleNoPermission());
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("rank.admin")) {
            sender.sendMessage(ConfigManager.getNoPermission());
            return true;
        }

        Location location = player.getLocation();
        plugin.getConfig().set("hub.world", location.getWorld().getName());
        plugin.getConfig().set("hub.x", location.getX());
        plugin.getConfig().set("hub.y", location.getY());
        plugin.getConfig().set("hub.z", location.getZ());
        plugin.getConfig().set("hub.yaw", location.getYaw());
        plugin.getConfig().set("hub.pitch", location.getPitch());
        plugin.saveConfig();

        player.sendMessage(Survival.getPlugin().colorize(ConfigManager.getMainPrefix() + "&aLobby location has been set!"));

        return true;
    }
}
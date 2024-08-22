package me.legit.survival.Commands;

import me.legit.survival.Survival;
import me.legit.survival.Utils.ConfigManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Check if the sender is a player
        if (!(sender instanceof Player)) {
            sender.sendMessage(ConfigManager.getConsoleNoPermission());
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("rank.helper")) {
            player.sendMessage(ConfigManager.getNoPermission());
            return true;
        }

        // Toggle flight
        if (player.getAllowFlight()) {
            player.setAllowFlight(false);
            player.sendMessage(Survival.getPlugin().colorize(ConfigManager.getMainPrefix() + "Flight mode disabled."));
        } else {
            player.setAllowFlight(true);
            player.sendMessage(Survival.getPlugin().colorize(ConfigManager.getMainPrefix() + "Flight mode enabled."));
        }

        return true;
    }
}
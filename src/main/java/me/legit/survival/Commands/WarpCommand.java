package me.legit.survival.Commands;

import me.legit.survival.Utils.ConfigManager;
import me.legit.survival.Utils.WarpManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ConfigManager.getConsoleNoPermission());
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("rank.default")) {
            player.sendMessage(ConfigManager.getNoPermission());
            return true;
        }
        if (args.length != 1) {
            player.sendMessage(ConfigManager.getMainPrefix() + "§cUsage: /warp <name>");
            return true;
        }
        String warpName = args[0];
        Location warp = WarpManager.getWarp(warpName);
        if (warp == null) {
            player.sendMessage(ConfigManager.getMainPrefix() + "§cWarp not found: §e" + warpName);
            return true;
        }
        player.teleport(warp);
        player.sendMessage(ConfigManager.getMainPrefix() + "§aTeleported to warp §e" + warpName + "§a!");
        return true;
    }
}

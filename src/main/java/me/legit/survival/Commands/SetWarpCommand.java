package me.legit.survival.Commands;

import me.legit.survival.Utils.ConfigManager;
import me.legit.survival.Utils.WarpManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetWarpCommand implements CommandExecutor {

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
            player.sendMessage(ConfigManager.getMainPrefix() + "§cUsage: /setwarp <name>");
            return true;
        }
        String warpName = args[0];
        WarpManager.setWarp(warpName, player.getLocation());
        player.sendMessage(ConfigManager.getMainPrefix() + "§aWarp §e" + warpName + " §aset successfully!");
        return true;
    }
}

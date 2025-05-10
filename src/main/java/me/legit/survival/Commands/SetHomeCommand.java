package me.legit.survival.Commands;

import me.legit.survival.Utils.ConfigManager;
import me.legit.survival.Utils.HomeManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetHomeCommand implements CommandExecutor {

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
        HomeManager.setHome(player);
        player.sendMessage(ConfigManager.getMainPrefix() + "§aHome set successfully!");
        return true;
    }
}

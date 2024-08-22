package me.legit.survival.Commands;

import me.legit.survival.Survival;
import me.legit.survival.Utils.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearChatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("rank.admin")) {
            for (int i = 0; i < 100; i++) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendMessage("");
                }
            }
            return true;
        } else {
            sender.sendMessage(Survival.getPlugin().colorize(ConfigManager.getErrorPrefix()));
            return true;
        }
    }
}
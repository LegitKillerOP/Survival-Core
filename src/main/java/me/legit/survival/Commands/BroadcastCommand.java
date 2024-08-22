package me.legit.survival.Commands;

import me.legit.survival.Survival;
import me.legit.survival.Utils.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BroadcastCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("rank.admin")) {
            if (args.length < 1) {
                sender.sendMessage(Survival.getPlugin().colorize(ConfigManager.getErrorPrefix() + "Usage: /broadcast <message>"));
                return true;
            }

            String message = String.join(" ", args);
            Bukkit.broadcastMessage(Survival.getPlugin().colorize("&c&l[ALERT]&f " + message));
        }
        return true;
    }
}
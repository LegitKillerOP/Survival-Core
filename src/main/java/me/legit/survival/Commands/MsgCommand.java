package me.legit.survival.Commands;

import me.legit.survival.Survival;
import me.legit.survival.Utils.ConfigManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MsgCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("msg")) {
            if (args.length < 2) {
                sender.sendMessage(Survival.plugin.colorize(ConfigManager.getErrorPrefix() + "&fUsage: /msg <player> <message>"));
                return true;
            }

            String targetName = args[0];
            Player targetPlayer = Survival.getPlugin().getServer().getPlayer(targetName);

            if (targetPlayer == null) {
                sender.sendMessage(Survival.plugin.colorize(ConfigManager.getErrorPrefix() + "&fPlayer '" + targetName + "' is not online!"));
                return true;
            }

            // Construct the message from args
            StringBuilder messageBuilder = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                messageBuilder.append(args[i]).append(" ");
            }
            String message = Survival.getPlugin().colorize(messageBuilder.toString().trim());

            // Check if sender is a player or console
            if (sender instanceof Player) {
                Player player = (Player) sender;
                targetPlayer.sendMessage(ChatColor.LIGHT_PURPLE + player.getName() + ": " + ChatColor.RESET + message);
                player.sendMessage(ChatColor.LIGHT_PURPLE + targetPlayer.getName() + ": " + ChatColor.RESET + message);
            } else {
                // If sender is console, send message without player-specific formatting
                targetPlayer.sendMessage(ChatColor.LIGHT_PURPLE + "CONSOLE" + ": " + ChatColor.RESET + message);
            }

            return true;
        }
        return false;
    }
}
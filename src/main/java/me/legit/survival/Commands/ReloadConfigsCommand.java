package me.legit.survival.Commands;

import me.legit.survival.Survival;
import me.legit.survival.Utils.ConfigManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class ReloadConfigsCommand implements CommandExecutor {

    private JavaPlugin plugin;

    public ReloadConfigsCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("rank.admin")) {
            ConfigManager.reloadAllConfigs();
            sender.sendMessage(Survival.getPlugin().colorize(ConfigManager.getMainPrefix() + "All configs have been reloaded!"));
        } else {
            sender.sendMessage(Survival.getPlugin().colorize(ConfigManager.getErrorPrefix() + "No permission"));
        }
        return true;
    }
}
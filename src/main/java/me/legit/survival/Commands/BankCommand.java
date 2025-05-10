package me.legit.survival.Commands;

import me.legit.survival.Gui.BankManager;
import me.legit.survival.Utils.ConfigManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BankCommand implements CommandExecutor {

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

        BankManager.openBankGUI(player);
        return true;
    }
}

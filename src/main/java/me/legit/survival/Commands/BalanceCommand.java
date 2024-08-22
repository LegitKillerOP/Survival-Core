package me.legit.survival.Commands;

import me.legit.survival.Survival;
import me.legit.survival.Utils.ConfigManager;
import me.legit.survival.Utils.CurrencyManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BalanceCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Survival.getPlugin().colorize(ConfigManager.getConsoleNoPermission()));
            return true;
        }

        Player player = (Player) sender;
        double balance = CurrencyManager.getEconomy().getBalance(player);
        player.sendMessage(Survival.getPlugin().colorize(ConfigManager.getMainPrefix() + "&aYour current balance is: ") + balance);
        return true;
    }
}
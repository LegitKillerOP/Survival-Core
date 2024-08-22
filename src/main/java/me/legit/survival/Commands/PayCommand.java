package me.legit.survival.Commands;

import me.legit.survival.Survival;
import me.legit.survival.Utils.ConfigManager;
import me.legit.survival.Utils.CurrencyManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PayCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Survival.getPlugin().colorize(ConfigManager.getConsoleNoPermission()));
            return true;
        }

        if (args.length != 2) {
            sender.sendMessage(Survival.getPlugin().colorize(ConfigManager.getErrorPrefix() + "Usage: /pay <player> <amount>"));
            return true;
        }

        Player payer = (Player) sender;
        Player receiver = Bukkit.getPlayer(args[0]);
        if (receiver == null) {
            payer.sendMessage(Survival.getPlugin().colorize(ConfigManager.getErrorPrefix() + "Player not online!"));
            return true;
        }

        double amount;
        try {
            amount = parseAmount(args[1]);
        } catch (NumberFormatException e) {
            payer.sendMessage(Survival.getPlugin().colorize(ConfigManager.getErrorPrefix() + "Invalid amount!"));
            return true;
        }

        if (CurrencyManager.getEconomy().getBalance(payer) < amount) {
            payer.sendMessage(Survival.getPlugin().colorize(ConfigManager.getErrorPrefix() + "Insufficient funds!"));
            return true;
        }

        CurrencyManager.getEconomy().withdrawPlayer(payer, amount);
        CurrencyManager.getEconomy().depositPlayer(receiver, amount);

        payer.sendMessage(Survival.getPlugin().colorize(ConfigManager.getMainPrefix() + "You have sent " + amount + " to " + receiver.getName()));
        receiver.sendMessage(Survival.getPlugin().colorize(ConfigManager.getMainPrefix() + "You have received " + amount + " from " + payer.getName()));

        return true;
    }

    private double parseAmount(String amountStr) throws NumberFormatException {
        if (amountStr.endsWith("k") || amountStr.endsWith("K")) {
            return Double.parseDouble(amountStr.substring(0, amountStr.length() - 1)) * 1_000;
        } else if (amountStr.endsWith("m") || amountStr.endsWith("M")) {
            return Double.parseDouble(amountStr.substring(0, amountStr.length() - 1)) * 1_000_000;
        } else {
            return Double.parseDouble(amountStr);
        }
    }
}

package me.legit.survival.Commands;

import me.legit.survival.Survival;
import me.legit.survival.Utils.ConfigManager;
import me.legit.survival.Utils.CurrencyManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetMoneyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 2) {
            sender.sendMessage(Survival.getPlugin().colorize(ConfigManager.getErrorPrefix() + "Usage: /setmoney <player|*> <amount>"));
            return true;
        }

        double amount;
        try {
            amount = parseAmount(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(Survival.getPlugin().colorize(ConfigManager.getErrorPrefix() + "Invalid amount!"));
            return true;
        }

        if (args[0].equalsIgnoreCase("*")) {
            for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
                double currentBalance = CurrencyManager.getEconomy().getBalance(offlinePlayer);
                CurrencyManager.getEconomy().withdrawPlayer(offlinePlayer, currentBalance);
                CurrencyManager.getEconomy().depositPlayer(offlinePlayer, amount);
            }
            sender.sendMessage(Survival.getPlugin().colorize(ConfigManager.getMainPrefix() + "Set all players' balance to " + CurrencyManager.formatCurrency(amount) + "."));
        } else {
            OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
            if (player.hasPlayedBefore() || player.isOnline()) {
                double currentBalance = CurrencyManager.getEconomy().getBalance(player);
                CurrencyManager.getEconomy().withdrawPlayer(player, currentBalance);
                CurrencyManager.getEconomy().depositPlayer(player, amount);
                sender.sendMessage(Survival.getPlugin().colorize(ConfigManager.getMainPrefix() + "Set " + player.getName() + "'s balance to " + CurrencyManager.formatCurrency(amount)));
            } else {
                sender.sendMessage(Survival.getPlugin().colorize(ConfigManager.getErrorPrefix() + "Player not found!"));
            }
        }
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

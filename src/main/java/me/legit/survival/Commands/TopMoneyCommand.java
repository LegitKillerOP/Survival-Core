package me.legit.survival.Commands;

import me.legit.survival.Survival;
import me.legit.survival.Utils.ConfigManager;
import me.legit.survival.Utils.CurrencyManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.*;
import java.util.stream.Collectors;

public class TopMoneyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Economy economy = CurrencyManager.getEconomy();
        if (economy == null) {
            sender.sendMessage(Survival.getPlugin().colorize(ConfigManager.getErrorPrefix() + "&cEconomy system not available."));
            return true;
        }

        // Get all offline players and their balances
        List<OfflinePlayer> players = Arrays.asList(Bukkit.getOfflinePlayers());
        Map<OfflinePlayer, Double> balances = players.stream()
                .collect(Collectors.toMap(player -> player, economy::getBalance));

        // Sort players by balance in descending order
        List<Map.Entry<OfflinePlayer, Double>> sortedBalances = balances.entrySet().stream()
                .sorted(Map.Entry.<OfflinePlayer, Double>comparingByValue().reversed())
                .limit(10) // Limit to top 10 players
                .collect(Collectors.toList());

        sender.sendMessage(Survival.getPlugin().colorize("&6Top 10 Richest Players:"));
        for (int i = 0; i < sortedBalances.size(); i++) {
            Map.Entry<OfflinePlayer, Double> entry = sortedBalances.get(i);
            String playerName = entry.getKey().getName();
            double balance = entry.getValue();
            sender.sendMessage(Survival.getPlugin().colorize("&e" + (i + 1) + ". " + playerName + " - " + CurrencyManager.formatCurrency(balance)));
        }

        return true;
    }
}

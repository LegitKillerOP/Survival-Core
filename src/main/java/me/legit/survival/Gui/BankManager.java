package me.legit.survival.Gui;

import me.legit.survival.Utils.ConfigManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

public class BankManager {

    private static FileConfiguration bankConfig;
    private static final String BANK_FILE = "bank.yml";
    private static Economy economy;

    public static void setup(Economy eco) {
        economy = eco;
        bankConfig = ConfigManager.getConfig(BANK_FILE);
        if (bankConfig == null) {
            // if bank.yml doesn't exist, create it
            ConfigManager.loadAllConfigs(me.legit.survival.Survival.getPlugin());
            bankConfig = ConfigManager.getConfig(BANK_FILE);
        }
    }

    public static void createBankGUI() {
        GUIManager.createGUI("Bank", 9, "&aBank", (player, event) -> handleBankClick(player, event));

        ItemStack depositItem = createItem(Material.CHEST, "&6Deposit", "&7Click to deposit $100");
        ItemStack withdrawItem = createItem(Material.DISPENSER, "&6Withdraw", "&7Click to withdraw $100");
        ItemStack balanceItem = createItem(Material.PAPER, "&bCheck Balance", "&7See your bank account balance");

        GUIManager.addItemToGUI("Bank", 2, depositItem);
        GUIManager.addItemToGUI("Bank", 4, balanceItem);
        GUIManager.addItemToGUI("Bank", 6, withdrawItem);
    }

    public static void openBankGUI(Player player) {
        GUIManager.openGUI(player, "Bank");
    }

    private static void handleBankClick(Player player, InventoryClickEvent event) {
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || !clickedItem.hasItemMeta()) return;

        String itemName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());
        UUID uuid = player.getUniqueId();

        if (itemName.equalsIgnoreCase("Deposit")) {
            if (economy.getBalance(player) >= 100) {
                economy.withdrawPlayer(player, 100);
                double currentBank = getBankBalance(uuid);
                setBankBalance(uuid, currentBank + 100);
                player.sendMessage(ConfigManager.getMainPrefix() + ChatColor.GREEN + "Deposited $100 to your bank account!");
            } else {
                player.sendMessage(ConfigManager.getErrorPrefix() + ChatColor.RED + "You don't have enough money to deposit!");
            }
        } else if (itemName.equalsIgnoreCase("Withdraw")) {
            double currentBank = getBankBalance(uuid);
            if (currentBank >= 100) {
                setBankBalance(uuid, currentBank - 100);
                economy.depositPlayer(player, 100);
                player.sendMessage(ConfigManager.getMainPrefix() + ChatColor.GREEN + "Withdrew $100 from your bank account!");
            } else {
                player.sendMessage(ConfigManager.getErrorPrefix() + ChatColor.RED + "You don't have enough bank balance to withdraw!");
            }
        } else if (itemName.equalsIgnoreCase("Check Balance")) {
            double bankBalance = getBankBalance(uuid);
            player.sendMessage(ConfigManager.getMainPrefix() + ChatColor.AQUA + "Your bank account balance: $" + String.format("%.2f", bankBalance));
        }
    }

    private static ItemStack createItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        meta.setLore(Arrays.stream(lore)
                .map(line -> ChatColor.translateAlternateColorCodes('&', line))
                .collect(Collectors.toList()));
        item.setItemMeta(meta);
        return item;
    }

    private static double getBankBalance(UUID uuid) {
        return bankConfig.getDouble("bank." + uuid.toString(), 0.0);
    }

    private static void setBankBalance(UUID uuid, double amount) {
        bankConfig.set("bank." + uuid.toString(), amount);
        ConfigManager.saveAllConfigs();
    }
}

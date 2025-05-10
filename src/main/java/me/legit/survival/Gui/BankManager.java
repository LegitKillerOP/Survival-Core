package me.legit.survival.Gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.stream.Collectors;

public class BankManager {

    public static void createBankGUI() {
        GUIManager.createGUI("Bank", 9, "&aBank", (player, event) -> handleBankClick(player, event));

        // Add some items to the GUI
        ItemStack depositItem = createItem(Material.CHEST, "&6Deposit", "&7Click to deposit money");
        ItemStack withdrawItem = createItem(Material.DISPENSER, "&6Withdraw", "&7Click to withdraw money");

        GUIManager.addItemToGUI("Bank", 3, depositItem);
        GUIManager.addItemToGUI("Bank", 5, withdrawItem);
    }

    public static void openBankGUI(Player player) {
        GUIManager.openGUI(player, "Bank");
    }

    private static void handleBankClick(Player player, InventoryClickEvent event) {
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || !clickedItem.hasItemMeta()) return;

        String itemName = clickedItem.getItemMeta().getDisplayName();
        if (itemName.equalsIgnoreCase(ChatColor.GREEN + "Deposit")) {
            // Handle deposit logic
            player.sendMessage(ChatColor.GREEN + "You clicked to deposit money!");
        } else if (itemName.equalsIgnoreCase(ChatColor.RED + "Withdraw")) {
            // Handle withdraw logic
            player.sendMessage(ChatColor.RED + "You clicked to withdraw money!");
        }
    }

    private static ItemStack createItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

        // Translate color codes for each line of lore
        meta.setLore(Arrays.stream(lore)
                .map(line -> ChatColor.translateAlternateColorCodes('&', line))
                .collect(Collectors.toList()));

        item.setItemMeta(meta);
        return item;
    }
}

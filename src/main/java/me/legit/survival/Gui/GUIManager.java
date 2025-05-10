package me.legit.survival.Gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class GUIManager {
    private static final Map<String, Inventory> guiInventories = new HashMap<>();
    private static final Map<String, BiConsumer<Player, InventoryClickEvent>> guiClickHandlers = new HashMap<>();
    private static final Map<String, BiConsumer<Player, InventoryCloseEvent>> guiCloseHandlers = new HashMap<>();

    // Create a new GUI
    public static void createGUI(String guiName, int size, String title, BiConsumer<Player, InventoryClickEvent> clickHandler) {
        Inventory gui = Bukkit.createInventory(null, size, ChatColor.translateAlternateColorCodes('&', title));
        guiInventories.put(guiName, gui);
        guiClickHandlers.put(guiName, clickHandler);
    }

    // Create a new GUI with specific type
    public static void createGUI(String guiName, InventoryType type, String title, BiConsumer<Player, InventoryClickEvent> clickHandler) {
        Inventory gui = Bukkit.createInventory(null, type, ChatColor.translateAlternateColorCodes('&', title));
        guiInventories.put(guiName, gui);
        guiClickHandlers.put(guiName, clickHandler);
    }

    // Get the GUI inventory by name
    public static Inventory getGUI(String guiName) {
        return guiInventories.get(guiName);
    }

    // Open the GUI for a player
    public static void openGUI(Player player, String guiName) {
        Inventory gui = guiInventories.get(guiName);
        if (gui != null) {
            player.openInventory(gui);
        }
    }

    // Register a handler for when a player closes a GUI
    public static void registerCloseHandler(String guiName, BiConsumer<Player, InventoryCloseEvent> closeHandler) {
        guiCloseHandlers.put(guiName, closeHandler);
    }

    // Handle inventory clicks
    public static void handleInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory clickedInventory = event.getInventory();
        for (Map.Entry<String, Inventory> entry : guiInventories.entrySet()) {
            if (clickedInventory.equals(entry.getValue())) {
                BiConsumer<Player, InventoryClickEvent> handler = guiClickHandlers.get(entry.getKey());
                if (handler != null) {
                    handler.accept(player, event);
                }
                event.setCancelled(true);
                return;
            }
        }
    }

    // Handle inventory closes
    public static void handleInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Inventory closedInventory = event.getInventory();
        for (Map.Entry<String, Inventory> entry : guiInventories.entrySet()) {
            if (closedInventory.equals(entry.getValue())) {
                BiConsumer<Player, InventoryCloseEvent> handler = guiCloseHandlers.get(entry.getKey());
                if (handler != null) {
                    handler.accept(player, event);
                }
                return;
            }
        }
    }

    // Add items to a GUI
    public static void addItemToGUI(String guiName, int slot, ItemStack item) {
        Inventory gui = guiInventories.get(guiName);
        if (gui != null) {
            gui.setItem(slot, item);
        }
    }
}

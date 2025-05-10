package me.legit.survival.Commands;

import me.legit.survival.Gui.GUIManager;
import me.legit.survival.Survival;
import me.legit.survival.Utils.ConfigManager;
import me.legit.survival.Utils.WarpManager;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WarpsCommand implements CommandExecutor {
    private static final String GUI_NAME = "warps_gui";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ConfigManager.getConsoleNoPermission());
            return true;
        }
        Player player = (Player) sender;

        if (GUIManager.getGUI(GUI_NAME) == null) {
            // Create GUI only once
            GUIManager.createGUI(GUI_NAME, 27, "&8Available Warps", (clicker, event) -> handleWarpClick(clicker, event));
            int slot = 0;
            for (String warpName : WarpManager.getAllWarpNames()) {
                ItemStack item = new ItemStack(Material.ENDER_PEARL);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("§b" + warpName);
                item.setItemMeta(meta);
                GUIManager.addItemToGUI(GUI_NAME, slot, item);
                slot++;
            }
        }

        GUIManager.openGUI(player, GUI_NAME);
        return true;
    }

    private void handleWarpClick(Player player, InventoryClickEvent event) {
        if (event.getCurrentItem() == null || !event.getCurrentItem().hasItemMeta()) return;

        String warpName = event.getCurrentItem().getItemMeta().getDisplayName().replace("§b", "");
        if (WarpManager.getWarp(warpName) == null) {
            player.sendMessage(Survival.getPlugin().colorize(ConfigManager.getErrorPrefix() + "§cWarp not found: §e" + warpName));
            return;
        }
        player.closeInventory();
        player.teleport(WarpManager.getWarp(warpName));
        player.sendMessage(Survival.getPlugin().colorize(ConfigManager.getMainPrefix() + "§aTeleported to §e" + warpName + "§a!"));
    }
}

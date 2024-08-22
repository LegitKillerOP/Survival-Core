package me.legit.survival.Listeners;

import me.clip.placeholderapi.PlaceholderAPI;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.stream.Collectors;

public class ChatListener implements Listener {
    private final JavaPlugin plugin;
    private final LuckPerms luckPerms;

    public ChatListener(JavaPlugin plugin, LuckPerms luckPerms) {
        this.plugin = plugin;
        this.luckPerms = luckPerms;
    }

    @EventHandler
    public void onChat(final AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        final String message = event.getMessage();

        // Get a LuckPerms cached metadata for the player.
        final CachedMetaData metaData = this.luckPerms.getPlayerAdapter(Player.class).getMetaData(player);
        final String group = metaData.getPrimaryGroup();

        // Load chat format from configuration
        FileConfiguration config = plugin.getConfig();
        String format = config.getString("group-formats." + group);
        if (format == null) {
            format = config.getString("chat-format");
        }

        // Replace placeholders with actual values
        format = format.replace("{prefix}", metaData.getPrefix() != null ? metaData.getPrefix() : "")
                .replace("{suffix}", metaData.getSuffix() != null ? metaData.getSuffix() : "")
                .replace("{prefixes}", metaData.getPrefixes().keySet().stream().map(key -> metaData.getPrefixes().get(key)).collect(Collectors.joining()))
                .replace("{suffixes}", metaData.getSuffixes().keySet().stream().map(key -> metaData.getSuffixes().get(key)).collect(Collectors.joining()))
                .replace("{world}", player.getWorld().getName())
                .replace("{name}", player.getName())
                .replace("{displayname}", player.getDisplayName())
                .replace("{username-color}", metaData.getMetaValue("username-color") != null ? metaData.getMetaValue("username-color") : "")
                .replace("{message-color}", metaData.getMetaValue("message-color") != null ? metaData.getMetaValue("message-color") : "");

        // Apply color and placeholders
        format = ChatColor.translateAlternateColorCodes('&', format);
        format = Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI") ? PlaceholderAPI.setPlaceholders(player, format) : format;

        // Apply message color and formatting permissions
        String formattedMessage = format.replace("{message}",
                player.hasPermission("rank.helper")
                        ? ChatColor.translateAlternateColorCodes('&', message)
                        : player.hasPermission("rank.helper") ? ChatColor.translateAlternateColorCodes('&', message)
                        : player.hasPermission("rank.helper") ? message : message);

        event.setFormat(formattedMessage.replace("%", "%%"));
    }
}
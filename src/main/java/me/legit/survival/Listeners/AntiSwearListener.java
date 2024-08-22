package me.legit.survival.Listeners;

import me.legit.survival.Survival;
import me.legit.survival.Utils.ConfigManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;

public class AntiSwearListener implements Listener {

    private final Survival plugin;

    public AntiSwearListener(Survival plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage().toLowerCase();
        FileConfiguration config = plugin.getConfig();
        List<String> swearWords = config.getStringList("swear-words");

        for (String swearWord : swearWords) {
            if (message.contains(swearWord)) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(Survival.getPlugin().colorize(ConfigManager.getMainPrefix() + "Please do not use inappropriate language."));
                return;
            }
        }
    }
}
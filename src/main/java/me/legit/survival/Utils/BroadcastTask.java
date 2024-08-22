package me.legit.survival.Utils;

import me.legit.survival.Survival;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class BroadcastTask extends BukkitRunnable {
    private final JavaPlugin plugin;
    private final List<String> messages;
    private int currentIndex = 0;

    public BroadcastTask(JavaPlugin plugin) {
        this.plugin = plugin;
        this.messages = plugin.getConfig().getStringList("broadcast.messages");
    }

    @Override
    public void run() {
        if (messages.isEmpty()) {
            return;
        }

        String message = Survival.getPlugin().colorize(messages.get(currentIndex));
        Bukkit.broadcastMessage(message);

        currentIndex++;
        if (currentIndex >= messages.size()) {
            currentIndex = 0;
        }
    }
}

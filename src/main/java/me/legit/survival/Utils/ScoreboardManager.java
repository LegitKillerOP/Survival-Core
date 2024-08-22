package me.legit.survival.Utils;

import me.clip.placeholderapi.PlaceholderAPI;
import me.legit.survival.Survival;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.List;

public class ScoreboardManager {
    private final JavaPlugin plugin;

    public ScoreboardManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void initializeScoreboard() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    updateScoreboard(player);
                }
            }
        }.runTaskTimer(plugin, 0L, 20L); // Update every second
    }

    private void updateScoreboard(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("hubScoreboard", "dummy");
        objective.setDisplayName(Survival.getPlugin().colorize(ConfigManager.getConfig("scoreboard.yml").getString("scoreboard.title")));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        List<String> lines = ConfigManager.getConfig("scoreboard.yml").getStringList("scoreboard.lines");
        int score = lines.size();

        for (String line : lines) {
            line = Survival.getPlugin().colorize(line);
            line = PlaceholderAPI.setPlaceholders(player, line);
            // Ensure the line does not exceed 40 characters
            if (line.length() > 40) {
                line = line.substring(0, 40);
            }
            Score scoreLine = objective.getScore(line);
            scoreLine.setScore(score--);
        }

        player.setScoreboard(scoreboard);
    }
}
package me.legit.survival;

import me.legit.survival.Commands.*;
import me.legit.survival.Listeners.AntiSwearListener;
import me.legit.survival.Listeners.ChatListener;
import me.legit.survival.Utils.BroadcastTask;
import me.legit.survival.Utils.ConfigManager;
import me.legit.survival.Utils.CurrencyManager;
import me.legit.survival.Utils.ScoreboardManager;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Survival extends JavaPlugin implements Listener {
    public static Survival plugin;
    private static Survival gPlugin;
    private LuckPerms luckPerms;

    public static Survival getPlugin() {
        return gPlugin;
    }

    @Override
    public void onEnable() {
        gPlugin = this;
        this.luckPerms = LuckPermsProvider.get();
        PluginManager pm = this.getServer().getPluginManager();
        ConfigManager.loadAllConfigs(this);

        if (!CurrencyManager.setupEconomy(this)) {
            getLogger().severe("Vault not found! Disabling plugin...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        this.getCommand("reloadconfigs").setExecutor(new ReloadConfigsCommand(this));
        this.getCommand("broadcast").setExecutor(new BroadcastCommand());
        this.getCommand("clearchat").setExecutor(new ClearChatCommand());
        this.getCommand("fly").setExecutor(new FlyCommand());
        this.getCommand("setlobby").setExecutor(new SetLobbyCommand(this));
        this.getCommand("spawn").setExecutor(new SpawnCommand(this));
        this.getCommand("msg").setExecutor(new MsgCommand());
        this.getCommand("addmoney").setExecutor(new AddMoneyCommand());
        this.getCommand("removemoney").setExecutor(new RemoveMoneyCommand());
        this.getCommand("setmoney").setExecutor(new SetMoneyCommand());

        pm.registerEvents(new AntiSwearListener(this),this);
        pm.registerEvents(new ChatListener(this, luckPerms), this);

        new ScoreboardManager(this).initializeScoreboard();
        int interval = getConfig().getInt("broadcast.interval");
        new BroadcastTask(this).runTaskTimer(this, 0, interval * 20L);

        this.luckPerms = getServer().getServicesManager().getRegistration(LuckPerms.class).getProvider();
        this.getConfig().options().copyDefaults();
        this.saveDefaultConfig();

    }

    @Override
    public void onDisable() {
        ConfigManager.saveAllConfigs();
    }

    public String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}

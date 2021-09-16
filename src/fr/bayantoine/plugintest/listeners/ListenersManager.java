package fr.bayantoine.plugintest.listeners;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import fr.bayantoine.plugintest.Main;
import fr.bayantoine.plugintest.listeners.interact.InteractListener;
import fr.bayantoine.plugintest.listeners.player.PlayerJoinListener;
import fr.bayantoine.plugintest.listeners.player.PlayerQuitListener;

public class ListenersManager {

    public Main plugin;
    public PluginManager publicManager;

    public ListenersManager(Main plugin) {
        this.plugin = plugin;
        this.publicManager = Bukkit.getPluginManager();
    }

    public void registerListeners() {
        this.publicManager.registerEvents(new PlayerJoinListener(this.plugin), this.plugin);
        this.publicManager.registerEvents(new PlayerQuitListener(), this.plugin);
        this.publicManager.registerEvents(new InteractListener(this.plugin), this.plugin);
    }
    
}

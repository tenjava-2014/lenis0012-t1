package com.tenjava.entries.lenis0012.t1.features;

import com.tenjava.entries.lenis0012.t1.TenJava;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.Listener;

/**
 * Created by Lenny on 12-7-2014.
 */
public abstract class BaseFeature {
    protected final TenJava plugin;
    protected final String name;
    protected ConfigurationSection section;

    public BaseFeature(TenJava plugin, String name) {
        this.plugin = plugin;
        this.name = name;
    }

    /**
     * Internal enable method
     */
    public final void onEnable() {
        reloadConfig();
        enable();
    }

    /**
     * ON ENABLE
     */
    public abstract void enable();

    /**
     * ON DISABLE
     */
    public abstract void disable();

    /**
     * Reloads the feature config section
     */
    public void reloadConfig() {
        this.section = plugin.getConfig().getConfigurationSection("features." + name);
    }

    /**
     * Returns the feature config section
     *
     * @return Feature's config section
     */
    public ConfigurationSection getConfig() {
        return section;
    }

    /**
     * Registers a listener
     *
     * @param listener Listener to register
     */
    protected void register(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, plugin);
    }

    /**
     * Registers a command with multiple aliases
     *
     * @param executor Command executor
     * @param commands Command aliases
     */
    protected void register(CommandExecutor executor, String... commands) {
        for(String command : commands) {
            plugin.getCommand(command).setExecutor(executor);
        }
    }
}

package com.tenjava.entries.lenis0012.t1.features;

import com.tenjava.entries.lenis0012.t1.TenJava;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;

/**
 * Created by Lenny on 12-7-2014.
 */
public abstract class BaseFeature {
    protected final TenJava plugin;

    public BaseFeature(TenJava plugin) {
        this.plugin = plugin;
    }

    public abstract void enable();

    public abstract void disable();

    protected void register(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, plugin);
    }

    protected void register(CommandExecutor executor, String... commands) {
        for(String command : commands) {
            plugin.getCommand(command).setExecutor(executor);
        }
    }
}

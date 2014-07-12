package com.tenjava.entries.lenis0012.t1;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class TenJava extends JavaPlugin {
    private static TenJava instance;

    public static TenJava getInstance() {
        return instance;
    }

    public static void setInstance(TenJava instance) {
        TenJava.instance = instance;
    }

    public void onLoad() {
        //Load all features
        for(Feature feature : Feature.values()) {
            feature.load(this);
        }
    }

    @Override
    public void onEnable() {
        setInstance(this);
        loadConfig();

        //Enable all features
        for(Feature feature : Feature.values()) {
            if(feature.enable()) {
                getLogger().log(Level.INFO, "Enabled feature: " + feature.getName());
            }
        }
    }

    @Override
    public void onDisable() {
        setInstance(null);

        //Disable all features
        for(Feature feature : Feature.values()) {
            feature.disable();
        }
    }

    public void loadConfig() {
        saveDefaultConfig(); //May wanna improve later
    }
}

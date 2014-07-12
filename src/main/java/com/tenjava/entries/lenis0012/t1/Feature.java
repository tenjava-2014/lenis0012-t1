package com.tenjava.entries.lenis0012.t1;

import com.tenjava.entries.lenis0012.t1.features.BaseFeature;
import com.tenjava.entries.lenis0012.t1.features.chests.ChestFeature;

import java.lang.reflect.Constructor;
import java.util.logging.Level;

/**
 * Created by Lenny on 12-7-2014.
 */
public enum Feature {
    CHESTS("chests", ChestFeature.class);

    private final String name;
    private final Class<? extends BaseFeature> baseClass;
    private BaseFeature instance;

    /**
     * @param name Name of features
     * @param baseClass Feature base class
     */
    private Feature(String name, Class<? extends BaseFeature> baseClass) {
        this.name = name;
        this.baseClass = baseClass;
    }

    public String getName() {
        return name;
    }

    public void load(TenJava plugin) {
        try {
            Constructor<? extends BaseFeature> constructor = baseClass.getConstructor(TenJava.class, String.class);
            this.instance = constructor.newInstance(plugin, name);
        } catch (Exception e) {
            TenJava.getInstance().getLogger().log(Level.SEVERE, "Failed to load feature", e);
        }
    }

    public void enable() {
        instance.enable();
    }

    public void disable() {
        instance.disable();
    }

    public <T> T getInstance(Class<T> type) {
        return type.cast(instance);
    }
}

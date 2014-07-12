package com.tenjava.entries.lenis0012.t1.features.chests;

import com.tenjava.entries.lenis0012.t1.TenJava;
import com.tenjava.entries.lenis0012.t1.features.BaseFeature;

/**
 * Created by Lenny on 12-7-2014.
 */
public class ChestFeature extends BaseFeature {

    public ChestFeature(TenJava plugin, String name) {
        super(plugin, name);
    }

    @Override
    public void enable() {
        register(new ChestListener(this));
    }

    @Override
    public void disable() {

    }
}

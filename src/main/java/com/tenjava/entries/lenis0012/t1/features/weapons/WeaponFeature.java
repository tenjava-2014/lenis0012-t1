package com.tenjava.entries.lenis0012.t1.features.weapons;

import com.tenjava.entries.lenis0012.t1.TenJava;
import com.tenjava.entries.lenis0012.t1.features.BaseFeature;

/**
 * Created by Lenny on 12-7-2014.
 */
public class WeaponFeature extends BaseFeature {

    public WeaponFeature(TenJava plugin, String name) {
        super(plugin, name);
    }

    @Override
    public void enable() {
        register(new WeaponListener());
    }

    @Override
    public void disable() {
    }
}

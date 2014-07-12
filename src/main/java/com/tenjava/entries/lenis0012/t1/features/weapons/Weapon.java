package com.tenjava.entries.lenis0012.t1.features.weapons;

import com.tenjava.entries.lenis0012.t1.TenJava;
import com.tenjava.entries.lenis0012.t1.features.weapons.types.BaseWeapon;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Constructor;

/**
 * Created by Lenny on 12-7-2014.
 */
public enum Weapon {
   THORS_HAMMER("ThorHammer", null);

    private final String name;
    private final ConfigurationSection config;
    private boolean enabled;
    private BaseWeapon weapon;
    private int wealth;

    private Weapon(String name, Class<? extends BaseWeapon> weaponClass) {
        this.name = name;
        this.config = TenJava.getInstance().getConfig().getConfigurationSection("features.weapons." + name);
        this.enabled = config.getBoolean("enabled");
        if(enabled) {
            try {
                Constructor<? extends BaseWeapon> constructor = weaponClass.getConstructor(ConfigurationSection.class);
                this.weapon = constructor.newInstance(config);
            } catch(Exception e) {
                this.enabled = false;
                this.weapon = null;
                e.printStackTrace(); //TODO: Change later
            }

            this.wealth = config.getInt("wealth");
        } else {
            this.weapon = null;
            this.wealth = 0;
        }
    }

    /**
     * Fire with weapon if enabled.
     *
     * @param player Weapon holder
     */
    public void fire(Player player) {
        if(enabled) {
            weapon.fire(player);
        }
    }

    /**
     * Get weapon by item in hand
     *
     * @param item Item in hand.
     * @return Weapon
     */
    public static Weapon getWeapon(ItemStack item) {
        for(Weapon weapon : values()) {
            if(weapon.weapon != null && weapon.weapon.check(item)) {
                return weapon;
            }
        }

        return null;
    }
}

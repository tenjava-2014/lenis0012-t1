package com.tenjava.entries.lenis0012.t1.features.weapons.types;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by Lenny on 12-7-2014.
 */
public abstract class BaseWeapon {
    protected final String displayName;
    protected final Material material;
    protected final ConfigurationSection config;

    /**
     * @param displayName Display name of weapon.
     * @param material Material of the weapon.
     */
    public BaseWeapon(String displayName, Material material, ConfigurationSection config) {
        this.displayName = ChatColor.translateAlternateColorCodes('&', displayName);
        this.material = material;
        this.config = config;
    }

    /**
     * Check if an item held is this weapon.
     *
     * @param item Item held
     * @return Item is this weapon?
     */
    public boolean check(ItemStack item) {
        return item != null && item.getType() == material && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals(displayName);
    }

    /**
     * Get the item of the weapon
     *
     * @return Make a new item and return it.
     */
    public ItemStack getItem() {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Fires the weapon for the holder.
     *
     * @param holder Holder of the weapon.
     */
    public abstract void fire(Player holder);
}

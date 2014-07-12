package com.tenjava.entries.lenis0012.t1.features.weapons.types;

import com.tenjava.entries.lenis0012.t1.TenJava;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

/**
 * Created by Lenny on 12-7-2014.
 *
 * Thor's hammer fires a fireball.
 * Once this fireball hits the ground it gets removed and it creates an explosion.
 * This explosion destroys the ground and shoots blocks into the sky followed by a lightning storm.
 */
public class ThorsHammer extends BaseWeapon {
    private final double speed;

    /**
     * @param config      Configuration of the weapon.
     */
    public ThorsHammer(ConfigurationSection config) {
        super("Thor's hammer", Material.IRON_AXE, config);

        this.speed = config.getDouble("speed");
    }

    @Override
    public void fire(Player holder) {
        Fireball fireball = holder.launchProjectile(Fireball.class);
        Vector velocity = fireball.getVelocity();
        velocity.multiply(speed);
        fireball.setVelocity(velocity);
        fireball.setMetadata("thorshammer", new FixedMetadataValue(TenJava.getInstance(), true));
    }
}

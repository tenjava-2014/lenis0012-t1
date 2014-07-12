package com.tenjava.entries.lenis0012.t1.features.weapons.types;

import com.tenjava.entries.lenis0012.t1.TenJava;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

/**
 * Created by Lenny on 12-7-2014.
 * NOT DONE IN TIME.
 *
 * The vampire wand shoots a wither skull.
 * When this skull hits the player it will drain lifes from you to hit to target.
 * Your health goes down to half a heart, and for every heart taken, 0.8 damage is done to the target.
 * This is configurable of course.
 */
public class VampireWand extends BaseWeapon implements Listener {
    private final double speed;
    private final double damageMultiplier;

    /**
     * @param config
     */
    public VampireWand(ConfigurationSection config) {
        super("Vampire wand", Material.BLAZE_ROD, config);

        this.speed = config.getDouble("speed");
        this.damageMultiplier = config.getDouble("damage-multiplier");
        Bukkit.getPluginManager().registerEvents(this, TenJava.getInstance());
    }

    @Override
    public void fire(Player holder) {
        Fireball fireball = holder.launchProjectile(WitherSkull.class);
        Vector velocity = fireball.getVelocity();
        velocity.multiply(speed);
        fireball.setVelocity(velocity);
        fireball.setMetadata("vampirewand", new FixedMetadataValue(TenJava.getInstance(), true));
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        final Entity entity = event.getEntity();
        final Entity damager = event.getDamager();
        if(entity instanceof Player && damager.hasMetadata("vampirewand")) {
            final Player player = (Player) entity;
            final ProjectileSource source = ((Projectile) damager).getShooter();
            if(source instanceof Player) {
                final Player shooter = (Player) source;
                if(shooter.isOnline()) {
                    //TODO: create task to damage entities.
                }
            }
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        final Entity entity = event.getEntity();
        if(entity != null && entity.hasMetadata("vampirewand")) {
            event.setCancelled(true);
        }
    }
}

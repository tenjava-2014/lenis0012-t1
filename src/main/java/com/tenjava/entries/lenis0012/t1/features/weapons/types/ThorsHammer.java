package com.tenjava.entries.lenis0012.t1.features.weapons.types;

import com.tenjava.entries.lenis0012.t1.TenJava;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import java.util.Random;

/**
 * Created by Lenny on 12-7-2014.
 *
 * Thor's hammer fires a fireball.
 * Once this fireball hits the ground it gets removed and it creates an explosion.
 * This explosion destroys the ground and shoots blocks into the sky followed by a lightning storm.
 */
public class ThorsHammer extends BaseWeapon implements Listener {
    private final double speed;
    private final int radius;
    private final int minLightning;
    private final int maxLightning;
    private final Random random = new Random();

    /**
     * @param config      Configuration of the weapon.
     */
    public ThorsHammer(ConfigurationSection config) {
        super("Thor's hammer", Material.IRON_AXE, config);

        this.speed = config.getDouble("speed");
        this.radius = config.getInt("radius");
        this.minLightning = config.getInt("min-lightning");
        this.maxLightning = config.getInt("max-lightning") + 1;
        Bukkit.getPluginManager().registerEvents(this, TenJava.getInstance());
    }

    @Override
    public void fire(Player holder) {
        Fireball fireball = holder.launchProjectile(Fireball.class);
        Vector velocity = fireball.getVelocity();
        velocity.multiply(speed);
        fireball.setVelocity(velocity);
        fireball.setMetadata("thorshammer", new FixedMetadataValue(TenJava.getInstance(), true));
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        Entity entity = event.getEntity();
        Location location = entity.getLocation();
        if(entity.hasMetadata("thorshammer")) {
            //Make blocks entities
            for(int x = -radius; x <= radius; x++) {
                for(int y = -radius; y <= radius; y++) {
                    for(int z = -radius; z <= radius; z++) {
                        location = location.add(x, y, z);
                        Block block = location.getBlock();
                        Material type = block.getType();
                        if(type != Material.AIR) {
                            byte data = block.getData();
                            block.setType(Material.AIR);
                            location.getWorld().spawnFallingBlock(location, type, data);
                        }

                        location = location.subtract(x, y, z);
                    }
                }
            }

            //Make explosion
            location.getWorld().createExplosion(location, radius);

            //Make lightning
            int count = minLightning + random.nextInt(maxLightning - minLightning);
            for(int i = 0; i < count; i++) {
                double dx = random.nextInt(radius * 2 + 1) - radius;
                double dz= random.nextInt(radius * 2 + 1) - radius;
                location = location.add(dx, 0, dz);
                location.getWorld().strikeLightningEffect(location);
                location = location.subtract(dx, 0, dz);
            }
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        final Entity entity = event.getEntity();
        if(entity != null && entity.hasMetadata("thorshammer")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        final Entity entity = event.getEntity();
        if(entity.hasMetadata("thorshammer")) {
            event.setCancelled(true);
        }
    }
}

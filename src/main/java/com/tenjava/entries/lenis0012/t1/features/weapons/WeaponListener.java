package com.tenjava.entries.lenis0012.t1.features.weapons;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Lenny on 12-7-2014.
 */
public class WeaponListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final ItemStack item = player.getItemInHand();
        final Action action = event.getAction();

        if(action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
            Weapon weapon = Weapon.getWeapon(item);
            if(weapon != null) {
                weapon.fire(player);
            }
        }
    }
}

package com.tenjava.entries.lenis0012.t1.features.chests;

import com.tenjava.entries.lenis0012.t1.features.weapons.Weapon;
import org.bukkit.*;
import org.bukkit.block.Chest;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Lenny on 12-7-2014.
 */
public class ChestListener implements Listener {
    private final Random random = new Random();
    private final List<String> worlds;
    private final int spawnChance;
    private final int minY;
    private final int maxY;
    private final int minWeapons;
    private final int maxWeapons;

    public ChestListener(ChestFeature chestFeature) {
        ConfigurationSection config = chestFeature.getConfig();
        this.worlds = config.getStringList("worlds");
        this.spawnChance = config.getInt("spawning.spawn-chance");
        this.minY = config.getInt("spawning.minY");
        this.maxY = config.getInt("spawning.maxY") + 1;
        this.minWeapons = config.getInt("spawning.min-weapons");
        this.maxWeapons = config.getInt("spawning.max-weapons") + 1;
    }

    @EventHandler
    public void onChunkGenerate(ChunkPopulateEvent event) {
        final World world = event.getWorld();
        final Chunk chunk = event.getChunk();

        //Check if enabled in world
        if(!worlds.contains(world.getName())) {
            return;
        }

        //This is when we spawn the chest
        if(random.nextInt(1000) < spawnChance) {
            //Pick coords
            int x = random.nextInt(16);
            int y = minY + random.nextInt(maxY - minY);
            int z = random.nextInt(16);

            //Set to chest
            chunk.getBlock(x, y, z).setType(Material.CHEST);
            Chest chest = (Chest) chunk.getBlock(x, y, z).getState();
            Inventory chestInventory = chest.getInventory();

            //Generate items
            Map<Integer, Weapon> wealthList = Weapon.getWealthList();
            int weapons = minWeapons + (maxWeapons > minWeapons ? random.nextInt(maxWeapons - minWeapons) : 0);
            for(int i = 0; i < weapons; i++) {
                int index = random.nextInt(wealthList.size());
                Weapon weapon = wealthList.get(index);
                int slot = random.nextInt(chestInventory.getSize());
                ItemStack item = chestInventory.getItem(slot);
                while(item != null && item.getType() != Material.AIR) {
                    slot = random.nextInt(chestInventory.getSize());
                    item = chestInventory.getItem(slot);
                }

                chestInventory.setItem(slot, weapon.itemStack());
            }

            chest.update(); //IDK if this is needed

            //Notify players
            int cx = chunk.getX() * 16 + x; //Chunk x + x
            int cz = chunk.getZ() * 16 + z; //Chunk z + z
            for(Player player : Bukkit.getOnlinePlayers()) {
                if(player.hasPermission("tenjava.chests.notifyspawn")) {
                    player.sendMessage(String.format("%sA weapon chest has spawned at %sX%d Y%d Z%d", ChatColor.GREEN, ChatColor.GRAY, cx, y, cz));
                }
            }
        }
    }
}

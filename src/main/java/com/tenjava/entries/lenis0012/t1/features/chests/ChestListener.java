package com.tenjava.entries.lenis0012.t1.features.chests;

import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;

import java.util.List;
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

    public ChestListener(ChestFeature chestFeature) {
        ConfigurationSection config = chestFeature.getConfig();
        this.worlds = config.getStringList("worlds");
        this.spawnChance = config.getInt("spawning.spawn-chance");
        this.minY = config.getInt("spawning.minY");
        this.maxY = config.getInt("spawning.maxY");
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

            //Notify players
            for(Player player : Bukkit.getOnlinePlayers()) {
                if(player.hasPermission("tenjava.chests.notifyspawn")) {
                    player.sendMessage(String.format("%sA weapon chest has spawned at %sX%d Y%d Z%d", ChatColor.GREEN, ChatColor.GRAY, x, y, z));
                }
            }
        }
    }
}

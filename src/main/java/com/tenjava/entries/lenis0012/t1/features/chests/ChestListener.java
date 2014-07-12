package com.tenjava.entries.lenis0012.t1.features.chests;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;

import java.util.Random;

/**
 * Created by Lenny on 12-7-2014.
 */
public class ChestListener implements Listener {
    private final ChestFeature chestFeature;
    private final Random random = new Random();

    public ChestListener(ChestFeature chestFeature) {
        this.chestFeature = chestFeature;
    }

    @EventHandler
    public void onChunkGenerate(ChunkPopulateEvent event) {
        final Chunk chunk = event.getChunk();

        //This is when we spawn the chest
        ConfigurationSection config = chestFeature.getConfig();
        int spawnChance = config.getInt("spawning.spawn-chance");
        int minY = config.getInt("spawning.minY");
        int maxY = config.getInt("spawning.maxY");
        if(random.nextInt(1000) < spawnChance) {
            //Pick coords
            int x = random.nextInt(16);
            int y = minY + random.nextInt(maxY - minY);
            int z = random.nextInt(16);

            //Set to chest
            chunk.getBlock(x, y, z).setType(Material.CHEST);
        }
    }
}

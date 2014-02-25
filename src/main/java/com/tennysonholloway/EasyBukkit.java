package com.tennysonholloway;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * easybukkit is designed to make some of the more scary features of Bukkit
 * more accessible to beginners.
 *
 * Author: Tennyson Holloway
 * Date: 2/24/14
 */
public class EasyBukkit extends JavaPlugin {

    @Override
    public void onEnable(){
    }

    @Override
    public void onDisable() {
    }

    public static void deleteBlockLater(Block b, int ticks) {
        final Block fb = b;
        /* start a task to run in 3 seconds */
        Bukkit.getScheduler().runTaskLater(this, new BukkitRunnable() {
            @Override
            public void run() {
                fb.setType(Material.AIR);
            }
        }, ticks); //20 ticks = 1 second
    }



}

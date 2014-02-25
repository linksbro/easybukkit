package com.tennysonholloway;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

/**
 * easybukkit is designed to make some of the more scary features of Bukkit
 * more accessible to beginners.
 *
 * Author: Tennyson Holloway
 * Date: 2/24/14
 */
public final class EasyBukkit extends JavaPlugin {

    public static BukkitScheduler scheduler;
    public static EasyBukkit singleton;

    @Override
    public void onEnable(){
        scheduler = Bukkit.getServer().getScheduler();
        singleton = this;
    }

    /**
     * Used to remove a block after so many ticks. 20 ticks = 1 second.
     * Equivalent to calling setBlockMaterialLater(b, Material.AIR, ticks);
     * @param b Block b to be removed.
     * @param ticks how many ticks to wait before deleting the block
     */
    public static void deleteBlockLater(Block b, int ticks) {
        final Block fb = b;
        /* start a task to run in 3 seconds */
        scheduler.runTaskLater(singleton, new BukkitRunnable() {
            @Override
            public void run() {
                fb.setType(Material.AIR);
            }
        }, ticks); //20 ticks = 1 second
    }

    /**
     * Used to set the Material of a block after so many ticks. 20 ticks = 1 second
     * @param b Block to change
     * @param m Material to change the block too
     * @param ticks how many ticks to wait before deleting the block
     */
    public static void setBlockMaterialLater(Block b, Material m, int ticks) {
        final Block fb = b;
        final Material mat = m;
        /* start a task to run in 3 seconds */
        scheduler.runTaskLater(singleton, new BukkitRunnable() {
            @Override
            public void run() {
                fb.setType(mat);
            }
        }, ticks); //20 ticks = 1 second
    }

    /**
     * Calculates the absolute distance between two locations. That is,
     * the distanceSquared between the locations, square rooted.
     * @param l1 first location
     * @param l2 second location
     * @return the absolute distance between the locations.
     */
    public static double distanceBetween(Location l1, Location l2) {
        return Math.pow(l1.distanceSquared(l2), 0.5d);
    }

    /**
     * Sets the name of an ItemStack, by modifying its meta data.
     * @param iS the item stack you want to name
     * @param name the name you want your item to have
     */
    public static void setItemName(ItemStack iS, String name) {
        ItemMeta meta = iS.getItemMeta();
        meta.setDisplayName(name);
        iS.setItemMeta(meta);
    }
}

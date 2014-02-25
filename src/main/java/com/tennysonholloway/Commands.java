package com.tennysonholloway;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

/**
 * User: links
 * Date: 2/25/14
 * ... __                      __  ________
 * .. / /  ___ ________  ___  /  |/  / ___/
 * . / _ \/ _ `/ __/ _ \/ _ \/ /|_/ / /__
 * ./_.__/\_,_/\__/\___/_//_/_/  /_/\___/
 */
public class Commands {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        if (cmd.getName().equalsIgnoreCase("spawn")) {
            if (!(sender instanceof Player)) {

                if (args.length != 3)
                    return false;

                Player target = (Bukkit.getServer().getPlayer(args[2]));
                if (target == null) {
                    sender.sendMessage(args[0] + " is not online!");
                    return false;
                }

                EntityType spawn = EntityType.valueOf(args[1].toUpperCase());
                if (spawn == null) {
                    sender.sendMessage("Could not find entity "+args[1].toUpperCase());
                    sender.sendMessage("Please use names found in EntityType");
                    return false;
                }

                int count = 0;
                try {
                    count = Integer.parseInt(args[0]);
                } catch (Exception e) {
                    sender.sendMessage("Failed to parse number.");
                    return false;
                }

                for (int i = 0; i < count; i++)
                    target.getWorld().spawnEntity(target.getLocation(), spawn);

            } else {
                sender.sendMessage("This command can only be run from the console.");
            }
        }


        return false;
    }


}

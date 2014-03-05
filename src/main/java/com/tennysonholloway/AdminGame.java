package com.tennysonholloway;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

public class AdminGame implements CommandExecutor, Listener {

	Plugin plugin;
	Logger logger;
	
	boolean miningOn = true;
	boolean moveOn = true;
	
	List<String> semi_op_player_names;
	
	List<String> permitted_commands;
	
	public AdminGame(Plugin plugin){
		this.plugin = plugin;
		this.logger = plugin.getLogger();
		
		semi_op_player_names = new ArrayList<String>();
		
		permitted_commands = new ArrayList<String>();
		permitted_commands.add("give");
		permitted_commands.add("effect");
	}
	
	
	
	
	
	private boolean playMode(ArrayList<String> arguments) {
		run("mass effect <player> clear"); //Mass clear effect
		run("semiop clear"); //No one is a semioperator
		run("move on");
		run("mine on");
		
		return true;
	}

	private boolean opMode(ArrayList<String> arguments) {
		run("mass effect <player> 15 1000 1000"); //Mass blindness
		run("semiop all");  //Everyone is a semioperator
		run("move off");
		run("mine off");
		
		return true;
	}
	
	
	@EventHandler
	public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
	 

		
		String[] args = event.getMessage().split(" ");
		
		String command = args[0].substring(1);
		
        Player sender = event.getPlayer();
        
		logger.info("Command preprocess " + command);

		if(semi_op_player_names.contains(sender.getName())  && permitted_commands.contains(command))
		{
			logger.info("You can do it!");
			
			
			
			
            event.setCancelled(true);
		}
	           

	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		logger.info(sender.getName());
		
	
		
		
		if(sender instanceof Player)
		{
			return false;
		}
		
	    //TODO: NO PLAYERS BEYOND THIS POINT
			
        if (cmd.getName().equalsIgnoreCase("semiop")) {
        	return semiOpSubcommands(args);
        }
        
        if (cmd.getName().equalsIgnoreCase("mass")) {
        	return massSubcommands(args);
        }
        
        if (cmd.getName().equalsIgnoreCase("game")) {
        	return gameSubcommands(args);
        }
        
		if(cmd.getName().equalsIgnoreCase("mine") ){
			plugin.getLogger().info("Turing Mining " + args[0]);
			miningOn = args[0].equals("on") ? true : false;
			return true;
		} 
		
		if(cmd.getName().equalsIgnoreCase("move") ){
			plugin.getLogger().info("Turning Moving " + args[0]);
			moveOn = args[0].equals("on") ? true : false;
			return true;
		} 
		
		return false;
	}
	
	
	private boolean gameSubcommands(String[] args)
	{
    	if(args.length == 0)
    		return false;
    	
    	String sub_command = args[0];
    	
    	ArrayList<String> arguments = new ArrayList<String>();
    	
    	for(int i = 1; i < args.length; i++)
    		arguments.add(args[i]);
		
		if(sub_command.equals("op"))
    	{
    		return opMode(arguments);
    	}
    	
    	if(sub_command.equals("play"))
    	{
    		return playMode(arguments);
    	}
		
		
		return false;
	}
	
	


	private boolean massSubcommands(String[] args)
	{
		String command_template = join(args, " ");
		
		logger.info(command_template);
		
		for(Player player: plugin.getServer().getOnlinePlayers()) {
			player.getServer().dispatchCommand(Bukkit.getConsoleSender(), command_template.replace("<player>", player.getDisplayName()));
		}
		
		return true;
	}
	
	private boolean semiOpSubcommands(String[] args){

    	if(args.length == 0)
    		return false;
    	
    	String sub_command = args[0];
    	
    	ArrayList<String> arguments = new ArrayList<String>();
    	
    	for(int i = 1; i < args.length; i++)
    		arguments.add(args[i]);
    		
    	if(sub_command.equals("add"))
    	{
    		return addPlayers(arguments);
    	}
    	
    	if(sub_command.equals("remove"))
    	{
    		return removePlayers(arguments);
    	}
    	
    	if(sub_command.equals("list"))
    	{
    		return listPlayers(arguments);
    	}
    	
    	if(sub_command.equals("clear"))
    	{
    		return clearPlayers(arguments);
    	}
    	
    	if(sub_command.equals("all"))
    	{
    		return addAllPlayers(arguments);
    	}
    	
    
    	return false;
		
	}
	

	private boolean addAllPlayers(ArrayList<String> arguments) {
		
		semi_op_player_names.clear();
		
		for(Player player: plugin.getServer().getOnlinePlayers()) {
			String name = player.getDisplayName();
			
			logger.info("Adding: " + name);
			
			semi_op_player_names.add(name);
			
			
		}
		
		return true;
	}

	private boolean clearPlayers(List<String> args) {
		logger.info("Clearing players");

		semi_op_player_names.clear();
		
		return true;
	}

	private boolean listPlayers(List<String> args) {
		
		logger.info("Listing players");
		
		for(String name : semi_op_player_names)
			logger.info("   " + name);
		
		
		return true;
	}

	private boolean removePlayers(List<String> args) {
		logger.info("Remove player(s)");

		semi_op_player_names.removeAll(args);
		
		return true;
	}

	private boolean addPlayers(List<String> args) {
		
		logger.info("Adding player(s)");

		semi_op_player_names.addAll(args);
		
		return true;
	}
	
	
	private String join(String[] stuff, String delim){
		String ret = "";
		
		for(int i = 0; i < stuff.length; i++)
		{
			ret = ret + stuff[i] + delim;
		}
		
		return ret;
	}
	
	private void run(String command){
		plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);	
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
	    event.setCancelled(!miningOn);
	    

	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
	    event.setCancelled(!moveOn);
	}
}

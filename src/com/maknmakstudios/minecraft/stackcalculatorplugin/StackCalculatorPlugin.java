package com.maknmakstudios.minecraft.stackcalculatorplugin;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.command.Command;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

@Plugin( name = "StackCalculatorPlugin", version = "1.0.0" )
@Description( "A plugin that tells the player how many stacks of an item are in a certain number of items." )
@Author( "Mak 'n Mak Studios" )
@Command( name = "stackcalc", aliases = { "stc" }, permission = "stackcalculatorplugin.stackcalc" )
public class StackCalculatorPlugin extends JavaPlugin {
	@Override
	public void onEnable( ) {
		// create /stc, which is an alias of /stackcalc <amount> [itemid or stack count]. these commands will calculate how many stacks are in a certain number of items. default to 64 in a stack, unless item id or stack count is supplied and states otherwise.
		registerCommands( );
	}
	
	public void registerCommands( ) {
		getCommand( "stackcalc" ).setExecutor( new CommandStackCalculate( ) );
	}
}

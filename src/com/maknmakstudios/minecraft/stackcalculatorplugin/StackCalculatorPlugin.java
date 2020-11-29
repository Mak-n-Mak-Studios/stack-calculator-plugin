package com.maknmakstudios.minecraft.stackcalculatorplugin;

import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.command.Command;
import org.bukkit.plugin.java.annotation.command.Commands;
import org.bukkit.plugin.java.annotation.permission.ChildPermission;
import org.bukkit.plugin.java.annotation.permission.Permission;
import org.bukkit.plugin.java.annotation.permission.Permissions;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

/**
 * The main plugin class.
 * @author Mak 'n Mak Studios
 */
@Plugin( name = "StackCalculatorPlugin", version = "1.0.0" )
@Description( "A plugin that tells the player how many stacks of an item are in a certain number of items." )
@Author( "Mak 'n Mak Studios" )
@Commands( {
	@Command(
		name = "stackcalc",
		desc = "Puts into chat how many stacks of an item are in a certain number of items.",
		aliases = { "stc" },
		permission = "stackcalculatorplugin.stackcalc",
		permissionMessage = "You do not have permission to use this command.",
		usage = "Usage: /<command> <amount> [itemID | maxStackCount]"
	)
} )
@Permissions( {
	@Permission(
		name = "stackcalculatorplugin.*",
		desc = "Allows the usage of the entire Stack Calculator Plugin.",
		defaultValue = PermissionDefault.TRUE,
		children = {
			@ChildPermission(
				name = "stackcalculatorplugin.stackcalc"
			)
		}
	),
	@Permission(
		name = "stackcalculatorplugin.stackcalc",
		desc = "Allows the usage of /stackcalc (/stc).",
		defaultValue = PermissionDefault.TRUE
	)
} )
public class StackCalculatorPlugin extends JavaPlugin {
	@Override
	public void onEnable( ) {
		registerCommands( );
	}
	
	/**
	 * Registers commands upon plugin initialization.
	 */
	public void registerCommands( ) {
		getCommand( "stackcalc" ).setExecutor( new CommandStackCalculate( ) );
	}
}

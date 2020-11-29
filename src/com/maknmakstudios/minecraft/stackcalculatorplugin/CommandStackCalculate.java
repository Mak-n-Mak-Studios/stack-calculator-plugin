package com.maknmakstudios.minecraft.stackcalculatorplugin;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.inventory.ItemStack;

public class CommandStackCalculate implements TabExecutor {
	private Set<String> allItemIDs;
	
	public CommandStackCalculate( ) {
		allItemIDs = Arrays.stream( Material.values( ) )
				.map( material -> "minecraft:" + material.toString( ).toLowerCase( ) )
				.collect( Collectors.toSet( ) );
	}
	
	@Override
	public boolean onCommand( CommandSender sender, Command command, String label, String[ ] args ) {
		if ( !( args.length == 1 || args.length == 2 ) ) {
			sender.sendMessage( "Not enough or too many arguments!" );
			
			return false;
		}
		
		BigInteger amountOfItems;
		int itemStackMaxCount = 64;
		
		try {
			amountOfItems = new BigInteger( args[ 0 ] );
		} catch ( NumberFormatException e ) {
			sender.sendMessage( "Could not parse amount of items as a number!" );
				
			return false;
		}
		
		if ( amountOfItems.compareTo( BigInteger.valueOf( 0 ) ) <= 0 ) {
			sender.sendMessage( "Amount of items must be more than zero!" );
			
			return false;
		}
		
		if ( args.length == 2 ) {
			String itemIDOrMaxCount = args[ 1 ];
			OptionalInt intParsable = parseItemIDOrMaxCount( itemIDOrMaxCount );
			
			if ( intParsable.isPresent( ) ) { // Is an integer.
				int maxCount = intParsable.getAsInt( );
				
				if ( maxCount <= 0 ) {
					sender.sendMessage( "Invalid max stack count! Max stack count must be more than zero." );
					
					return false;
				}
				
				itemStackMaxCount = intParsable.getAsInt( );
			} else { // Is an item ID.
				if ( !allItemIDs.contains( itemIDOrMaxCount ) ) {
					sender.sendMessage( "Invalid item ID or max stack count!" );
					
					return false;
				}
				
				String removedNamespaceItemID = itemIDOrMaxCount.substring( 10 ); // Removes "minecraft:" from the item ID.
				ItemStack itemStack = new ItemStack( Material.getMaterial( removedNamespaceItemID.toUpperCase( ) ) );
				itemStackMaxCount = itemStack.getMaxStackSize( );
			}
		}
		
		String numberOfStacks = amountOfItems.divide( BigInteger.valueOf( itemStackMaxCount ) ).toString( );
		String remainder = amountOfItems.mod( BigInteger.valueOf( itemStackMaxCount ) ).toString( );
		sender.sendMessage( "Total stack count: " + amountOfItems + " item(s) = " + numberOfStacks + " stack(s) and " + remainder + " item(s) (" + itemStackMaxCount + " maximum stack size)" );
		
		return true;
	}

	@Override
	public List<String> onTabComplete( CommandSender sender, Command command, String alias, String[ ] args ) {
		if ( args.length == 2 ) { // Item ID or integer. Only autocomplete item IDs.
			String currentArg = args[ 1 ];
			boolean isNumeric = currentArg.chars( ).allMatch( character -> Character.isDigit( character ) );
			
			if ( isNumeric ) {
				return Collections.emptyList( );
			}
			
			return allItemIDs.stream( )
					.filter( itemID -> itemID.startsWith( currentArg ) || itemID.startsWith( "minecraft:" + currentArg ) )
					.collect( Collectors.toList( ) );
		}
		
		return Collections.emptyList( );
	}
	
	private OptionalInt parseItemIDOrMaxCount( String itemIDOrMaxCount ) {
		try {
			return OptionalInt.of( Integer.parseInt( itemIDOrMaxCount ) );
		} catch ( NumberFormatException e ) {
			return OptionalInt.empty( );
		}
	}
}

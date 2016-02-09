package com.firegodjr.ancientlanguage.command;

import java.util.ArrayList;
import java.util.List;

import com.firegodjr.ancientlanguage.EntListIterated;
import com.firegodjr.ancientlanguage.output.ModOutput;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class CommandCast implements ICommand{
	
	private final int INVALID = 0;
	private final int ACTION = 1;
	private final int TARGET = 2;
	private final int TILE = 3;
	private final int SEPARATOR = -1;
	
	
	private final List aliases;
	
	public CommandCast()
	{
		 aliases = new ArrayList(); 
	        aliases.add("cast");
	        aliases.add("c");
	}

	@Override
	public int compareTo(Object arg0) {
		return 0;
	}

	@Override
	public String getName() {
		return "cast";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/cast <action> <target>";
	}

	@Override
	public List getAliases() {
		return aliases;
	}

	@Override
	public void execute(ICommandSender sender, String[] args) throws CommandException 
	{
		
		float fatigue = 0;
		float distance = 0;
		
		List<WordTagged> script = new ArrayList();
		
		boolean success = true;
		int wordfailed = 0;
		
		World world = sender.getEntityWorld();
		
		if(args.length == 0)
			sender.addChatMessage(new ChatComponentText("No words casted!"));
		else
		{

			ScriptHandler scriptHandler = new ScriptHandler();
			
			EntityPlayer player = null;
			if(sender.getCommandSenderEntity() instanceof EntityPlayer)
			player = (EntityPlayer)sender.getCommandSenderEntity();
			
			List<EntityLivingBase> targets = new ArrayList();
			
			script = scriptHandler.getScriptFromSpell(args);
			ModOutput.println("Spell script built!");
			
			script = scriptHandler.cleanScript(script);
			ModOutput.println("Cleaned up script!");
			
			scriptHandler.executeScript(script, player);
			player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GRAY + "" + EnumChatFormatting.ITALIC + "You chant: \"" + EnumChatFormatting.RESET + EnumChatFormatting.AQUA + scriptHandler.getChantFromScript(script) + EnumChatFormatting.GRAY + EnumChatFormatting.ITALIC + "\""));
			ModOutput.println("Script Executed!");
			
		}
	}

	@Override
	public boolean canCommandSenderUse(ICommandSender sender) {
		return true;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return false;
	}
	
}
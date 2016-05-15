package com.firegodjr.ancientlanguage.command;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

import com.firegodjr.ancientlanguage.Main;
import com.firegodjr.ancientlanguage.entity.properties.ExtPropMagicExperience;
import com.google.common.collect.Lists;

public class CommandMagPush extends CommandBase {

	public CommandMagPush() {
	}

	@Override
	public int compareTo(Object arg0) {
		return 0;
	}

	@Override
	public String getName() {
		return "magpush";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "command:"+Main.MODID+".magpush.usage";
	}

	@Override
	public List<String> getAliases() {
		return Lists.newArrayList("magicpush", "mp", "aal.mp", "aal.magpush");
	}

	@Override
	public void execute(ICommandSender sender, String[] args) throws CommandException {
		if(args.length < 1) throw new WrongUsageException(this.getCommandUsage(sender));
		if(args.length == 2)
			ExtPropMagicExperience.getExtProp(getPlayer(sender, args[0])).setDefaultPush(parseInt(args[0]));
		else
			ExtPropMagicExperience.getExtProp((Entity) 
					(sender instanceof Entity ? sender : sender.getCommandSenderEntity()))
					.setDefaultPush(parseInt(args[0]));
	}

	@Override
	public boolean canCommandSenderUse(ICommandSender sender) {
		return true;
	}

	@Override
	public List<?> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return index == 0;
	}
}
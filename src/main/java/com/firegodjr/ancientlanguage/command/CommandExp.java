package com.firegodjr.ancientlanguage.command;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

import com.firegodjr.ancientlanguage.Main;
import com.firegodjr.ancientlanguage.entity.properties.ExtPropMagicExperience;
import com.google.common.collect.Lists;

public class CommandExp extends CommandBase {

	public CommandExp() {
	}

	@Override
	public int compareTo(Object arg0) {
		return 0;
	}

	@Override
	public String getName() {
		return "exp";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "command:"+Main.MODID+".exp.usage";
	}

	@Override
	public List<String> getAliases() {
		return Lists.newArrayList("experience", "level", "aal.exp");
	}

	@Override
	public void execute(ICommandSender sender, String[] args) throws CommandException {
		if(args.length == 0) {
			processEntity(sender, ExtPropMagicExperience.getExtProp((Entity) (sender instanceof Entity ? sender : sender.getCommandSenderEntity())));
		} else {
			ExtPropMagicExperience prop = ExtPropMagicExperience.getExtProp(getPlayer(sender, args[0]));
			if(args.length > 1) {
				if (args.length == 2) {
					throw new WrongUsageException(this.getCommandUsage(sender), new Object[0]);
				}
				if (args[2].endsWith("L") || args[2].endsWith("l")) {
					args[2] = args[2].substring(0, args[2].length()-1);
					if ("add".equals(args[1])) prop.setLevel(parseInt(args[2], 0) + prop.getLevel());
					else if ("set".equals(args[1])) prop.setLevel(parseInt(args[2], 0));
				}
				else {
					if ("add".equals(args[1])) prop.addExperience((float) (parseDouble(args[2]) * 0.01));
					else if ("set".equals(args[1])) prop.setExperience((float) (parseDouble(args[2], 0) * 0.01));
				}
			}
			// TODO: implement name message
			processEntity(sender, prop);
		}
	}

	protected ExtPropMagicExperience processEntity(ICommandSender sender, ExtPropMagicExperience prop) {
		ChatStyle style = new ChatStyle().setColor(EnumChatFormatting.BLUE).setItalic(true);
		String gold = EnumChatFormatting.GOLD.toString();
		sender.addChatMessage(new ChatComponentTranslation("text:ancientlanguage.level", 
			gold + String.valueOf(prop == null ? 0 : prop.getLevel())).setChatStyle(style));
		sender.addChatMessage(new ChatComponentTranslation(
			"text:ancientlanguage.experience", gold + (prop == null ? "0%" : prop.getExperiencePercent())).setChatStyle(style));
		return prop;
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
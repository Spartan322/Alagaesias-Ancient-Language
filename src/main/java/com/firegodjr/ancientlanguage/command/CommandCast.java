package com.firegodjr.ancientlanguage.command;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

import com.firegodjr.ancientlanguage.Main;
import com.firegodjr.ancientlanguage.magic.ScriptInstance;
import com.firegodjr.ancientlanguage.magic.ScriptRegistry;
import com.google.common.collect.Lists;

public class CommandCast extends CommandBase {

	public CommandCast() {
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
		return "command:"+Main.MODID+".cast.usage";
	}

	@Override
	public List<String> getAliases() {
		return Lists.newArrayList("c", "aal.cast", "aal.c");
	}

	@Override
	public void execute(ICommandSender sender, String[] args) throws CommandException {
		ChatStyle style = new ChatStyle().setColor(EnumChatFormatting.GRAY).setItalic(true);
		if (args.length == 0) {
			sender.addChatMessage(new ChatComponentTranslation("text:ancientlanguage.nothingSaid").setChatStyle(style));
		} else {
			Main.getLogger().info("Starting new ScriptInstance object");
			ScriptInstance instance = new ScriptInstance(sender, args);
			Main.getLogger().info("Entering script execution");
			instance.onExecute(sender.getEntityWorld(), sender.getPositionVector());
			if (sender instanceof EntityPlayer)	Main.getLogger().info("Telling Player Chant");
			String message = instance.getPrintableChant();
			if (message.isEmpty())
				throw new CommandException("text:ancientlanguage.emptyMessageException");
			sender.addChatMessage(
					new ChatComponentTranslation("text:ancientlanguage.chantPrint", EnumChatFormatting.RESET.toString() + EnumChatFormatting.AQUA + message)
					.setChatStyle(style));
			Main.getLogger().info("Cast Command Executed!");
		}
	}

	@Override
	public boolean canCommandSenderUse(ICommandSender sender) {
		return true;
	}

	@Override
	public List<?> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		return getListOfStringsMatchingLastWord(args, this.getTabPossibilities());
	}

	public String[] getTabPossibilities() {
		List<String> result = Lists.newArrayList(MinecraftServer.getServer().getAllUsernames());
		result.addAll(ScriptRegistry.getAllWords());
		return result.toArray(new String[result.size()]);
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return false;
	}
}
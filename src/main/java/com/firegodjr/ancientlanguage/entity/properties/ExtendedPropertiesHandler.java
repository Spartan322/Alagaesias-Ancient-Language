package com.firegodjr.ancientlanguage.entity.properties;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.player.PlayerEvent;

import com.firegodjr.ancientlanguage.utils.GameRule;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public final class ExtendedPropertiesHandler {

	public static final GameRule preserveDeadRule = new GameRule("aal.preserveDeadExperience", "true");

	@SubscribeEvent
	public void onEntityConstruct(EntityConstructing evt) {
		if (evt.entity instanceof EntityPlayer) evt.entity.registerExtendedProperties(ExtPropMagicExperience.NAME, new ExtPropMagicExperience());
	}

	@SubscribeEvent
	public void onPlayerClone(PlayerEvent.Clone evt) {
		if (evt.wasDeath && !preserveDeadRule.getValueBoolean()) {
			ExtPropMagicExperience.getExtProp(evt.entityPlayer).loadNBTData(new NBTTagCompound());
			return;
		}
		NBTTagCompound compound = new NBTTagCompound();
		ExtPropMagicExperience.getExtProp(evt.original).saveNBTData(compound);
		ExtPropMagicExperience.getExtProp(evt.entityPlayer).loadNBTData(compound);
	}
}

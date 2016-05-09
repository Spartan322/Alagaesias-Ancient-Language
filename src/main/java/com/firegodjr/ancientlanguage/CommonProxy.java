package com.firegodjr.ancientlanguage;

import net.minecraftforge.common.MinecraftForge;

import com.firegodjr.ancientlanguage.blocks.ModBlocks;
import com.firegodjr.ancientlanguage.entity.properties.ExtendedPropertiesHandler;
import com.firegodjr.ancientlanguage.event.LanguageEventHandler;
import com.firegodjr.ancientlanguage.items.ModItems;
import com.firegodjr.ancientlanguage.magic.words.WordHandler;
import com.firegodjr.ancientlanguage.tileentity.TileEntityGhostLight;
import com.firegodjr.ancientlanguage.wards.RegisterWard;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent e) 
	{
		ModItems.createItems();
		ModBlocks.registerBlocks();
		RegisterWard.registerWardComponents();
		GameRegistry.registerTileEntity(TileEntityGhostLight.class, "nainaLightEntity");
		WordHandler.initalizeWords();
	}

	public void init(FMLInitializationEvent e) {
		LanguageEventHandler handler = new LanguageEventHandler();
		MinecraftForge.EVENT_BUS.register(handler);
		FMLCommonHandler.instance().bus().register(handler);
		MinecraftForge.EVENT_BUS.register(new ExtendedPropertiesHandler());
	}

	public void postInit(FMLPostInitializationEvent e) {

	}
}

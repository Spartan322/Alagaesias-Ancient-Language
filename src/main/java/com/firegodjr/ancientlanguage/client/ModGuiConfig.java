package com.firegodjr.ancientlanguage.client;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

import com.firegodjr.ancientlanguage.Main;

import cpw.mods.fml.client.config.GuiConfig;

public class ModGuiConfig extends GuiConfig {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ModGuiConfig(GuiScreen parentScreen) {
		super(parentScreen, 
				new ConfigElement(Main.getConfig().getForgeConfig().getCategory(Configuration.CATEGORY_GENERAL))
				.getChildElements(), Main.MODID, true, false, "Ancient Language Config");
	}

}

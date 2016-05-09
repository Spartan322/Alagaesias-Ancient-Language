package com.firegodjr.ancientlanguage.items;

import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems {

	public static Item spellbook;

	public static void createItems() {
		GameRegistry.registerItem(spellbook = new Spellbook("spellbook"), "spellbook");
	}

}

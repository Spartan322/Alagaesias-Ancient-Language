package com.firegodjr.ancientlanguage.blocks;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {

	public static Block ghostLight;
	public static Block moveableLight;

	public static void registerBlocks() {
		GameRegistry.registerBlock(ghostLight = new BlockGhostLight("ghostLight"), "ghostLight");
		GameRegistry.registerBlock(moveableLight = new BlockMoveableLight("moveableLight", 15), "moveableLight");
	}
}

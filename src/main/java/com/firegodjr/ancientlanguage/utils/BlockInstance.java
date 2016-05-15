package com.firegodjr.ancientlanguage.utils;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class BlockInstance {

	private final IBlockState state;
	private final Vec3 position;
	private final World world;

	public BlockInstance(World w, Vec3 pos, IBlockState state) {
		this.world = w;
		this.position = pos;
		this.state = state;
	}

	public BlockInstance(World w, Vec3 pos, Block block) {
		this(w, pos, block.getDefaultState());
	}

	public IBlockState getBlockState() {
		return this.state;
	}

	public Block getBlock() {
		return this.state.getBlock();
	}

	public Vec3 getPosition() {
		return this.position;
	}

	public World getWorld() {
		return this.world;
	}
}

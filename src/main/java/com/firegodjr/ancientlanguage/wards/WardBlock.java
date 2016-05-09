package com.firegodjr.ancientlanguage.wards;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class WardBlock extends BlockContainer {

	private int energy;
	private List<String> script;
	private static String name;
	
	public WardBlock(String bname) {
		super(Material.air);
		name = bname;
		this.setBlockName(name);
	}
	
	public WardBlock(List<String> script, int energy) {
		super(Material.air);
		this.energy = energy;
		this.script = script;
		this.setBlockName(name);
	}

	/*@Override
	public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ,
			int meta, EntityLivingBase player) {
		return (IBlockState) getBlockState();
	}*/

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		WardEntity we = new WardEntity();
		we.setEnergy(energy);
		we.setSpell(script);
		return we;
	}

	@Override
	public Item getItemDropped(int metadata, Random rand, int fortune) {
		return null;

	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	public boolean isSolidFullCube() {
		return false;
	}

	public boolean isFullCube() {
		return false;
	}

	@Override
	public boolean canDropFromExplosion(Explosion explosion) {
		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World worldIn, int x, int y, int z) {
		return null;
	}
}

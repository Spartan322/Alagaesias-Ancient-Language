package com.firegodjr.ancientlanguage.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import com.firegodjr.ancientlanguage.tileentity.TileEntityMoveableLight;

/**
 * A block to handle a moving light 
 */
public class BlockMoveableLight extends Block implements ITileEntityProvider {

	protected BlockMoveableLight(String unlocalName, int lightLevel) {
		super(Material.air);
		//setDefaultState(blockState.getBaseState());
		setTickRandomly(false);
		setLightLevel(lightLevel);
		setBlockBounds(0.5F, 0.5F, 0.5F, 0.5F, 0.5F, 0.5F);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityMoveableLight();
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World worldIn, int x, int y, int z) {
		return null;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	public boolean isFullCube() {
		return false;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, int x, int y, int z) {
		return true;
	}

	@Override
	public int onBlockPlaced(World worldIn, int x, int y, int z, int facing, float hitX, float hitY, float hitZ, int meta) {
		return 0;
	}

	@Override
	public void onBlockAdded(World worldIn, int x, int y, int z) {}

	/**
	 * Called when a neighboring block changes.
	 */
	@Override
	public void onNeighborBlockChange(World worldIn, int x, int y, int z, Block neighborBlock) {}

	@Override
	public void onFallenUpon(World worldIn, int x, int y, int z, Entity entityIn, float fallDistance) {}

	public void onLanded(World worldIn, Entity entityIn) {}

	public boolean shouldEmit(TileEntityMoveableLight entity) {
		return true;
	}
}

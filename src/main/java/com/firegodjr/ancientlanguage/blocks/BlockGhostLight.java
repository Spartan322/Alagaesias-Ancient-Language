package com.firegodjr.ancientlanguage.blocks;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import com.firegodjr.ancientlanguage.Main;
import com.firegodjr.ancientlanguage.ParticleHandler;
import com.firegodjr.ancientlanguage.tileentity.TileEntityGhostLight;

public class BlockGhostLight extends BlockContainer {

	public BlockGhostLight(String unlocalizedName, Material material) {
		super(material);
		this.setBlockName(unlocalizedName).setBlockTextureName(Main.MODID+":"+unlocalizedName);
		this.setLightLevel(0.9f);
		this.setLightOpacity(0);
		this.setBlockBounds(0.33F, 0.33F, 0.33F, 0.67F, 0.67F, 0.67F);
	}

	public BlockGhostLight(String unlocalizedName) {
		this(unlocalizedName, Material.circuits);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityGhostLight();
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int metadata) {
		Vec3 pos = Vec3.createVectorHelper(x+0.5, y+0.5, z+0.5);
		ParticleHandler.burst(pos, world, 10, 0.5, "fireworks_spark");
	}
	
	@Override
	public Item getItemDropped(int metadata, Random rand, int fortune) {
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
	public boolean canDropFromExplosion(Explosion explosion) {
		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World worldIn, int x, int y, int z) {
		return null;
	}
}

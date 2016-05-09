
package com.firegodjr.ancientlanguage.utils;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

/**
 * A storage object that holds both BlockPos and EnumFacing objects
 */
public class BlockPosHit {
	public Vec3 pos = Vec3.createVectorHelper(0, 0, 0);
	public EnumFacing side;
	
	public BlockPosHit(Vec3 posIn, EnumFacing sideIn)
	{
		pos = posIn;
		side = sideIn;
	}
	
	public BlockPosHit(Vec3 posIn, int sideIn) {
		this(posIn, EnumFacing.values()[sideIn]);
	}
	
	public BlockPosHit(MovingObjectPosition pos) {
		this(pos.hitVec, pos.sideHit);
	}
	
	public double getX() {
		return pos.xCoord;
	}
	
	public double getY() {
		return pos.yCoord;
	}
	
	public double getZ() {
		return pos.zCoord;
	}
	
	public int getIX() {
		return MathHelper.floor_double(pos.xCoord);
	}
	
	public int getIY() {
		return MathHelper.floor_double(pos.yCoord);
	}
	
	public int getIZ() {
		return MathHelper.floor_double(pos.zCoord);
	}
}

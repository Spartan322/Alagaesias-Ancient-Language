package com.firegodjr.ancientlanguage.utils;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

public final class VersionUtils {

	private VersionUtils() {
	}

	@SuppressWarnings("unchecked")
	public static List<EntityPlayer> findPlayersIn(World world,
			final Predicate<EntityPlayer> predicate) {
		List<EntityPlayer> result = Lists.newArrayList(world.playerEntities);
		result.removeIf(new java.util.function.Predicate<EntityPlayer>() {
			@Override
			public boolean test(EntityPlayer input) {
				return !predicate.apply(input);
			}
		});
		return result;
	}

	public static MovingObjectPosition rayTraceFor(Entity tracefor,
			int distance, int idk) {
		Vec3 pos = Vec3.createVectorHelper(tracefor.posX, tracefor.posY,
				tracefor.posZ);
		Vec3 look = tracefor.getLookVec();
		look = pos.addVector(look.xCoord * distance, look.yCoord * distance,
				look.zCoord * distance);
		return tracefor.worldObj.func_147447_a(pos, look, false, false, true);
	}

	public static AxisAlignedBB getAABBFor(Vec3 v1, Vec3 v2) {
		return AxisAlignedBB.getBoundingBox(v1.xCoord, v1.yCoord, v1.zCoord,
				v2.xCoord, v2.yCoord, v2.zCoord);
	}

	public static Vec3 getEntityPosition(EntityLivingBase ent) {
		return ent.getPosition(1f);
	}

}

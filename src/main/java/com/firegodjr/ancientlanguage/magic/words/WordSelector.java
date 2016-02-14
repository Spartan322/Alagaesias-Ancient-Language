package com.firegodjr.ancientlanguage.magic.words;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.firegodjr.ancientlanguage.BlockPosHit;
import com.firegodjr.ancientlanguage.Main;
import com.firegodjr.ancientlanguage.api.script.ISelector;
import com.firegodjr.ancientlanguage.magic.ScriptInstance;
import com.google.common.base.Predicate;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class WordSelector {

	public static class MeSelector implements ISelector {
		@Override
		public List<?> getSelected(ScriptInstance script, World world, Vec3 position) {
			return Collections.singletonList(script.getActualUser());
		}
	}
	
	public static class UsSelector implements ISelector {
		public static final int RADIUS = 4;
		
		@Override
		public List<?> getSelected(ScriptInstance script, World world, Vec3 position) {
			Vec3 pos1 = position.addVector(-RADIUS, -RADIUS / 3, -RADIUS), 
					pos2 = position.addVector(RADIUS, RADIUS / 3, RADIUS);
			return world.getEntitiesWithinAABB(EntityLivingBase.class,
					new AxisAlignedBB(pos1.xCoord,pos1.yCoord,pos1.zCoord, pos2.xCoord, pos2.yCoord, pos2.zCoord));
		}
	}
	
	public static class FriendSelector implements ISelector {
		public static final int MAX_RADIUS = 4;
		
		@Override
		public List<?> getSelected(ScriptInstance script, World world, Vec3 position) {
			return Collections.singletonList(getClosestUnrelatedPlayer(world, script.getActualUser(), position));
		}
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		private EntityPlayer getClosestUnrelatedPlayer(World world, final Object possiblePlayer, final Vec3 position) {
			List<EntityPlayer> list = world.getPlayers(EntityPlayer.class, new Predicate(){
				double distanceSq = -1;
				@Override
				public boolean apply(Object input) {
					if(!(input instanceof EntityPlayer)) return false;
					EntityPlayer player = (EntityPlayer) input;
					if(distanceSq == -1) {
						distanceSq = player.getPositionVector().squareDistanceTo(position);
						return player != possiblePlayer;
					}
					double newDist = player.getPositionVector().squareDistanceTo(position);
					if(distanceSq > newDist) {
						distanceSq = newDist;
						return player != possiblePlayer;
					}
					
					return false;
				}});
			if(list == null || list.isEmpty()) return null;
			EntityPlayer p = list.get(list.size()-1);
			if(p.getPositionVector().squareDistanceTo(position) > MAX_RADIUS*MAX_RADIUS) return null;
			return p;
		}
	}
	
	public static class ThoseSelector implements ISelector {
		public static final int RADIUS = 4;
		
		@Override
		public List<?> getSelected(ScriptInstance script, World world, Vec3 position) {
			Vec3 pos1 = position.addVector(-RADIUS, -RADIUS / 3, -RADIUS), 
					pos2 = position.addVector(RADIUS, RADIUS / 3, RADIUS);
			return world.getEntitiesWithinAABB(EntityLiving.class,
					new AxisAlignedBB(pos1.xCoord,pos1.yCoord,pos1.zCoord, pos2.xCoord, pos2.yCoord, pos2.zCoord));
		}
	}
	
	public static class ThatSelector implements ISelector {
		@Override
		public List<?> getSelected(ScriptInstance script, World world, Vec3 position) {
			if(!(script.getActualUser() instanceof Entity)) return null;
			Entity user = (Entity) script.getActualUser();
			MovingObjectPosition pos = user.rayTrace(64, 1);
			return Collections.singletonList(new BlockPosHit(pos.getBlockPos(), pos.sideHit));
		}		
	}
	
	public static class StoneSelector implements ISelector {
		public static final int MAX_RADIUS = 2;
		
		@Override
		public List<?> getSelected(ScriptInstance script, World world, Vec3 position) {
			return getAllBlockHits(script.getActualUser(), Blocks.stone);
		}
		
		@SuppressWarnings("unchecked")
		private List<BlockPosHit> getAllBlockHits(Object check, Block block) {
			if(!(check instanceof Entity)) return Collections.emptyList();
			return getBlocks(block, MAX_RADIUS, ((Entity)check).rayTrace(64, 1).getBlockPos());
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static List getBlocks(Block block, int radius, BlockPos centerpoint) {
		// TODO: Fix the facing code, it's broken. (now commented out)
		List out = new ArrayList();
		World world = Minecraft.getMinecraft().theWorld;
		int trueX = 0;
		int trueY = 0;
		int trueZ = 0;
		int relX = 0;
		int relY = 0;
		int relZ = 0;
		//int absX = 0;
		//int absY = 0;
		//int absZ = 0;
		for (int x = 0; x < radius * 2 + 1; x++) {
			relX = x - radius;
			trueX = relX + centerpoint.getX();
			for (int y = 0; y < radius * 2 + 1; y++) {
				relY = y - radius;
				trueY = relY + centerpoint.getY();
				for (int z = 0; z < radius * 2 + 1; z++) {
					relZ = z - radius;
					trueZ = relZ + centerpoint.getZ();

					Main.getLogger().info("Checking " + trueX + ", " + trueY + ", " + trueZ + " for blocks matching "
							+ block.getLocalizedName());
					if (world.getBlockState(new BlockPos(trueX, trueY, trueZ)).getBlock() == block) {
						//absX = Math.abs(relX);
						//absY = Math.abs(relY);
						//absZ = Math.abs(relZ);
						EnumFacing face = EnumFacing.UP;
						// if(absX > absY)
						// {
						// if(absX > absZ)
						// face = relX > 0 ? EnumFacing.WEST : EnumFacing.EAST;
						// }
						// else if(absY > absZ)
						// {
						// if(absY > absX)
						// face = relY > 0 ? EnumFacing.DOWN : EnumFacing.UP;
						// }
						// else if(absZ > absX)
						// {
						// if(absZ > absY)
						// face = relZ > 0 ? EnumFacing.NORTH :
						// EnumFacing.SOUTH;
						// }
						Main.getLogger().info("   -found matching block and calculated facing side");
						out.add(new BlockPosHit(new BlockPos(trueX, trueY, trueZ), face));
					}
				}
			}
		}
		return out;
	}
}

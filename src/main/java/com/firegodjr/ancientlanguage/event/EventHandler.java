package com.firegodjr.ancientlanguage.event;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.firegodjr.ancientlanguage.api.magic.IBlockStore;
import com.firegodjr.ancientlanguage.api.magic.IItemStore;
import com.firegodjr.ancientlanguage.entity.properties.ExtPropMagicExperience;
import com.firegodjr.ancientlanguage.utils.BlockInstance;
import com.firegodjr.ancientlanguage.utils.VersionUtils;

public class EventHandler {

	@SubscribeEvent
	public void onInteract(PlayerInteractEvent event) {
		if(event.entity.isSneaking()) return;
		ExtPropMagicExperience p = ExtPropMagicExperience.getExtProp(event.entity);
		if(event.action != Action.LEFT_CLICK_BLOCK && event.entityLiving.getHeldItem() != null
				&& event.entityLiving.getHeldItem().getItem() instanceof IItemStore) {
			ItemStack instance = event.entityLiving.getHeldItem();
			p.getProducer().pullEnergy((float) (p.getDefaultPush()*0.01));
			((IItemStore) ((ItemStack) instance).getItem()).pushEnergy(instance, p.getDefaultPush());
			event.setCanceled(true);
		} else if(event.action == Action.RIGHT_CLICK_BLOCK) {
			IBlockState state = event.entity.worldObj.getBlockState(event.pos);
			BlockInstance instance = new BlockInstance(event.entity.worldObj,
					VersionUtils.createVec3(event.pos), state);
			if(state instanceof IBlockStore) {
				p.getProducer().pullEnergy((float) (p.getDefaultPush()*0.01));
				((IBlockStore) state).pushEnergy(instance, p.getDefaultPush());
				event.setCanceled(true);
			} else if(((IBlockState) state).getBlock() instanceof IBlockStore) {
				p.getProducer().pullEnergy((float) (p.getDefaultPush()*0.01));
				((IBlockStore) state.getBlock()).pushEnergy(instance, p.getDefaultPush());
				event.setCanceled(true);
			}
		}
	}
}

package com.firegodjr.ancientlanguage.utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

import com.firegodjr.ancientlanguage.Main;
import com.firegodjr.ancientlanguage.api.magic.IEnergyProducer;
import com.firegodjr.ancientlanguage.entity.properties.ExtPropMagicExperience;

/**
 * A Magic Utility Helper Class
 */
public class MagicUtils {

	public static final float ENERGY_DAMAGE_DIVIDER = 20F;
	public static final float ENERGY_DRAIN_DIVIDER = 5F;

	public static final float MAX_EXAHUSTION = 40f;
	public static final float MAX_HEALTH = 20f;
	public static final float MAX_FOOD = 20f;
	public static final float MAX_PERCENTAGE = 1.0f;

	public static class EntityProducer implements IEnergyProducer {

		private final Entity entity;

		public EntityProducer(Entity entity) {
			this.entity = entity;
		}

		@Override
		public float useMagic(float energyToPull) {
			if(energyToPull <= 0) return 0;
			else if(energyToPull > MAX_PERCENTAGE) energyToPull *= 0.01;
			ExtPropMagicExperience prop = ExtPropMagicExperience.getExtProp(this.entity);
			if(prop != null) energyToPull /= prop.getLevel();
			float energyResult = 1;
			if(entity instanceof EntityLivingBase) {
				EntityLivingBase base = (EntityLivingBase) entity;
				base.addPotionEffect(new PotionEffect(Potion.hunger.getId(), (int) energyToPull*10, (int) energyToPull*10, false, false));
				energyResult = base.getRNG().nextFloat();
			}
			if(entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) entity;
				if(player.getFoodStats().getFoodLevel() > MAX_FOOD*0.25) {
					player.addExhaustion(energyToPull * MAX_EXAHUSTION);
					energyResult = 0;
				} else energyResult = player.getRNG().nextFloat();
			}
			if(energyResult > 0) {
				energyResult *= (MAX_HEALTH * 0.25);
				energyResult += (energyToPull * MAX_HEALTH);
				entity.attackEntityFrom(DamageSource.magic, energyResult);
			}
			if(prop != null) prop.addExperience(energyToPull);
			return energyResult;
		}
	}

	/**
	 * Creates an IEnergyProducer wrapper for an entity
	 *
	 * @param entity
	 *            The entity to create a wrapper for
	 */
	public static IEnergyProducer createProducerFor(Entity entity) {
		Main.getLogger().info("Creating Producer for Entity: " + entity);
		return new EntityProducer(entity);
	}
}

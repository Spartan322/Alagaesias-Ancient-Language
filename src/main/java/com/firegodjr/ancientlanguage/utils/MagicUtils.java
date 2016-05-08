package com.firegodjr.ancientlanguage.utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

import com.firegodjr.ancientlanguage.Main;
import com.firegodjr.ancientlanguage.api.magic.IEnergyProducer;
import com.firegodjr.ancientlanguage.api.magic.IMagicUser;
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

	public static class EntityProducer implements IMagicUser {

		private final Entity entity;
		private final ExtPropMagicExperience property;

		public EntityProducer(Entity entity) {
			this.entity = entity;
			this.property = ExtPropMagicExperience.getExtProp(this.entity);
		}

		@Override
		public boolean pullEnergy(float energyToPull) {
			if(energyToPull <= 0) return false;
			else if(energyToPull > MAX_PERCENTAGE) energyToPull *= 0.01;
			if(this.property != null) energyToPull /= this.property.getLevel(); // energyToPull decreases based on level
			float energyResult = 1;
			if(entity instanceof EntityLivingBase) {
				EntityLivingBase base = (EntityLivingBase) this.entity;
				base.addPotionEffect(new PotionEffect(Potion.hunger.getId(), (int) energyToPull*10, (int) energyToPull*10, false, false));
				energyResult = base.getRNG().nextFloat();
			}
			if(entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) this.entity;
				if(player.getFoodStats().getFoodLevel() > MAX_FOOD*0.25) {
					player.addExhaustion(energyToPull * MAX_EXAHUSTION);
					energyResult = 0; // don't harm player
				} else energyResult = player.getRNG().nextFloat();
			}
			if(energyResult > 0) {
				energyResult *= (MAX_HEALTH * 0.25); // multiplied by 1/4 health
				energyResult += (energyToPull * MAX_HEALTH); // add energy pull multiplied by health
				entity.attackEntityFrom(DamageSource.magic, energyResult);
			}
			if(this.property != null) this.property.addExperience(energyToPull); // adds experience based on energyToPull
			return energyResult > 0;
		}

		@Override
		public int getLevel() {
			return this.property.getLevel();
		}

		@Override
		public float getExperience() {
			return this.property.getExperience();
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

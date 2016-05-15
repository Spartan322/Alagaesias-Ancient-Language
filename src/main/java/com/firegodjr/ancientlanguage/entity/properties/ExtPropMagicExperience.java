package com.firegodjr.ancientlanguage.entity.properties;

import java.text.NumberFormat;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import com.firegodjr.ancientlanguage.api.magic.IEnergyProducer;
import com.firegodjr.ancientlanguage.utils.MagicUtils;

public final class ExtPropMagicExperience implements IExtendedEntityProperties {

	public static final String NAME = "ExtendedPropMagicExp";

	public static final String EXP_NBT = "Experience";
	public static final String CURRENTLEVEL_NBT = "CurrentLevel";
	public static final String DEFAULTPUSH_NBT= "DefaultPush";

	protected Entity entity;
	protected IEnergyProducer producer;

	private float exp = 0.0f;
	private int currentLevel = 1;
	private int defaultPush = 1;

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		compound.setFloat(EXP_NBT, this.getExperience());
		compound.setInteger(CURRENTLEVEL_NBT, this.getLevel());
		compound.setInteger(DEFAULTPUSH_NBT, this.getDefaultPush());
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		this.setExperience(compound.getFloat(EXP_NBT));
		this.setLevel(compound.getInteger(CURRENTLEVEL_NBT));
		this.setDefaultPush(compound.getInteger(DEFAULTPUSH_NBT));

	}

	@Override
	public void init(Entity entity, World world) {
		this.entity = entity;
		this.producer = MagicUtils.createProducerFor(this.entity);
	}

	public static ExtPropMagicExperience getExtProp(Entity ent) {
		if (ent == null)
			return null;
		return (ExtPropMagicExperience) ent.getExtendedProperties(NAME);
	}

	/**
	 * Adds to the experience level of {@link #entity}
	 *
	 * @param exp
	 *            The experience to add
	 */
	public void addExperience(float exp) {
		this.exp += exp;
		while (this.exp > 1) {
			this.currentLevel++;
			this.exp -= 1;
		}
		if(this.exp == -0) { // Prevent negative zero, it just looks weird later and adds random sign for no reason
			this.exp = 0;
			return;
		}
		while (this.exp < 0) {
			this.currentLevel--;
			this.exp += 1;
		}
	}

	/**
	 * Sets the experience level of {@link #entity}
	 *
	 * @param exp
	 *            The experience to set
	 */
	public void setExperience(float exp) {
		this.exp = exp;
		while (this.exp > 1) {
			this.currentLevel++;
			this.exp -= 1;
		}
		if(this.exp == -0) { // Prevent negative zero, it just looks weird later and adds random sign for no reason
			this.exp = 0;
			return;
		}
		while (this.exp < 0) {
			this.currentLevel--;
			this.exp += 1;
		}
	}

	public void setLevel(int level) {
		this.currentLevel = Math.max(1, level);
	}

	public void setDefaultPush(int push) {
		this.defaultPush = Math.max(0, push);
	}

	/**
	 * Retrieves {@link #entity}'s current level
	 */
	public int getLevel() {
		return this.currentLevel;
	}

	/**
	 * Retrieves {@link #entity}'s current experience
	 */
	public float getExperience() {
		return this.exp;
	}

	public int getDefaultPush() {
		return this.defaultPush;
	}

	public IEnergyProducer getProducer() {
		return this.producer;
	}

	/**
	 * Retrieves {@link #entity}'s current experience in percentage form
	 */
	public String getExperiencePercent() {
		return NumberFormat.getPercentInstance().format(this.getExperience());
	}
}

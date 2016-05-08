package com.firegodjr.ancientlanguage.magic;

import net.minecraft.util.MathHelper;

import com.firegodjr.ancientlanguage.api.magic.IEnergyProducer;
import com.firegodjr.ancientlanguage.api.magic.IMagicUser;

/**
 * Class for handling magic and energy related data
 */
public class MagicData {

	private IEnergyProducer producer;
	private Object actualUser;
	private boolean auIsSet;
	private float magicMultiplier = 1;

	public MagicData(IEnergyProducer producer, Object user) {
		this.producer = producer;
		this.actualUser = user;
		this.auIsSet = true;
	}

	public MagicData(IEnergyProducer producer, boolean actualUser) {
		this(producer, producer);
		this.auIsSet = actualUser;
	}

	public MagicData(IEnergyProducer producer) {
		this(producer, false);
	}

	public void addMagicMultipler(float multipler) {
		this.magicMultiplier += multipler;
	}

	public boolean performMagic(float reqEnergy) {
		return this.producer.pullEnergy(MathHelper.clamp_float(reqEnergy * this.magicMultiplier, 0, 1));
	}

	public boolean performMagic(int reqEnergy) {
		return this.producer.pullEnergy(Math.max(reqEnergy * this.magicMultiplier, 1));
	}

	public boolean isMagicUser() {
		return this.producer instanceof IMagicUser;
	}

	public boolean isProducer(Class<?> clazz) {
		return clazz.isInstance(this.producer);
	}

	public int getUserLevel() {
		return ((IMagicUser) this.producer).getLevel();
	}

	public float getUserExperience() {
		return ((IMagicUser) this.producer).getExperience();
	}

	public Object getActualUser() {
		return this.actualUser;
	}

	public MagicData setActualUser(Object user) {
		if (this.auIsSet)
			return this;
		this.actualUser = user;
		this.auIsSet = true;
		return this;
	}
}

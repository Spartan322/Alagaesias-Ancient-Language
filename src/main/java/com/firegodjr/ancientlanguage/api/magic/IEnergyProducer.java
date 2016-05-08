package com.firegodjr.ancientlanguage.api.magic;

/**
 * An interface for objects implementing magical energy production
 */
public interface IEnergyProducer {

	/**
	 * Pulls magic energy from user
	 *
	 * @param energyToPull
	 *            The percentage of energy to pull compared to what the producer has
	 *
	 * @return Whether IEnergyProducer was harmed
	 */
	public boolean pullEnergy(float energyToPull);

}

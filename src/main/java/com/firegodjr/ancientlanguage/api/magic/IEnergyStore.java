package com.firegodjr.ancientlanguage.api.magic;

/**
 * An interface for object implementing storing and pulling energy
 */
public interface IEnergyStore extends IEnergyProducer {

	/**
	 * Retrieves level of stored energy
	 */
	public int getStored();

	/**
	 * Retrieves max amount of storable energy, or -1 for unlimited
	 */
	public int getMaxStorable();

	/**
	 * Pushes energy into IEnergyStore
	 *
	 * @param energyToPut
	 *            Energy to put into store
	 * @return Energy not saved
	 */
	public int pushEnergy(int energyToPut);

}

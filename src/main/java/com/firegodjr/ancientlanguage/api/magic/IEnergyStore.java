package com.firegodjr.ancientlanguage.api.magic;

/**
 * An interface for object implementing storing and pulling energy
 *
 * @param T
 *            instance class
 */
interface IEnergyStore<T> {

	/**
	 * Retrieves level of stored energy
	 *
	 * @param instanceObject
	 *            The instance to retrieve from
	 */
	public int getStored(T instanceObject);

	/**
	 * Retrieves max amount of storable energy, or -1 for unlimited
	 *
	 * @param instanceObject
	 *            The instance to retrieve from
	 */
	public int getMaxStorable(T instanceObject);

	/**
	 * Pushes energy into IEnergyStore
	 *
	 * @param energyToPut
	 *            Energy to put into store
	 * @param instanceObject
	 *            The instance to push to
	 * @return Energy not saved
	 */
	public int pushEnergy(T instanceObject, int energyToPut);

	/**
	 * Pulls energy from IEnergyStore
	 *
	 * @param energyToPut
	 *            Energy to pull
	 * @param instanceObject
	 *            The instance to pull from
	 * @return true if object was damaged (or if stored is less then pull),
	 *         false otherwise
	 */
	public boolean pullEnergy(T instanceObject, int energyToPull);

}

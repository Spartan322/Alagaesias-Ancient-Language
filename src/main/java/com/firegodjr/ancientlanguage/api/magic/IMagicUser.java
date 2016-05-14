package com.firegodjr.ancientlanguage.api.magic;

/**
 * An interface for implementing magic using objects
 */
public interface IMagicUser extends IEnergyProducer {

	/**
	 * Retrieves user's level
	 */
	public int getLevel();

	/**
	 * Retrieves user's experience
	 */
	public float getExperience();

}

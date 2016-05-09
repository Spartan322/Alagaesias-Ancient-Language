package com.firegodjr.ancientlanguage.tileentity;

import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;

import com.firegodjr.ancientlanguage.ParticleHandler;

/**
 * A tile entity to handle the updating particle effects of the ghostlight
 */
public class TileEntityGhostLight extends TileEntity implements IUpdatePlayerListBox {

	@Override
	public void update() {
		ParticleHandler.ghostLight(this.xCoord, this.yCoord, this.zCoord, 1, worldObj);
	}

}

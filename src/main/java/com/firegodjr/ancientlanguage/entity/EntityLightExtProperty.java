package com.firegodjr.ancientlanguage.entity;

import com.firegodjr.ancientlanguage.Main;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class EntityLightExtProperty implements IExtendedEntityProperties {

	public static final String IDENT = Main.MODID+".EntityLightExtProperty";
	
	@SuppressWarnings("unused")
	private final Entity entity;
	private boolean hasLight;
	
	public EntityLightExtProperty(Entity entity) {
		this.entity = entity;
	}
	
	public static final void registerProp(Entity entity) {
		entity.registerExtendedProperties(IDENT, new EntityLightExtProperty(entity));
		
	}
	
	public static final EntityLightExtProperty get(Entity entity) {
		return (EntityLightExtProperty) entity.getExtendedProperties(IDENT);
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setBoolean(IDENT+".light", this.hasLight);
		compound.setTag(IDENT+".tag", tag);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound tag = (NBTTagCompound) compound.getTag(IDENT+".tag");
		this.hasLight = tag.getBoolean(IDENT+".light");
	}

	@Override
	public void init(Entity entity, World world) {
	}
	
	public void setHasLight(boolean light) {
		this.hasLight = light;
	}

	public boolean hasLight() {
		return this.hasLight;
	}
	
	public static final boolean hasLight(Entity entity) {
		return get(entity).hasLight();
	}
}

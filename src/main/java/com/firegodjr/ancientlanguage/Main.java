package com.firegodjr.ancientlanguage;

import org.apache.logging.log4j.Logger;

import com.firegodjr.ancientlanguage.command.CommandCast;
import com.firegodjr.ancientlanguage.command.CommandExp;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod(modid = Main.MODID, name = Main.MODNAME, version = Main.VERSION, guiFactory = Main.GUIFACORY)
public class Main {
	
	//////////////////////////////////////////
	public static final String MODID = "ancientlanguage";
	public static final String MODNAME = "Ancient Language";
	public static final String VERSION = "1.0";
	public static final String GUIFACORY = "com.firegodjr.ancientlanguage.client.ModGuiFactory";
	//////////////////////////////////////////
	public static final String CLIENTPROXY = "com.firegodjr.ancientlanguage.ClientProxy";
	public static final String SERVERPROXY = "com.firegodjr.ancientlanguage.ServerProxy";
	//////////////////////////////////////////

	@Instance(MODID)
	public static Main instance;

	@SidedProxy(clientSide=CLIENTPROXY, serverSide=SERVERPROXY)
	public static CommonProxy proxy;
	
	private Config config;
	private Logger logger;

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		//Configuration file settings and variables
		config = new Config(e); 
		logger = e.getModLog();
		proxy.preInit(e);
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		proxy.init(e);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		proxy.postInit(e);
	}
	
	@EventHandler
	public void serverLoad(FMLServerStartingEvent e) {
		e.registerServerCommand(new CommandCast());
		e.registerServerCommand(new CommandExp());
	}
	
	/**
	 * Retrieves config object from instance
	 */
	public static Config getConfig() {
		return instance.config;
	}
	
	/**
	 * Retrieves mod specific logger
	 * @return
	 */
	public static Logger getLogger() {
		return instance.logger;
	}
}
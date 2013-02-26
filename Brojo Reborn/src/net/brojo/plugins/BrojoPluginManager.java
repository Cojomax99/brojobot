package net.brojo.plugins;

import java.lang.reflect.Method;
import java.util.HashMap;

import net.brojo.message.Message;

/**
 * Handles the instantiation of brojo plugins
 * 
 * @author Cojomax99
 */
public class BrojoPluginManager {

	/**
	 * Holds all loaded plugin Class objects for processing when a message is received, mapped to whether the plugin is loaded or not
	 */
	private static HashMap<Class<? extends BrojoPlugin>, Boolean> pluginInstances = new HashMap<Class<? extends BrojoPlugin>, Boolean>();
	
	/**
	 * Singleton instance
	 */
	private static BrojoPluginManager instance;
	
	private BrojoPluginManager() {
		
	}
	
	/**
	 * Get the singleton instance
	 * @return Singleton instance
	 */
	public BrojoPluginManager getInstance() {
		if (instance == null) {
			instance = new BrojoPluginManager();
		}
		
		return instance;
	}
	
	/**
	 * Called by the main implementer, handles all plugin recognition/instantiation
	 * @param m Message received from server
	 */
	public static void onMessageReceived(Message m) {
		for (Class<? extends BrojoPlugin> clazz : pluginInstances.keySet()) {
			try {
				Method accepts = clazz.getDeclaredMethod("accepts", Boolean.TYPE);
				Object o = accepts.invoke(null, m);
				
				if ((Boolean)o) {
					BrojoPlugin plugin = clazz.newInstance();
					plugin.onActivated(m);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Sets the mapping in pluginInstances so that this plugin cannot be "loaded"
	 * @param plugin BrojoPlugin to be "unloaded"
	 */
	public static void unloadPlugin(Class<? extends BrojoPlugin> plugin) {
		System.err.println("Unloading plugin: " + pluginInstances.get(plugin));
		pluginInstances.put(plugin, Boolean.valueOf(false));
	}
	
	/**
	 * Sets the mapping in pluginInstances so that this plugin can be "loaded"
	 * @param plugin BrojoPlugin to be "loaded"
	 */
	public static void loadPlugin(Class<? extends BrojoPlugin> plugin) {
		System.err.println("Loading plugin: " + pluginInstances.get(plugin));
		pluginInstances.put(plugin, Boolean.valueOf(true));
	}

}

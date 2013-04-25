package net.brojo.pluginimpl;

import java.lang.reflect.Method;
import java.util.HashMap;

import net.brojo.irc.IConnector;
import net.brojo.message.Message;
import net.brojo.plugins.BrojoPlugin;

/**
 * Handles the instantiation of brojo plugins
 */
public class BrojoPluginManager {

	/**
	 * Holds all loaded plugin Class objects for processing when a message is received, mapped to whether the plugin is loaded or not
	 */
	private HashMap<Class<? extends BrojoPlugin>, Boolean> pluginInstances = new HashMap<Class<? extends BrojoPlugin>, Boolean>();
	
	/**
	 * Connector instance
	 */
	private IConnector impl;
	
	
	public BrojoPluginManager(IConnector impl) {
		this.impl = impl;
	}
	
	/**
	 * Loads plugins
	 */
	public void loadPlugins() {
		for (Class<? extends BrojoPlugin> c : BrojoPluginLoader.pluginList) {
			loadPlugin(c);
		}
	}

	/**
	 * Called by the main implementer, handles all plugin recognition/instantiation
	 * @param m Message received from server
	 */
	public void onMessageReceived(Message m) {
		
		for (Class<? extends BrojoPlugin> clazz : pluginInstances.keySet()) {
			try {
				Method accepts = clazz.getDeclaredMethod("accepts", Message.class);
				Object o = accepts.invoke(null, m);
				
				if ((Boolean)o) {
					BrojoPlugin plugin = clazz.newInstance();
					plugin.onActivated(impl, m);
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
	public void unloadPlugin(Class<? extends BrojoPlugin> plugin) {
		System.err.println("Unloading plugin: " + pluginInstances.get(plugin));
		pluginInstances.put(plugin, Boolean.valueOf(false));
	}
	
	/**
	 * Sets the mapping in pluginInstances so that this plugin can be "loaded"
	 * @param plugin BrojoPlugin to be "loaded"
	 */
	public void loadPlugin(Class<? extends BrojoPlugin> plugin) {
		System.err.println("Loading plugin: " + pluginInstances.get(plugin));
		pluginInstances.put(plugin, Boolean.valueOf(true));
	}

}

package net.brojo.irc;

import java.io.FileNotFoundException;

import net.brojo.connection.ConnectionManager;
import net.brojo.pluginimpl.BrojoPluginLoader;

public class BrojoBot {

	/**
	 * Static instance to use when referencing this class as a singleton
	 */
	private static BrojoBot instance;

	/**
	 * Stores information for all server connections
	 */
	private ConfigManager config;

	/**
	 * Simple constructor that initializes the configuration manager
	 */
	private BrojoBot() {
		try {
			config = new ConfigManager("BrojoBot.json");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Singleton instance of BrojoBot
	 * @return Get an instance of BrojoBot
	 */
	public static BrojoBot getInstance() {
		if (instance == null) {
			instance = new BrojoBot();
		}

		return instance;
	}

	/**
	 * 
	 * @return Instance of the configuration manager
	 */
	public ConfigManager getConfigManager() {
		return this.config;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			BrojoPluginLoader.loadPlugins();
			BrojoBot brojo = BrojoBot.getInstance();
			brojo.getConfigManager().load();
			ConnectionManager.initConnections();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}

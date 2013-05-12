package net.brojo.plugins;

import net.brojo.connection.IConnector;
import net.brojo.message.Message;

/**
 * Simple abstract class that will be extended by any brojo plugin class.
 */
public abstract class BrojoPlugin {
	/**
	 * 
	 * @return Name of this plugin
	 */
	public abstract String getName();
	
	/**
	 * 
	 * @return Version of this plugin
	 */
	public abstract String getVersion();
	
	/**
	 * 
	 * @return Description of this plugin
	 */
	public abstract String getDescription();
	
	/**
	 * NOTE: NEEDS TO BE OVERRIDDEN BY ANY PLUGIN THAT AIMS TO BE LOADED
	 * @param m Message received
	 * @return whether this plugin should be loaded
	 */
	public static boolean accepts(Message m) {
		return false;
	}
	
	/**
	 * Triggered when any of the registered keywords or commands are detected
	 * @param impl Implementor instance
	 * @param message Message containing all details of said received message
	 * @return true if completed successfully
	 */
	public abstract boolean onActivated(IConnector impl, Message message);
}

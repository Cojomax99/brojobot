package net.brojo.connection;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This class manages all connections and keeps a list of all servers, connected and disconnected.
 *
 */
public class ConnectionManager {
	
	/**
	 * Collection of different connections mapped to local server names
	 */
	private static Map<String, ConnectionImpl> connections = new HashMap<String, ConnectionImpl>();
	
	/**
	 * Register a connection to be loaded
	 * @param cnxn Connection
	 */
	public static void registerConnection(ConnectionImpl cnxn) {
		connections.put(cnxn.getServerInfo().getLocalName(), cnxn);
	}
	
	/**
	 * Initiate connection to the server with given server name
	 * @param serverName Name of the server to initiate connection on
	 */
	public static void startConnection(String serverName) {
		connections.get(serverName).start();
	}
	
	/**
	 * Terminate connection to the server with given server name by sending the QUIT message, then stopping the i/o threads
	 * @param serverName Name of the server to end connection of
	 */
	public static void endConnection(String serverName) {
		try {
			connections.get(serverName).terminate();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Starts all connections that should be connected to on startups
	 */
	public static void initConnections() {
		for (Entry<String, ConnectionImpl> connection : connections.entrySet()) {
			if (connection.getValue().getServerInfo().shouldConnectOnStartup()) {
				connection.getValue().start();
			}
		}
	}
}

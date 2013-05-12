package net.brojo.irc;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import net.brojo.connection.ConnectionImpl;
import net.brojo.connection.ConnectionManager;
import net.brojo.connection.ServerInfo;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

public class ConfigManager {
	
	/**
	 * Holds separate info for each connection
	 */
	private Map<String, ServerInfo> servers = new HashMap<String, ServerInfo>();
	
	/**
	 * File location of the JSON config file
	 */
	private String filePath;
	
	/**
	 * GSON instance for this class
	 */
	private Gson gson;
	
	/**
	 * Simple constructor that loads from a JSON file
	 * @param fileName JSON file to load from
	 */
	public ConfigManager(String fileName) {
		this.filePath = fileName;
		gson = new Gson();
	}
	
	/**
	 * Load the JSON configuration file into the program
	 * @throws FileNotFoundException 
	 */
	public void load() throws FileNotFoundException {
		JsonReader reader = new JsonReader(new FileReader(filePath));		
		JsonParser parser = new JsonParser();		
		JsonArray array = parser.parse(reader).getAsJsonArray();
		
		for (JsonElement elem : array) {
			ServerInfo info = gson.fromJson(elem, ServerInfo.class);
			servers.put(info.getLocalName(), info);
			ConnectionManager.registerConnection(new ConnectionImpl(info));
		}
	}	
}

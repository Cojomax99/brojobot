package net.brojo.connection;

import java.util.List;

/**
 * Simple class that contains getters and setters for all information for a single server instance
 *
 */
public class ServerInfo {

	/**
	 * Username of user
	 */
	private String username;
	
	/**
	 * Real name of user
	 */
	private String realname;
	
	/**
	 * Nick that actually shows up in-game
	 */
	private String nick;
	
	/**
	 * Password of the user
	 */
	private String nickServPass;
	
	/**
	 * Password for the server
	 */
	private String serverPass;
	
	/**
	 * Version of the user
	 */
	private String version;
	
	/**
	 * Quit message that is displayed when the user sends a QUIT command
	 */
	private String quitmsg;
	
	/**
	 * Channels this user is in
	 */
	private List<String> channels;
	
	/**
	 * Server to connect to, ex: irc.esper.net
	 */
	private String server;
	
	/**
	 * Port to connect to, ex: 6667
	 */
	private int port;
	
	/**
	 * Should a connection to this network be initiated on startup?
	 */
	private boolean connectOnStartup;
	
	/**
	 * Name used locally as a unique "id" for a server.
	 */
	private String localName;

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the realname
	 */
	public String getRealname() {
		return realname;
	}

	/**
	 * @param realname the realname to set
	 */
	public void setRealname(String realname) {
		this.realname = realname;
	}

	/**
	 * @return the nick
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * @param nick the nick to set
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * @return the nickServPass
	 */
	public String getNickServPass() {
		return nickServPass;
	}

	/**
	 * @param nickServPass the nickServPass to set
	 */
	public void setNickServPass(String nickServPass) {
		this.nickServPass = nickServPass;
	}

	/**
	 * @return the serverPass
	 */
	public String getServerPass() {
		return serverPass;
	}

	/**
	 * @param serverPass the serverPass to set
	 */
	public void setServerPass(String serverPass) {
		this.serverPass = serverPass;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the quitmsg
	 */
	public String getQuitmsg() {
		return quitmsg;
	}

	/**
	 * @param quitmsg the quitmsg to set
	 */
	public void setQuitmsg(String quitmsg) {
		this.quitmsg = quitmsg;
	}

	/**
	 * @return the channels
	 */
	public List<String> getChannels() {
		return channels;
	}

	/**
	 * @param channels the channels to set
	 */
	public void setChannels(List<String> channels) {
		this.channels = channels;
	}

	/**
	 * @return the server
	 */
	public String getServer() {
		return server;
	}

	/**
	 * @param server the server to set
	 */
	public void setServer(String server) {
		this.server = server;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return the connectOnStartup
	 */
	public boolean shouldConnectOnStartup() {
		return connectOnStartup;
	}

	/**
	 * @param connectOnStartup the connectOnStartup to set
	 */
	public void setConnectOnStartup(boolean connectOnStartup) {
		this.connectOnStartup = connectOnStartup;
	}

	/**
	 * @return the localName
	 */
	public String getLocalName() {
		return localName;
	}

	/**
	 * @param localName the localName to set
	 */
	public void setLocalName(String localName) {
		this.localName = localName;
	}
	
}

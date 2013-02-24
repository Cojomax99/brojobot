package net.brojo.irc;

public interface Bot {

	/**
	 * method that is an intermediary between the input and output threads
	 * @param msg Message to send
	 */
	public void send(String msg);
	
	/**
	 * method that is an intermediary between the input and output threads
	 * @param recipient user/channel to send the message to
	 * @param contents contents of message to send
	 */
	public void send(String recepient, String contents);
	
	/**
	 * method that is an intermediary between the input and output threads, implemented using String.format
	 * @param contents message contents to send
	 * @param args String.format arguments
	 */
	public void sendf(String contents, Object...args);
	
	/**
	 * Simple getter for a UserInfo instance
	 * @return getUserInfo
	 */
	public UserInfo getUserInfo();
}

package net.brojo.irc;

import net.brojo.message.Message;

/**
 * Interface to be implemented by a client
 *
 */
public interface IConnector {

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
	 * Method that sends a Message in Message format
	 * @param msg Message to send
	 */
	public void send(Message msg);
	
	/**
	 * Simple getter for a UserInfo instance
	 * @return getUserInfo
	 */
	public UserInfo getUserInfo();
	
	/**
	 * Called when a message is received from the server
	 * @param serverMsg Raw message received from the server
	 * @param msg Raw message encapsulated into a Message object
	 */
	public void onMessageReceived(String serverMsg, Message msg);

	/**
	 * Changes the nick of the user
	 * @param nick New nick
	 */
	public void setNick(String nick);
}

package net.brojo.irc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

import net.brojo.innards.ThreadedInput;
import net.brojo.innards.ThreadedOutput;
import net.brojo.message.Message;
import net.brojo.pluginimpl.BrojoPluginLoader;
import net.brojo.pluginimpl.BrojoPluginManager;

public class BrojoBot implements IConnector {

	/**
	 * UserInfo object that stores all data about this user
	 */
	private UserInfo userInfo;
	
	/**
	 * Thread for receiving messages from server
	 */
	private ThreadedInput input;
	
	/**
	 * Thread for sending messages to the server
	 */
	private ThreadedOutput output;
	
	/**
	 * Plugin manager instance for this implementor
	 */
	public BrojoPluginManager pluginManager;
	
	public BrojoBot() {
		try {
			pluginManager = new BrojoPluginManager(this);
			BrojoPluginLoader.registerPluginManager(pluginManager);
			pluginManager.loadPlugins();
			userInfo = new UserInfo("BrojoBot.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Initiate IRC connection
	 */
	private void start() {
		try{
			//load plugins
			
			final Socket sock = new Socket("irc.esper.net",6667);

			input = new ThreadedInput(this, new BufferedReader(new InputStreamReader(sock.getInputStream())));
			output = new ThreadedOutput(this, new BufferedWriter(new OutputStreamWriter(sock.getOutputStream())));

			input.start();
			output.start();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public UserInfo getUserInfo() {
		return this.userInfo;
	}
	
	/**
	 * Sends a message to the server with no intended recipient other than the server itself
	 * @param contents message contents to send
	 */
	public void send(String contents) {
		output.send(new Message(null, null, contents));
	}
	
	/**
	 * Sends a message to the server with an intended recipient
	 * @param recipient user/channel to send the message to
	 * @param contents message contents to send
	 */
	public void send(String recipient, String contents) {
		output.send(new Message(null, recipient, contents));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BrojoPluginLoader.loadPlugins();
		BrojoBot brojo = new BrojoBot();
		
		brojo.start();
	}

	@Override
	public void sendf(String contents, Object... args) {
		send(String.format(contents, args));		
	}

	@Override
	public void onMessageReceived(String serverMsg, Message msg) {
		pluginManager.onMessageReceived(msg);		
	}

	@Override
	public void setNick(String nick) {
		this.userInfo.setNick(nick);
		Commands.NICK(this, nick);
	}

	@Override
	public void onCTCPReceived(String sender, String msg) {
		if(msg.toLowerCase().equals("version")){
			Commands.NOTICE(this, sender, "VERSION BrojoBot "+userInfo.getVersion());
		}
	}

}

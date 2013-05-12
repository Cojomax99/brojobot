package net.brojo.connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import net.brojo.innards.ThreadedInput;
import net.brojo.innards.ThreadedOutput;
import net.brojo.irc.Commands;
import net.brojo.message.Message;
import net.brojo.pluginimpl.BrojoPluginLoader;
import net.brojo.pluginimpl.BrojoPluginManager;

public class ConnectionImpl implements IConnector {

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
	
	/**
	 * Server information data storage
	 */
	private ServerInfo serverInfo;
	
	/**
	 * Constructor that sets attributes of this implementor via a ServerInfo object, obtained from the JSON config file.<br>
	 * Also initializes the input and output threads, and the plugin manager.
	 * @param info ServerInfo instance
	 */
	public ConnectionImpl(ServerInfo info) {
		
		serverInfo = info;
		
		final Socket sock;
		try {
			sock = new Socket(serverInfo.getServer(), serverInfo.getPort());

			input = new ThreadedInput(this, new BufferedReader(new InputStreamReader(sock.getInputStream(), "UTF8")));
			output = new ThreadedOutput(this, new BufferedWriter(new OutputStreamWriter(sock.getOutputStream(), "UTF8")));
			
			pluginManager = new BrojoPluginManager(this);
			BrojoPluginLoader.registerPluginManager(pluginManager);
			pluginManager.loadPlugins();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

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
		Commands.NICK(this, nick);
	}

	@Override
	public void send(Message msg) {
		output.send(msg);
	}

	@Override
	public void onCTCPReceived(String sender, String msg) {
		if(msg.toLowerCase().equals("version")){
			Commands.NOTICE(this, sender, "VERSION BrojoBot "+ this.serverInfo.getVersion());
		}
	}

	public void start() {
		input.start();
		output.start();
	}

	public void terminate() throws InterruptedException {
		Commands.QUIT(this);
		Thread.sleep(1000);
		input.stopThread();
		output.stopThread();
	}

	@Override
	public ServerInfo getServerInfo() {
		return this.serverInfo;
	}
}

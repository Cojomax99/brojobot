package net.brojo.irc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import net.brojo.innards.ThreadedInput;
import net.brojo.innards.ThreadedOutput;
import net.brojo.message.Message;

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
	
	public BrojoBot() {
		try {
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
			final Socket sock = new Socket("irc.esper.net",6667);

			input = new ThreadedInput(this, new BufferedReader(new InputStreamReader(sock.getInputStream())));
			output = new ThreadedOutput(this, new BufferedWriter(new OutputStreamWriter(sock.getOutputStream())));

			input.start();
			output.start();
			
			System.out.println("hm");
		
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
		BrojoBot brojo = new BrojoBot();
		
		brojo.start();

	}

	@Override
	public void sendf(String contents, Object... args) {
		send(String.format(contents, args));		
	}

}

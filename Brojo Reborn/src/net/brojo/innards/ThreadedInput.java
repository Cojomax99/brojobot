package net.brojo.innards;

import java.io.BufferedReader;
import java.io.Console;

import net.brojo.irc.IConnector;
import net.brojo.message.Message;

public class ThreadedInput extends Thread {

	private boolean isRunning = false;
	
	private BufferedReader reader;
	
	private IConnector bot;

	public ThreadedInput(IConnector bot, BufferedReader reader) {
		super("BrojoBot Input Thread");
		this.reader = reader;
		this.bot = bot;
		isRunning = true;
	}

	@Override
	public void run() {
		boolean loggedIn = false;
		net.brojo.message.Message message = null;
		String serverMsg = null;
		
		while (isRunning) {
			try {
				if (!loggedIn) {

					bot.send(String.format("USER %s  8 * : %s", bot.getUserInfo().getUserName(), bot.getUserInfo().getVersion()));
					bot.send(String.format("NICK %s", bot.getUserInfo().getNick()));
					loggedIn = true;
				}
				sleep(1L);

				while ((serverMsg = reader.readLine()) != null) {
					System.out.println(serverMsg);
					
					parseIO(serverMsg);

					//TODO: Eventually outsource this to a simple bot.processInput call
					if (serverMsg.split(" :", 1)[0].contains(" PRIVMSG ")) {
						message = Message.createMessageFromRaw(serverMsg);
						
						if (message.getContents().toLowerCase().equals("r brojobot")) {
							bot.send(message.getRecipient(), "Hello, " + message.getSender());
						}
						
						
						//bot.send(message.getSender(), message.getContents());
						
						message = null;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		
			//do stuff
		}
	}
	
	/**
	 * @param output
	 *            This is the output from the BufferedReader from the Socket
	 *            (Encompasses all channels)
	 */
	private synchronized void parseIO(String output) {
		// Answer pings
		if (output.startsWith("PING :")) {
			bot.send("PONG " + output.substring(5));
		}

		// fired when succesfully connected to the server.
		if (output.split(" ")[1].contains("376")) {
			System.out.println("Successfully connected to server " + output.split(" ")[0].substring(1));
		//	joinChans();
		}
		// username in use
		if (output.split(" ")[1].contains("433")) {
		//	nick = output.split(" ")[3] + "_";
			bot.send("NICK " + bot.getUserInfo().getNick());
		}

		// join on invite
		if (output.split(" :", 1)[0].contains(" INVITE ")) {
			String target = output.split(bot.getUserInfo().getNick() + " :", 2)[1];
			System.out.println("Joining " + target);
			bot.sendf("PRIVMSG nickserv identify %s", bot.getUserInfo().getPass());
			bot.send("JOIN " + target);
			bot.send(target, "o7");
		}
	}

}

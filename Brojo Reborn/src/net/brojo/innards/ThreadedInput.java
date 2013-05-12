package net.brojo.innards;

import java.io.BufferedReader;

import net.brojo.connection.IConnector;
import net.brojo.irc.Commands;
import net.brojo.irc.IRCLine;
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
	
	public void stopThread() {
		this.isRunning = false;
	}

	@Override
	public void run() {
		boolean loggedIn = false;
		String serverMsg = null;

		while (isRunning) {
			try {
				if (!loggedIn) {

					Commands.USER(bot);
					Commands.NICK(bot);
					loggedIn = true;
				}
				sleep(1L);
				
				if ((serverMsg = reader.readLine()) == null) {
					isRunning=false;
					break;
				}

				while ((serverMsg = reader.readLine()) != null) {
					System.out.println(serverMsg.trim());
					IRCLine line = IRCLine.parse(serverMsg);
					parseIO(line);
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
	private void parseIO(IRCLine line) {
		//TODO: Eventually outsource this to a simple bot.processInput call
		if (line.getCommand().equals("PRIVMSG")) {
			Message message = Message.createMessageFromRaw(line.getRaw());

			if (message.getContents().toLowerCase().equals("r brojobot")) {
				bot.send(message.getRecipient(), "Hello, " + message.getSender());
			}

			if(message.getContents().toLowerCase().startsWith("\u0001")){
				bot.onCTCPReceived(message.getSender(), message.getContents().toLowerCase().split("\u0001")[1]);
			}else{
				bot.onMessageReceived(line.getRaw(), message);
			}
			return;
		}
		
		if (line.getCommand().equals("JOIN")) {
			String nick = line.getNick();

			if (nick.toLowerCase().startsWith("frox")) {
				String channel = line.getParam(0);
				bot.send(channel, "Who's that lady?");
			}
		}
		
		if (line.getCommand().equals("PING")) {
			Commands.PONG(bot, line.getParam(0));
			return;
		}
		
		// fired when successfully connected to the server.
		// TODO add delay after identifying so we can join invite only channels, perhaps listen for NickServ's reply
		// Join channels!
		if (line.getCommand().equals("376")) { // ENDOFMOTD
			bot.sendf("PRIVMSG nickserv :identify %s %s", bot.getServerInfo().getNick(), bot.getServerInfo().getNickServPass());
			Commands.JOINALL(bot);
			return;
		}
		
		// username in use -- works now :) -- mrschlauch
		if (line.getCommand().equals("433")) { // ERR_NICKNAMEINUSE
			// remember for numerics, param 0 is our nick
			// probably why the simple substring didn't work before
			String newNick = line.getParam(1)+"_";
			Commands.NICK(bot, newNick);
			return;
		}
		
		// join on invite
		if (line.getCommand().equals("INVITE")) {
			// params: <nick> <channel>
			// http://tools.ietf.org/html/rfc1459#section-4.2.7
			String target = line.getParam(1);
			System.out.println("Joining " + target);
			Commands.JOIN(bot, target);
			bot.send(target, "o7");
			return;
		}
	}
}

package net.brojo.plugins;

import net.brojo.irc.IConnector;
import net.brojo.message.Message;

public class BrojoCommandsPlugin extends BrojoPlugin {

	@Override
	public String getName() {
		return "Brojo Commands";
	}

	@Override
	public String getVersion() {
		return "Whatever I feel like!";
	}

	@Override
	public String getDescription() {
		return "This plugin manages the changing of brojo attributes via irc commands by \"admins\"";
	}
	
	/**
	 * @param m Message received
	 * @return whether this plugin should be loaded
	 */
	public static boolean accepts(Message m) {
		return m.getContents().toLowerCase().startsWith(",nick");
	}

	@Override
	public boolean onActivated(IConnector impl, Message message) {
		if (impl.getUserInfo().isOp(message.getSender())) {
			System.out.printf("Changing nick to '%s'\n", message.getContents().split("\\s+", 2)[1]);
			impl.setNick(message.getContents().split("\\s+", 2)[1].replace("\n", "").replace("\r", "").replace("\t", ""));
		}
		
		return true;
	}

}

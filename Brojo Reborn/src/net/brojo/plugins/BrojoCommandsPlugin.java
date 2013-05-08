package net.brojo.plugins;

import net.brojo.irc.Commands;
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
		return m.getContents().toLowerCase().startsWith(",nick")
				|| m.getContents().toLowerCase().startsWith(",me")
					|| m.getContents().toLowerCase().startsWith(",part");
	}

	@Override
	public boolean onActivated(IConnector impl, Message message) {
		if (impl.getUserInfo().isOp(message.getSender())) {
			if (message.getContents().startsWith(",nick")) {
				System.out.printf("Changing nick to '%s'\n", message.getContents().split("\\s+", 2)[1]);
				impl.setNick(message.getContents().split("\\s+", 2)[1].replace("\n", "").replace("\r", "").replace("\t", ""));
			}
			else
				if (message.getContents().startsWith(",me")) {
					Commands.ACTION(impl, message.getRecipient(), message.getContents().split(",me ")[1]);
				} else
					if (message.getContents().startsWith(",part")) {
						Commands.PART(impl, message.getRecipient(), message.getContents().split(",part ")[1]);
					}
		}

		return true;
	}

}

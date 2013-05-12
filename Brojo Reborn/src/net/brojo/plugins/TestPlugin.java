package net.brojo.plugins;

import net.brojo.connection.IConnector;
import net.brojo.message.Message;

public class TestPlugin extends BrojoPlugin {

	@Override
	public String getName() {
		return "Test";
	}

	@Override
	public String getVersion() {
		return "0.01";
	}

	@Override
	public String getDescription() {
		return "Test Plugin for BrojoBot, outputs 'Doop' when the user types ',hello'";
	}
	
	/**
	 * @param m Message received
	 * @return whether this plugin should be loaded
	 */
	public static boolean accepts(Message m) {
		return m.getContents().toLowerCase().equals(",test");
	}

	@Override
	public boolean onActivated(IConnector impl, Message message) {
		impl.send(message.getRecipient(), "Hello there");
		return false;
	}

}

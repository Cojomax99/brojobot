package net.brojo.plugins;

import java.util.Random;

import net.brojo.connection.IConnector;
import net.brojo.message.Message;

public class CriticalPlugin extends BrojoPlugin {

	private static final String[] types = {"Normal", "Fire", "Water", "Electric", "Grass", "Ice", "Fighting", "Poison", "Ground", "Flying",
		"Psychic", "Bug", "Rock", "Ghost", "Dragon", "Dark", "Steel", "Butter", "Bacon", "Newt"
	};
	
	@Override
	public String getName() {
		return "!critical plugin from the old crow days";
	}

	@Override
	public String getVersion() {
		return "Crow Version";
	}
	
	/**
	 * @param m Message received
	 * @return whether this plugin should be loaded
	 */
	public static boolean accepts(Message m) {
		return m.getContents().toLowerCase().startsWith(",critical");
	}

	@Override
	public String getDescription() {
		return getVersion();
	}

	@Override
	public boolean onActivated(IConnector impl, Message message) {
		impl.send(message.getRecipient(), String.format("%s hits %s for %d %s damage!", message.getSender(), message.getContents().substring(10).trim(), new Random().nextInt(1000), types[new Random().nextInt(types.length)]));
		return false;
	}

}

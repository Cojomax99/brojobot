package net.brojo.plugins;

import net.brojo.connection.IConnector;
import net.brojo.message.Message;

public class PunchAPlugin extends BrojoPlugin {

	@Override
	public String getName() {
		return "Punchaface Soundcloud Plugin";
	}

	@Override
	public String getVersion() {
		return "?earpunch";
	}

	@Override
	public String getDescription() {
		return "Currently only gives a single link to Punchaface's awesome soundcloud with his awesome music, but may expand in the future";
	}	
	
	public static boolean accepts(Message m){
		return m.getContents().startsWith(",punch");
	}

	@Override
	public boolean onActivated(IConnector impl, Message message) {
		impl.send(message.respond("Here, listen to some awesome music! https://soundcloud.com/64bit-2"));
		
		return true;
	}

}

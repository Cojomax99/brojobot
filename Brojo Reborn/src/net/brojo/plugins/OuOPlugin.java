package net.brojo.plugins;

import java.util.Random;

import net.brojo.irc.IConnector;
import net.brojo.message.Message;

public class OuOPlugin extends BrojoPlugin {
	@Override
	public String getName(){
		return "OuO";
	}

	@Override
	public String getVersion(){
		return "1.0.0.0";
	}

	@Override
	public String getDescription(){
		return "Makes Brojo OuO when input is OuO";
	}

	public static boolean accepts(Message m){
		return m.getContents().toLowerCase().replace("", "").contains("ouo");
	}

	@Override
	public boolean onActivated(IConnector iCon, Message m) {
		String returnMsg = "";
		Random r = new Random();
		returnMsg += r.nextInt(2) == 0 ? "o" : "O";
		returnMsg += r.nextInt(2) == 0 ? "u" : "U";
		returnMsg += r.nextInt(2) == 0 ? "o" : "O";
		iCon.send(m.getRecipient(), returnMsg);
		return false;
	}
}
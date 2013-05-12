package net.brojo.plugins;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import net.brojo.connection.IConnector;
import net.brojo.message.Message;

public class TreebsPlugin extends BrojoPlugin {

	private static final Map<String, String> treebsTerms = new HashMap<String, String>();

	static {
		try {
			URL url = new URL("http://tribes.wikia.com/wiki/Voice_Game_System");
			URLConnection urlc = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				if (inputLine.startsWith("<td>") && inputLine.contains("</td><td>")) {
					String command = inputLine.substring(4, inputLine.substring(1).indexOf('<')).trim();
					String definition = inputLine.split("</td><td>")[1].split("</td><td>")[0].trim();
					treebsTerms.put(command, definition);
				}	 
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getName() {
		return "VGTG";
	}

	@Override
	public String getVersion() {
		return "0.0.0.0.0.1";
	}

	@Override
	public String getDescription() {
		return "Returns the meanings of any Tribes: Ascend in-game voice command";
	}

	private static boolean isTreebsMessage(String cmd) {
		return treebsTerms.containsKey(cmd.toUpperCase());
	}

	public static boolean accepts(Message m){
		return isTreebsMessage(m.getContents().trim());
	}

	@Override
	public boolean onActivated(IConnector impl, Message message) {
		impl.send(message.getRecipient(), treebsTerms.get(message.getContents().trim().toUpperCase()));

		return true;
	}

}

package net.brojo.plugins;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import net.brojo.connection.IConnector;
import net.brojo.message.Message;

public class FroxSearchPlugin extends BrojoPlugin {

	/**
	 * Maps song names to links
	 */
	private static Map<String, String> froxSongMap = new HashMap<String, String>();
	
	private static final String URL_PREFIX = "https://soundcloud.com/froxlab/";
	
	static {
		froxSongMap.put("trade winds", "breakset-37-unnamed");
		froxSongMap.put("eastern isles", "eastern_isles");
		froxSongMap.put("breakset 33", "breakset33-bad-weather");
		froxSongMap.put("breakset 32", "breakset32-fancy-dinner-party");
		froxSongMap.put("face the light", "face-the-light");
		froxSongMap.put("birds & bees", "bird-bee");
		froxSongMap.put("surreal unreal", "surreal-unreal");
		froxSongMap.put("wonderlust", "wonderlust");
		froxSongMap.put("rain in technicolor", "rain-in-technicolor");
		froxSongMap.put("tromboner", "tromboner");
		froxSongMap.put("best song", "tromboner");
	}
	
	@Override
	public String getName() {
		return "Froxlab Search Plugin";
	}

	@Override
	public String getVersion() {
		return "0.01";
	}

	@Override
	public String getDescription() {
		return "Searches Frox's soundcloud for the song entered";
	}
	
	public static boolean accepts(Message m){
		return m.getContents().startsWith(",frox");
	}

	@Override
	public boolean onActivated(IConnector impl, Message message) {
		if (message.getContents().toLowerCase().startsWith(",frox list")) {
			StringBuilder sb = new StringBuilder();
			
			int index = 0;
			for (String s : froxSongMap.keySet()) {				
				sb.append(s);
					
				if (index < froxSongMap.keySet().size() - 1)
					sb.append(", ");
				
				index++;
			}
			
			impl.send(message.getRecipient(), sb.toString());
			
		} else {
			boolean foundSong = false;
			for (String s : froxSongMap.keySet()) {
				String songName = message.getContents().toLowerCase().split(",frox ")[1];
				
				if (songName.equalsIgnoreCase(s)) {
					impl.send(message.getRecipient(), URL_PREFIX + froxSongMap.get(songName.trim()));
					foundSong = true;
					break;
				}
			}
			
			if (!foundSong)
				impl.send(message.getRecipient(), "Sorry, I could not find that specific song :(");

		}
		
		return true;
	}

}

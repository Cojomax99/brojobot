package net.brojo.irc;

/**
 * Helper class for sending commands to server
 */
public class Commands {

	/**
	 * sends the NICK command to the server.
	 * @param impl IConnector implementation
	 */
	public static void NICK(IConnector impl) {
		impl.sendf("NICK", impl.getUserInfo().getNick());
	}
	
	public static void NICK(IConnector impl, String newNick) {
		impl.getUserInfo().setNick(newNick);
		impl.sendf("NICK", newNick);
	}
	
	public static void IDENT(IConnector impl) {
		impl.sendf("IDENT", impl.getUserInfo().getPass());
	}
	
	/**
	 * Sends a PONG to the server when a PING is received
	 * @param impl
	 * @param pingVal Value to attach to the PONG
	 */
	public static void PONG(IConnector impl, String pingVal) {
		impl.sendf("PONG %s", pingVal);
	}
	
	/**
	 * Join a single channel
	 * @param impl Implementor to join a chan
	 * @param channel Chan to join
	 */
	public static void JOIN(IConnector impl, String channel) {
		impl.sendf("JOIN %s", channel);
	}
	
	/**
	 * Join all channels this impl has saved
	 * @param impl
	 */
	public static void JOINALL(IConnector impl) {
		for (String s : impl.getUserInfo().getChannels()) {
			impl.sendf("JOIN %s", s);
		}
	}
	
	/**
	 * Send CTCP message to target
	 * @param impl
	 * @param target
	 * @param message
	 */
	public static void CTCP(IConnector impl, String target, String message) {
		impl.sendf("PRIVMSG %s :\u0001%s\u0001", target, message);
	}
	
	/**
	 * Send notice to target
	 * @param impl
	 * @param target
	 * @param message
	 */
	public static void NOTICE(IConnector impl, String target, String message) {
		impl.sendf("NOTICE %s :\u0001%s\u0001", target, message);
	}

}

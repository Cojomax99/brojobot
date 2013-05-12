package net.brojo.irc;

/**
 * Type of incoming IRC line prefix
 * @author mrschlauch
 */
public enum SenderType {
	/**
	 * Prefix consists of nick!user@host.
	 * Example: :_303!mrschlauch@hagrid.cho.de PRIVMSG #bot :derp
	 */
	USER,
	/**
	 * Prefix consists of a server address.
	 * Example: :prometheus.rozznet.net MODE #bot +ao _303 _303
	 * (on netjoin)
	 */
	SERVER,
	/**
	 * No prefix. 
	 * Example: PING :aperture.esper.net
	 */
	NONE
}

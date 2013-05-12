package net.brojo.irc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents an incoming IRC Line, parsed into usable parts.
 * @author mrschlauch
 */
public class IRCLine {
	private String raw;
	private String prefix;
    private String nick;
    private String username;
    private String host;
    private SenderType senderType;
    private String command;
    private List<String> params;
    
    private IRCLine(String raw, String prefix, String command, List<String> params) {
    	this.raw = raw;
    	this.prefix = prefix;
    	this.command = command;
    	this.params = params;
    	
    	if (prefix != null) {
            int ex = prefix.indexOf("!");
            int at = prefix.indexOf("@");

            if (ex != -1 && at != -1 && at > ex) {
                this.nick = prefix.substring(0, ex);
                this.username = prefix.substring(ex + 1, at);
                this.host = prefix.substring(at + 1);
                this.senderType = SenderType.USER;
            } else {
                this.senderType = SenderType.SERVER;
            }
        } else {
        	this.senderType = SenderType.NONE;
        }
    }
    
    /**
     * Builds an outgoing IRC line (without the \r\n!) from the given parts.
     * 
     * @param command the IRC command.
     * @param params the List of parameters.
     * @param trailing a parameter to be prefixed with : and added at the end.
     * @return a String ready to be sent to the server (if you tack on a \r\n)
     */
    public static String assemble(String command, List<String> params, String trailing) {
    	StringBuilder sb = new StringBuilder(command);
    	
    	for (String param: params) {
    		sb.append(' ');
    		sb.append(param);
    	}
    	
    	if (trailing != null) {
    		sb.append(" :");
    		sb.append(trailing);
    	}
    	
    	return sb.toString();
    }
    
    /**
     * Builds an outgoing IRC line (without the \r\n!) from the given parts.
     * @param command the IRC command.
     * @param params the array of parameters.
     * @return a String ready to be sent to the server (if you tack on a \r\n)
     */
    public static String assemble(String command, String... params) {
    	return assemble(command, Arrays.asList(params), null);
    }
    
    /**
     * Construct a parsed IRCLine from the given raw incoming line.
     * @param raw the raw incoming line:
     * @return the constructed IRCLine, or null if the line breaks the RFC spec.
     */
    public static IRCLine parse(String raw) {
    	if (raw.isEmpty()) {
    		return null;
    	}
    	
    	if (raw.endsWith("\r\n")) {
    		raw = raw.substring(0, raw.length()-2);
    	}
    	
        LinkedList<String> parts = new LinkedList<String>(Arrays.asList(raw
                .split(" ")));
        
    	String prefix = null;
        
        if (parts.get(0).startsWith(":")) {
            prefix = parts.removeFirst().substring(1);
        }

        String command = parts.removeFirst().toUpperCase();
    	List<String> params = new ArrayList<String>();

        StringBuilder sb = null;
        for (String part : parts) {
            if (sb != null) {
                sb.append(" ");
                sb.append(part);
            } else if (part.startsWith(":")) {
                sb = new StringBuilder();
                sb.append(part.substring(1));
            } else if (!part.isEmpty()) {
                params.add(part);
            } else {
                // ignore, extra space between params
            }
        }

        if (sb != null) {
        	params.add(sb.toString());
        }
        
        return new IRCLine(raw, prefix, command, params);
    }
    
    public String getParam(int i) {
        return this.params.get(i);
    }

    public int getParamCount() {
        return this.params.size();
    }

	public String getPrefix() {
		return this.prefix;
	}

	public String getNick() {
		return this.nick;
	}

	public String getUsername() {
		return this.username;
	}

	public String getHost() {
		return this.host;
	}

	public SenderType getSenderType() {
		return this.senderType;
	}

	public String getCommand() {
		return this.command;
	}
	
    public String getRaw() {
		return raw;
	}
}
package net.brojo.message;

/**
 * Legacy Message class written by newthead, could use some modifications to simplify things, though.
 */
public class Message {

	/**
	 * Sender of the message (User or channel)
	 */
	private String sender;
	
	/**
	 * Recipient of message (User or channel)
	 */
	private String recipient;
	
	/**
	 * Contents of message
	 */
	private String contents;

	public static Message createMessage(String input) {

		String from = null;

		if (input.startsWith(":")) {
			// Message includes a sender
			String[] parts = input.substring(1).split(" ", 2);
			from = parts[0].substring(1);
			input = parts[1].trim();
		}

		if (!input.startsWith("PRIVMSG")) {
			// This is not a PRIVMSG command
			return null;
		}
		input = input.substring("PRIVMSG".length()).trim();

		String[] parts = input.split(" ",2);
		if (parts.length != 2) {
			// Missing recipient or contents
			return null;
		}

		if (parts[1].startsWith(":")) {
			parts[1] = parts[1].substring(1);
		}

		return new Message(from, parts[0], parts[1]);
	}

	public Message(String sender, String recipient, String contents) {
		this.sender = sender;
		this.recipient = recipient;
		this.contents = contents;
	}

	public Message(String recipient, String contents) {
		this(null, recipient, contents);
	}

	@Override
	public String toString() {
		StringBuilder command = new StringBuilder();
		if (sender != null) {
			command.append(":").append(sender).append(" ");
		}

		if(recipient != null){
			command.append("PRIVMSG ");
			command.append(recipient).append(" ");
			command.append(":").append(contents).append("\n");
		}else
		{
			command.append(contents).append("\n");
		}			

		return command.toString();
	}

	/** 
	 * @return Sender of the message
	 */
	public String getSender() {
		return sender;
	}
	/** 
	 * @return Recipient of the message, whether this be a channel or a user PM
	 */
	public String getRecipient() {
		return recipient;
	}
	/** 
	 * @return Contents of the message
	 */
	public String getContents() {
		return contents;
	}

}
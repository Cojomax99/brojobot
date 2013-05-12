package net.brojo.message;

public class PrioritizedMessage implements Comparable {

	/**
	 * Encapsulated Message object
	 */
	private Message message;
	
	/**
	 * Priority of the message to be sent
	 */
	private Priority priority;
	
	/**
	 * System time when the message was sent
	 */
	private long sendTime;
	
	public PrioritizedMessage(Message msg, Priority p, long time) {
		this.message = msg;
		this.priority = p;
		this.sendTime = time;
	}

	@Override
	public int compareTo(Object o) {
		PrioritizedMessage m = (PrioritizedMessage)o;
		
		int pAmt = this.priority.compareTo(m.priority);
		
		// first checks if the priorities are not equal, if so, return the difference. 
		// otherwise if they are equal, return 1 if this message should be sent before the other one
		// and return -1 if this message should be sent after the other one
		return pAmt != 0 ? pAmt : this.sendTime - m.sendTime == 0 ? 0 : this.sendTime - m.sendTime > 0 ? -1 : 1;
	}
}

package net.brojo.innards;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

import net.brojo.irc.IConnector;
import net.brojo.message.Message;

public class ThreadedOutput extends Thread {
	
	/**
	 * Implementor instance
	 */
	private IConnector bot;

	/**
	 * Is this thread currently running?
	 */
	private boolean isRunning = false;
	
	/**
	 * For writing to socket
	 */
	private BufferedWriter writer;
	
	/**
	 * Queue the messages are added to before they are sent. Thread-safe :D
	 */
	private final ConcurrentLinkedQueue<Message> messageQueue = new ConcurrentLinkedQueue<Message>();
	
	public ThreadedOutput(IConnector bot, BufferedWriter writer) {
		super("BrojoBot Output Thread");
		this.writer = writer;
		this.bot = bot;
		isRunning = true;
	}

	/**
	 * Anyway, the way this class works is plugins and the GUI (optional) add messages to the messageQueue, which
	 * outputs any time it is not empty \o/
	 */
	@Override
	public void run()
	{	
		try
		{
			while(isRunning)
			{			
				sleep(1L);
				while(!messageQueue.isEmpty())
				{
					out(messageQueue.poll().toString() + "\r\n");
				}
				sleep(1L);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void out(String s)
	{
		try 
		{
			StringBuffer sb = new StringBuffer(s);
			System.out.println(sb.toString());
			writer.write(sb.toString());
			writer.flush();			
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}		
	}
	
	public synchronized void send(Message msg)
	{
		System.out.println("Sending message" + msg.getContents() + " from " + msg.getSender() + " to " + msg.getRecipient());
		messageQueue.offer(msg);
	}

}

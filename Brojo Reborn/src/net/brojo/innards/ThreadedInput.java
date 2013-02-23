package net.brojo.innards;

public class ThreadedInput extends Thread {

	private boolean isRunning = false;
	
	public ThreadedInput() {
		super("BrojoBot Input Thread");
	}
	
	@Override
	public void run() {
		isRunning = true;
		
		while (isRunning) {
			
			//do stuff
		}
	}

}

package net.brojo.innards;

public class ThreadedOutput extends Thread {

	private boolean isRunning = false;
	
	public ThreadedOutput() {
		super("BrojoBot Output Thread");
	}
	
	@Override
	public void run() {
		isRunning = true;
		
		while (isRunning) {
			
			//do stuff
		}
	}


}

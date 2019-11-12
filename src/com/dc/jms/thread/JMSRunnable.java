package com.dc.jms.thread;

public interface JMSRunnable extends Runnable {
	
	public void setMessage(String message);
}
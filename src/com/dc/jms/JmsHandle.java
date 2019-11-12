package com.dc.jms;

public interface JmsHandle<T> {

	public String jmsHandle(T t,String messageId);
}
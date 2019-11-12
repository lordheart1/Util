package com.dc.jms.thread;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Scope("prototype")
@Repository("jmsRunnable")
public class DefaultJMSRunnable extends AbstractJMSRunnable {

	@Override
	public String buiess(String message) {
		// TODO Auto-generated method stub
		return null;
	}
}
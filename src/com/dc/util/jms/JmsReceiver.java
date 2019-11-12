package com.dc.util.jms;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

import com.dc.jms.thread.JMSRunnable;
import com.dc.util.spring.SpringUtil;

/**
 * Jms消息接受器
 * @author 小俊俊
 *
 */

public class JmsReceiver implements MessageListener {

	private static final Logger logger = Logger.getLogger(JmsReceiver.class);
	
	private static  ExecutorService EXECUTOR;   
	
	private int threadCount = 10;

	private  ExecutorService getExecutor() {
		
		if(EXECUTOR == null) {
			
			EXECUTOR = Executors.newFixedThreadPool(this.threadCount);   
		}
		
		return EXECUTOR;
	}
	
	public int getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}

	
	/**
	 * 收到消息后的回调方法
	 * @param message 消息对象
	 */
	public void onMessage(Message message) {

		if (message instanceof TextMessage) {
			
			TextMessage textMessage = (TextMessage)message;
			
			try {
				logger.debug(textMessage.getText());
				
				JMSRunnable runable = (JMSRunnable)SpringUtil.getApplicationContext().getBean("jmsRunnable");
				
				logger.debug("JmsRunnable:" + runable.hashCode());
				
				runable.setMessage(textMessage.getText());
				
				ExecutorService es = this.getExecutor();
				
				es.execute(runable);

			} catch (JMSException e) {
				logger.error(e.getMessage(), e);
				throw new RuntimeException(e);
			}
		} else {
			logger.warn("MessageListener.onMessage(): Message must be of type TextMessage");
			
			try {
				
				logger.warn(message.getJMSTimestamp());
				logger.warn(message.getJMSType());
			} catch(Exception e) {
				
				logger.warn(e.getMessage(),e);
			}
		}
	}
}
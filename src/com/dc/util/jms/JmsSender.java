package com.dc.util.jms;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * jms消息发送类 需要注入 template
 * @author 小俊俊
 *
 */

public class JmsSender implements MessageSender {

	private static final Logger logger = Logger.getLogger(JmsSender.class);
	
	JmsTemplate template;
	Destination destination;

	/**
	 * 发送文本内容
	 * @param 发送文本内容
	 */
	public boolean sendText (final String text) {
		
		logger.debug(this.destination);
		
		template.send(destination, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				
				logger.debug(text);
				
				return session.createTextMessage(text);
			}
		});
		
		return true;
	}

	public JmsTemplate getTemplate() {
		return template;
	}

	public void setTemplate(JmsTemplate template) {
		this.template = template;
	}

	public Destination getDestination() {
		return destination;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}
}
package com.dc.util.jms;

import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * rabbit发送类
 * @author 小俊俊
 *
 */

public class RabbitJmsSender implements MessageSender {

	private static final Logger logger = Logger.getLogger(RabbitJmsSender.class);
	
	private RabbitTemplate template;
	
	/**
	 * 发送文本内容
	 * @param text 文本内容
	 */
	@Override
	public boolean sendText(String text) {
		
		try {
			template.convertAndSend(text);
		} catch(Exception e) {
			
			logger.error(e.getMessage(),e);
			return false;
		}
		return true;
	}

	public RabbitTemplate getTemplate() {
		return template;
	}

	public void setTemplate(RabbitTemplate template) {
		this.template = template;
	}

}

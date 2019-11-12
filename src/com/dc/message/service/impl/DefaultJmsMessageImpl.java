package com.dc.message.service.impl;



import java.util.UUID;

import org.apache.log4j.Logger;

import com.dc.message.service.MessageService;

public class DefaultJmsMessageImpl implements MessageService {
	
	private static final Logger logger = Logger.getLogger(DefaultJmsMessageImpl.class);

	@Override
	public String preSendMessage(String message) {
		
		String id = UUID.randomUUID().toString();
		
		if(logger.isTraceEnabled()) {
		
			StringBuilder sb = new StringBuilder("Jms preSend");
			sb.append("id:").append(id).append(" text:").append(message);
			
			logger.trace(sb.toString());
		}
		
		return id;
	}

	@Override
	public String receMessage(String message) {
		
		String id = UUID.randomUUID().toString();
		
		if(logger.isTraceEnabled()) {
		
			StringBuilder sb = new StringBuilder("Jms receMessage");
			sb.append("id:").append(id).append(" text:").append(message);
			
			logger.trace(sb.toString());
		}
		
		return id;
	
	}

	@Override
	public boolean receMessageDealOver(String id, String type, String from, String version, String bussId,
			String result) {
		
		if(logger.isTraceEnabled()) {
			
			StringBuilder sb = new StringBuilder("Jms receMessageDealOver");
			sb.append("id:").append(id).append(" type:").append(type)
			.append(" from:").append(from).append(" version:").append(version)
			.append(" bussId:").append(bussId)
			.append(" result:").append(result);
			
			logger.trace(sb.toString());
		}
		
		return true;
	}

	@Override
	public boolean sendMessageOver(String id, String type, String from, String version, String bussId, String content,
			String result) {
		
		if(logger.isTraceEnabled()) {
			
			StringBuilder sb = new StringBuilder("Jms sendMessageOver");
			sb.append("id:").append(id).append(" type:").append(type)
			.append(" from:").append(from).append(" version:").append(version)
			.append(" bussId:").append(bussId).append(" content:").append(content)
			.append(" result:").append(result);
			
			logger.trace(sb.toString());
		}
		
		return true;
	}

	@Override
	public boolean updateMessage(String id, String content) {
		// TODO Auto-generated method stub
		return false;
	}

}

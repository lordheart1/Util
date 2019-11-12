package com.dc.jms.impl;

import java.util.UUID;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.dc.esb.bean.Message;
import com.dc.jms.JmsSenderObject;
import com.dc.message.service.MessageService;
import com.dc.sender.annotation.JmsBean;
import com.dc.util.jms.MessageSender;


public class JmsSenderImpl implements JmsSenderObject {

	private static final Logger logger = Logger.getLogger(JmsSenderImpl.class);
	
	@Resource(name="jmsSender")
	private MessageSender messageSender;
	
	private MessageService messageService;
	
	@Value("${SystemId}")
	private String from;
	
	public void sendObject(Object obj,String type,String busiId) {
		
		this.sendObject(obj, type,"1" ,busiId);
	}
	
	public void sendObject(Object obj,String type,String v,String busiId) {
		
		Message message = new Message();
		
		message.setFrom(this.from);
		message.setType(type);
		message.setV(v);
	
		JSONObject jsonData = JSONObject.fromObject(obj);
		
		String data = jsonData.toString();
		
		message.setData(obj);
		
		String mid = null;
		
		if(this.messageService != null) {
		
			mid = this.messageService.preSendMessage(data);
		} else {
			
			mid = UUID.randomUUID().toString();
		}

//		String mid = type + busiId;
		message.setMid(mid);
		
		JSONObject jsonMessage = JSONObject.fromObject(message);
		
		String text = jsonMessage.toString();
		
		logger.info("jms_send:" + text);
		
		this.messageSender.sendText(text);
		
		this.messageService.sendMessageOver(mid, type, this.from, v, busiId,text, "0");
		
		return;
	}

	@Override
	public void sendObject(Object obj, String busiId) {
		
		JmsBean jmsBean = obj.getClass().getAnnotation(JmsBean.class);
		
		if(jmsBean == null) {
			
			throw new RuntimeException("no jms annotatioin");
		}
	
		String type = jmsBean.type();
		String v = jmsBean.v();
		
		this.sendObject(obj, type,v, busiId);;
	}
	
}
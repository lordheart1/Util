package com.dc.message.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dc.esb.domain.MessageBean;
import com.dc.message.repository.MessageRepository;
import com.dc.message.service.MessageService;

@Service("messageService")
public class MessageServiceImpl implements MessageService {

	@Resource(name="messageRepository")
	private MessageRepository messageRepository; 
	
	public String receMessage(String message) {
		
		MessageBean mBean = new MessageBean();
		
		mBean.setData(message);
		mBean.setResult("1");
		this.messageRepository.save(mBean);
		
		return mBean.getId();
	}

	public boolean receMessageDealOver(String id, String type,String from,String version, String bussId,
			String result) {
		
		MessageBean mBean = this.messageRepository.findOne(id);
		
		if(mBean == null) {
			
			return false;
		}
		
		mBean.setType(type);
		mBean.setBussId(bussId);
		mBean.setResult(result);
		mBean.setFrom(from);
		mBean.setVer(version);
		
		Object obj = this.messageRepository.save(mBean);
		
		return (obj != null);
	}

	public boolean updateMessage(String id, String content) {
		
		MessageBean mBean = this.messageRepository.findOne(id);
		
		mBean.setData(content);
		
		mBean = this.messageRepository.save(mBean);
		
		return mBean != null;
	}

	public boolean sendMessageOver(String id, String type, String from,
			String version, String bussId, String conntent, String result) {
		
		MessageBean mBean = this.messageRepository.findOne(id);
		
		if(mBean == null) {
			
			return false;
		}
		
		mBean.setType(type);
		mBean.setBussId(bussId);
		mBean.setResult(result);
		mBean.setFrom(from);
		mBean.setVer(version);
		mBean.setData(conntent);
		
		Object obj = this.messageRepository.save(mBean);
		
		return (obj != null);
	}

	@Override
	public String preSendMessage(String message) {
		
		MessageBean mBean = new MessageBean();
		
		mBean.setData(message);
		mBean.setResult("0");
		this.messageRepository.save(mBean);
		
		return mBean.getId();
	}

}

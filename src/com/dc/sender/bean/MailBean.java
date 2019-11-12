package com.dc.sender.bean;

import com.dc.sender.annotation.JmsBean;

@JmsBean(type = "112")
public class MailBean {

	private String content;
	private String subject;
	private String recievers;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getRecievers() {
		return recievers;
	}
	public void setRecievers(String recievers) {
		this.recievers = recievers;
	}
	
	
}

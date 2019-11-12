package com.dc.sender.bean;

import java.util.Map;

import com.dc.sender.annotation.JmsBean;

@JmsBean(type = "111")
public class SmsBean {

	private String mobile;
	private String content;
	private String templateId;
	
	private Map<String,String> map;
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Map<String, String> getMap() {
		return map;
	}
	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	
	
}

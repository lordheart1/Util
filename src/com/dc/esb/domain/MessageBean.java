package com.dc.esb.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity(name="message")
@Table(name="th_logs_message")
public class MessageBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)  
	private String id;
	
	@Column(name="from_id")
	private String from;
	
	@Column(name="m_type")
	private String type;
	
	private String ver;
	
	@Column(name="m_content")
	private String data;
	
	@Column(name="busi_id")
	private String bussId;
	
	@Column(name="m_status")
	private String result;
	
	public String getBussId() {
		return bussId;
	}
	public void setBussId(String bussId) {
		this.bussId = bussId;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getVer() {
		return ver;
	}
	public void setVer(String ver) {
		this.ver = ver;
	}
	
	
}
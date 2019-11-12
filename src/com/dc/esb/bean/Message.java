package com.dc.esb.bean;


public class Message {

	private String mid;
	private String from;
	private String type;
	private String v;
	private Object data;
	
	
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String form) {
		this.from = form;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getV() {
		return v;
	}
	public void setV(String v) {
		this.v = v;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
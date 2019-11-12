package com.dc.jms;

/**
 * 
 * @author 小俊俊
 *
 */
public interface JmsSenderObject {
	
	/**
	 * 发送报文
	 * @param obj 报文对象
	 * @param type 报文类型
	 * @param busiId 业务来源ID
	 */
	public void sendObject(Object obj,String type,String busiId);
	
	/**
	 * 发送报文
	 * @param obj 报文对象
	 * @param busiId 业务来源ID
	 */
	
	public void sendObject(Object obj,String busiId);
}

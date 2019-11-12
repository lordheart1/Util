package com.dc.message.service;

/**
 * 
 * @author 小俊俊
 * 报文持久化接口
 */
public interface MessageService {

	/**
	 * 预发送
	 * @param message 报文本体
	 * @return 发送消息编号
	 */
	public String preSendMessage(String message);
	
	
	/**
	 * 接受消息
	 * @param message
	 * @return  接受消息流水
	 */
	public String receMessage(String message);
	
	/**
	 * 消息更新 接受处理完毕用
	 * @param id
	 * @param type
	 * @param from
	 * @param version
	 * @param bussId
	 * @param result
	 * @return
	 */
	public boolean receMessageDealOver(String id,String type,String from,String version,String bussId,String result);
	
	/**
	 * 消息更新 发送成功后
	 * @param id
	 * @param type
	 * @param from
	 * @param version
	 * @param bussId
	 * @param conntent
	 * @param result
	 * @return
	 */
	public boolean sendMessageOver(String id,String type,String from,String version,String bussId,String conntent,String result);
	
	/**
	 * 消息更新 （暂不用）
	 * @param id
	 * @param content
	 * @return
	 */
	public boolean updateMessage(String id,String content);
}

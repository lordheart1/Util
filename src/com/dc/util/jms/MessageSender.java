package com.dc.util.jms;

/**
 * 报文消息
 * @author 小俊俊
 *
 */

public interface MessageSender {

	/**
	 * 发送报文
	 * @param text 文本内容
	 * @return true 发送成功
	 */
	public boolean sendText(String text);
}

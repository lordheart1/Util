package com.dc.util.msg;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.dc.jms.JmsSenderObject;
import com.dc.sender.bean.MsgBean;

/**
 * 系统推送工具
 * @author 小俊俊
 *
 */

@Repository("msgPushUtil")
public class MsgPushUtil {

	private static final String SEND_MSG_TYPE = "11";
	
	private static final Logger logger = Logger.getLogger(MsgPushUtil.class);
	
	@Resource(name = "esbSender")
	private JmsSenderObject jmsSender;
	
	/**
	 * 系统消息推送
	 * @param msg 系统消息对象
	 * @param busiType 业务类型
	 * @param busiId 业务主键
	 * @return
	 */
	public boolean sendUserMessage(MsgBean msg,String busiType,String busiId ) {
	
		logger.debug("send message:" + msg.getContent());
		jmsSender.sendObject(msg, SEND_MSG_TYPE + busiType,busiId );
		return true;
	}
}
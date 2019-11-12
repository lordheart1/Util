package com.dc.util.mobile;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.dc.jms.JmsSenderObject;
import com.dc.sender.bean.SMSBean;

/**
 * 手机短信异步报文封装类
 * @author 小俊俊
 *
 */
@Repository("mobileUtil")
public class MobileUtil {

	private static final String SEND_TEL_TYPE = "61";
	
	private static final Logger logger = Logger.getLogger(MobileUtil.class);
	
	@Resource(name = "esbSender")
	private JmsSenderObject jmsSender;
	
	/**
	 * 发送短消息
	 * @param mobile 手机号码
	 * @param content 短信内容
	 * @param busiType 业务类型
	 * @param busiId 业务主键
	 * @return
	 */
	public boolean sendUserMessage(String mobile,String content,String busiType,String busiId ) {
	
		logger.debug("send message:" + mobile);
		
		SMSBean smsBean=new SMSBean(); 
		smsBean.setContent(content);
		smsBean.setMobile(mobile);
	
		jmsSender.sendObject(smsBean, SEND_TEL_TYPE + busiType,busiId );
		return true;
	}
}
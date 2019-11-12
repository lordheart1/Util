package com.dc.util.email;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.dc.jms.JmsSenderObject;
import com.dc.sender.bean.MailBean;

/**
 * 发送email的工具类
 * @author 小俊俊
 *
 */
@Repository("emailUtil")
public class EmailUtil {

	private static final String SEND_EMAIL_TYPE = "71";
	
	private static final Logger logger = Logger.getLogger(EmailUtil.class);
	
	@Resource(name = "esbSender")
	private JmsSenderObject jmsSender;
	
	/**
	 * 发送邮件
	 * @param email 邮箱
	 * @param sub 标题
	 * @param content 内容
	 * @param busiType 业务类型
	 * @param busiId 业务主键
	 * @return true 发送成功
	 */
	public boolean sendUserMessage(String email,String sub,String content,String busiType,String busiId ) {
	
		logger.debug("send message:" + email);
		
		MailBean bean = new MailBean();
		
		bean.setContent(content);
		bean.setSubject(sub);
		bean.setRecievers(email);
	
		jmsSender.sendObject(bean,busiId );
		return true;
	}
}
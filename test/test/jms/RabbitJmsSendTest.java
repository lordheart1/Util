package test.jms;

import java.util.UUID;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.dc.jms.JmsSenderObject;
import com.dc.sender.bean.MailBean;
import com.dc.sender.bean.SmsBean;

@ContextConfiguration(locations={"classpath:applicationContext-rabbit.xml"})
public class RabbitJmsSendTest extends AbstractJUnit4SpringContextTests{

	@Resource(name = "objectSender")
	private JmsSenderObject objectSender;
	
	@Test
	public void test() {
		
		MailBean email = new MailBean();
		email.setContent("content");
		email.setSubject("sub");
		email.setRecievers("rec");
		
		String id = UUID.randomUUID().toString();
		
		this.objectSender.sendObject(email, id);;
	}

}

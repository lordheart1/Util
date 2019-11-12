package com.dc.util.jms;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import com.dc.jms.thread.JMSRunnable;
import com.dc.util.spring.SpringUtil;

/**
 * rabbit监听器
 * @author 小俊俊
 *
 */

public class JmsRabbitListen {

	private static final Logger logger = Logger.getLogger(JmsRabbitListen.class);

	private static final String RUN_ID = "jmsRunnable";

	private static ExecutorService EXECUTOR;

	@Value("${jms_thread_count}")
	private int threadCount;

	private ExecutorService getExecutor() {

		if (EXECUTOR == null) {

			logger.debug("Jms threadCount=" + this.threadCount);
			EXECUTOR = Executors.newFixedThreadPool(this.threadCount);
		}

		return EXECUTOR;
	}

	/**
	 * 获得消息后回调方法(实际内部直接调用字符串重载方法)
	 * @param bytes
	 */
	public void listen(byte[] bytes) {
		
		String text = new String(bytes);
		
		this.listen(text);
	}

	
	/**
	 * 获得消息后回调方法
	 * @param text
	 */

	public void listen(String text) {
		
		try {
			logger.debug(text);

			JMSRunnable runable = (JMSRunnable) SpringUtil
					.getApplicationContext().getBean(RUN_ID);

			logger.debug("JmsRunnable:" + runable.hashCode());

			runable.setMessage(text);

			ExecutorService es = this.getExecutor();

			es.execute(runable);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

}

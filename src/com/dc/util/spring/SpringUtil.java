package com.dc.util.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * spring钩子
 * @author 小俊俊
 *
 */
public class SpringUtil implements ApplicationContextAware {

	private static ApplicationContext factory;

	public static ApplicationContext getApplicationContext() {
		return factory;
	}

	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		
		factory = context;
	}
}
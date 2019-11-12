package com.dc.jms.thread;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Properties;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import com.dc.esb.bean.MessageBean;
import com.dc.jms.JmsHandle;
import com.dc.message.service.MessageService;
import com.dc.util.spring.SpringUtil;

public abstract class AbstractJMSRunnable implements JMSRunnable {

	private final Logger logger = Logger.getLogger(AbstractJMSRunnable.class);
	private final String TYPE_PRE = "type";
	
	private  Properties TYPE_MAP;
	
	private final String OK = "1";
	private final String NO_OK = "2";
	
	private String message;
	
	@Resource(name="messageService")
	private MessageService messageService;
	
	@Value("${MAP_FILE}")
	private String mapFile = "map/map.properties";
	
	public void setMessage(String message) {
		
		this.message = message;
	}
	
	synchronized private Properties getTypeMap() {
		
		if(TYPE_MAP == null) {
			
			InputStream input = null;
			try {
				TYPE_MAP = new Properties();
			
				input = Thread.currentThread().getContextClassLoader().getResourceAsStream(this.mapFile);
			
				TYPE_MAP.load(input);
			}catch(Exception e) {
			
				logger.error(e.getMessage(),e);
			} finally {
				
				if(input != null) {
					
					try {
						input.close();
					} catch (IOException e) {
						logger.error(e.getMessage(),e);
					}
				}
			}
		}
		
		return TYPE_MAP;
	}

	public void run() {
		
		logger.debug("message:" + message);
		
		String id = null;
		
		try {
			id = this.messageService.receMessage(message);
		} catch(Exception e) {
			
			logger.error(e.getMessage(), e);
		}
		
		String type = null;
		
		String busiId = null;
		
		String version = null;
		
		String formId = null;
		
		String data = null;
		
		try {
		
			JSONObject jObj = JSONObject.fromObject(this.message);
			
			type = jObj.getString("type");
			version = jObj.getString("v");
			formId = jObj.getString("from");
			data = jObj.getString("data");
		
			MessageBean meBean = new MessageBean();
			
			meBean.setTypeId(type);
			meBean.setVersion(version);
			meBean.setFormId(formId);
			meBean.setData(data);
			
		} catch(Exception e) {
			
			logger.error(e.getMessage(), e);
		}
		
		try {
			
			Properties typeMap = this.getTypeMap();
			
			String springName = typeMap.getProperty(TYPE_PRE + type);
			
			Object obj = SpringUtil.getApplicationContext().getBean(springName);
			
			if(obj == null) {
				
				StringBuilder sb = new StringBuilder();
				sb.append("type=").append(TYPE_PRE).append(type).append(" springName=").append(springName);
				sb.append(" mapFile=").append(this.mapFile).append(" not found!");
				
				logger.error(sb.toString());
				return;
			}
			
			if(!(obj instanceof JmsHandle<?>)) {
				
				StringBuilder sb = new StringBuilder();
				sb.append("type=").append(TYPE_PRE).append(type).append(" springName=").append(springName)
				.append(" mapFile=").append(this.mapFile).append(" find Object is not JMSHandle")
				.append(obj.getClass());
				
				logger.error(sb.toString());
				return;
			}
			
			Type jmsType = null;
			Type[] typs = obj.getClass().getGenericInterfaces();
			
			for (Type type2 : typs) {
				if(type2 instanceof ParameterizedType) {
					
					jmsType = type2;
					break;
				}
			}
			
			ParameterizedType pType = (ParameterizedType)jmsType;
			
			Class<?> mClass = (Class<?>)pType.getActualTypeArguments()[0];
			
			JSONObject jObj = JSONObject.fromObject(data);
			
			Object dObj = JSONObject.toBean(jObj, mClass);
			
			Method method = obj.getClass().getMethod("jmsHandle", new Class<?>[]{mClass,String.class});
			
			Object r = method.invoke(obj, new Object[]{dObj,id});
			
			if(r != null) {
				
				busiId = r.toString();
			}
			
		} catch(Exception e) {
			
			logger.error(e.getMessage(),e);
		}
		
		if(id != null) {
			
			String result = null;
			
			if(busiId != null) {
				result = OK;
			} else {
				result = NO_OK;
			}
			
			this.messageService.receMessageDealOver(id, type,formId,version, busiId, result);
		}
	}
	
	abstract public String buiess(String message);
}
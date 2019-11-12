package com.dc.mybatis.service.config;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class MybatisConfig {
	
	private static Map<Class,ProcessToValue> CONFIG =  new HashMap<Class,ProcessToValue>();
	
	private static final ProcessToValue DEFAULT_PROCESS = new DefaultProcessToValue();
	
	public static void  regist(Class clazz,ProcessToValue process) {
		
		CONFIG.put(clazz, process);
	}

	public static ProcessToValue getProcess(Class clazz) {
		
		ProcessToValue process = CONFIG.get(clazz);
		
		if(process == null) {
			
			return DEFAULT_PROCESS;
		}
		
		return process;
	}
	
	static {
		
		regist(Timestamp.class,new DefaultTimestampProceToValue("yyyy-MM-dd HH:mm:ss"));
	}
}
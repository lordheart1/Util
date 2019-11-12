package com.dc.mybatis.service.config;

public class DefaultProcessToValue implements ProcessToValue {

	@Override
	public String process(Object obj) {
		
		return obj.toString();
	}

}

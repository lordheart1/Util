package com.dc.mybatis.service.config;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DefaultTimestampProceToValue implements ProcessToValue {

	public static final String DEFAULT_DATE_PATTERN = "yyyy/MM/dd";
	
	private DateFormat dateFormat;
	
	public DefaultTimestampProceToValue(String datePattern) {
		try {
			dateFormat = new SimpleDateFormat(datePattern);
		} catch (Exception ex) {
			dateFormat = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
		}
	}
	
	@Override
	public String process(Object obj) {
		return dateFormat.format((Date) obj);
	}

}

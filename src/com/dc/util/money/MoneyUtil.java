package com.dc.util.money;

import java.math.BigDecimal;

import common.Logger;

public class MoneyUtil {
	
	private static final Logger logger = Logger.getLogger(MoneyUtil.class);

	public static Integer bigDecimalToInteger(BigDecimal src){
		Integer result = new Integer(0);
		try {
			src = src.multiply(new BigDecimal(100));
			result = new Integer(src.intValue());
		} catch (Exception e) {
			logger.info(e);
		}
		return result;
	}
	
	public static String integerToString(Integer src) {
		String result = "";
		if(src!=null) {
			BigDecimal srcbig = new BigDecimal(src.intValue());
			srcbig = srcbig.divide(new BigDecimal(100));
			srcbig.setScale(2);
			result = srcbig.toString();
		}
		return result;
	}
	
	
}

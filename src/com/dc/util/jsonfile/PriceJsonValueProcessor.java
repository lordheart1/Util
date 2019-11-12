
package com.dc.util.jsonfile;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * json转换价格处理
 * @author 小俊俊
 *
 */
public class PriceJsonValueProcessor implements JsonValueProcessor {
	


	public Object processObjectValue(String key, Object value,
			JsonConfig jsonConfig) {
		
		if("price".equals(key) && value != null) {
			
			BigDecimal temp = new BigDecimal(value.toString());
			temp = temp.setScale(2, BigDecimal.ROUND_HALF_UP);
			
			return temp.toString();
		}
		
		return value;
	}

	@Override
	public Object processArrayValue(Object arg0, JsonConfig arg1) {
		
		return arg0;
	}

}

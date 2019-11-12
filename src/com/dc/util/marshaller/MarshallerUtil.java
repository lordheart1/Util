package com.dc.util.marshaller;

import java.io.IOException;
import java.io.StringWriter;

import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.ValidationException;

/**
 * castor工具类
 * @author 小俊俊
 *
 */
public class MarshallerUtil {

	/**
	 * 将对象转为xml的文本
	 * @param value java 对象
	 * @return xml文本
	 * @throws MarshalException
	 * @throws ValidationException
	 * @throws IOException
	 */
	public static String sendMarshaller(Object value) throws MarshalException, ValidationException, IOException {

		StringWriter writer = new StringWriter();
		Marshaller marshaller =new Marshaller(writer);
		marshaller.setMarshalAsDocument(false);		
		marshaller.marshal(value);
		String zjsResponses = writer.toString();
		return zjsResponses;
	}
	
}
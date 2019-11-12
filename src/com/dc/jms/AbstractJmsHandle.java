package com.dc.jms;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class AbstractJmsHandle {

	protected Class getGenericType() {
		Type genType = getClass().getGenericSuperclass();
		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		
		return (Class) params[0];
	}
}

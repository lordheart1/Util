package com.dc.spring.context.parse;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class IceNamespaceHandler extends NamespaceHandlerSupport {

	 @Override    
	    public void init() {
		 	
	       registerBeanDefinitionParser("remote",new IceBeanDefinitionParser());    
	    }
}

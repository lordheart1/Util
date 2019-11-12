package com.dc.spring.context.parse;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

public class IceBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {    
    @Override    
    protected Class<?> getBeanClass(Element element) {    
        return com.dc.util.ice.IcePrxFactory.class;
    }    
    @Override    
    protected void doParse(Element element, BeanDefinitionBuilder builder) {    
        String iName =element.getAttribute("interface");    
        String proxy = element.getAttribute("proxy");   
    
       
       builder.addPropertyValue("prxHelper", iName);          
       builder.addPropertyValue("proxyString", proxy);    
            
    }    
}  
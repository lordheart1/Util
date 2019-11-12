package com.dc.util.ice;

import java.lang.reflect.Method;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.FactoryBean;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectPrx;

public class IcePrxFactory implements FactoryBean<Object> {

	private static final Logger logger = Logger.getLogger(IcePrxFactory.class);
	
	private static final String CAST_METHOD = "checkedCast";
	
	private Method castMethod;
	
	@Resource(name = "ice")
	private  Communicator ic = null;
	
	private String proxyString;
	
	private String prxHelper;

	@Override
	public Object getObject() throws Exception {
		
		try {
		if(this.proxyString != null && !this.proxyString.trim().equals("")) {
		
			ObjectPrx base = ic.stringToProxy(this.proxyString);
		//	IdServicePrx idService = IdServicePrxHelper.checkedCast(base);
			
			if(this.castMethod == null) {
				
				Class preHelperClass = Class.forName(prxHelper + "Helper");
				
				this.castMethod = preHelperClass.getMethod(CAST_METHOD, new Class[]{ObjectPrx.class});
			}
			
			Object obj = castMethod.invoke(null, new Object[]{base});
			
			return obj;
		}
		} catch(Exception e) {
		
			logger.error(e.getMessage(),e);
			StringBuilder sb = new StringBuilder();
			
			sb.append("Ice proxyString=").append(this.proxyString)
			.append(" prxHelper=").append(this.prxHelper);
			
			logger.error(sb.toString());
		}
		
		return null;
	}

	@Override
	public Class getObjectType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getProxyString() {
		return proxyString;
	}

	public void setProxyString(String proxyString) {
		this.proxyString = proxyString;
	}

	public String getPrxHelper() {
		return prxHelper;
	}

	public void setPrxHelper(String prxHelper) {
		this.prxHelper = prxHelper;
	}
}
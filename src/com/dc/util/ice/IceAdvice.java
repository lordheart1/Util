package com.dc.util.ice;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectPrx;

/**
 * bill下Ice专用拦截器
 * @author 小俊俊
 *
 */
@Repository("iceAdvice")
public class IceAdvice implements MethodInterceptor {

	private static final Logger logger = Logger.getLogger(IceAdvice.class);

	private static Map<String, Method> METHOD_CACHE = new HashMap<String, Method>();
	
	private static Map<String,ObjectPrx> ICE_OBJECT_PRX_CACHE = new HashMap<String,ObjectPrx>();

	private static final String CAST_METHOD = "checkedCast";
	
	private static final String PRX_HELPER = "ServicePrxHelper";

	private Method castMethod;

	@Resource(name = "ice")
	private Communicator ic = null;

	private IceClientConfig iceClientConfig;

	private String prxHelper;
	
	private String iceName;

	public String getIceName() {
		return iceName;
	}

	public void setIceName(String iceName) {
		this.iceName = iceName;
	}

	public IceClientConfig getIceClientConfig() {
		return iceClientConfig;
	}

	public void setIceClientConfig(IceClientConfig iceClientConfig) {
		this.iceClientConfig = iceClientConfig;
	}

	public String getPrxHelper() {
		return prxHelper;
	}

	public void setPrxHelper(String prxHelper) {
		this.prxHelper = prxHelper;
	}
	
	private String getPrxHelperClassName(Package p) {
		
		StringBuilder sb = new StringBuilder(p.getName());
		
		sb.delete(sb.lastIndexOf("."), sb.length());
		sb.append(".").append(this.iceName).append(PRX_HELPER);
		
		return sb.toString();
		
	}

	private Method getCheckedCast(Package p) {

		if (this.castMethod == null) {

			Class preHelperClass;
			try {
				
				String prxHelperClassName = this.prxHelper;
				
				if(prxHelperClassName == null || prxHelperClassName.trim().equals("")) {
					
					prxHelperClassName = this.getPrxHelperClassName(p);
				}
				
				preHelperClass = Class.forName(prxHelperClassName);

				this.castMethod = preHelperClass.getMethod(CAST_METHOD,
						new Class[] { ObjectPrx.class });
			} catch (ClassNotFoundException | NoSuchMethodException
					| SecurityException e) {

				logger.error(e.getMessage(), e);
				return null;
			}
		}

		return this.castMethod;

	}
	
	protected ObjectPrx getIceObjectPrx(String iceName,long id,Package p) {
		
		String proxyString = this.iceClientConfig.getIceNodeCount(iceName,
				id);
		
		
		logger.debug("proxyString:" + proxyString);
		
		ObjectPrx prx = ICE_OBJECT_PRX_CACHE.get(proxyString) ;
		
		if(prx != null) {
			
			return prx;
		}

		ObjectPrx base = ic.stringToProxy(proxyString);

		Method m = this.getCheckedCast(p);

		try {
			prx = (ObjectPrx) m.invoke(null, new Object[]{base});
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			
			logger.error(e.getMessage(), e);
			return null;
		}
		
		ICE_OBJECT_PRX_CACHE.put(proxyString, prx);
		
		return prx;
	}

	@Override
	public Object invoke(MethodInvocation arg) throws Throwable {

		int paramIndex = 0;
		
		Method arg0 = arg.getMethod();

		Object[] arg1 = arg.getArguments();

		String id = arg1[paramIndex].toString();
		
		Package p = arg0.getDeclaringClass().getPackage();

	//	String serviceName = arg0.getDeclaringClass().getSimpleName();

		long idL = Long.parseLong(id);

		ObjectPrx obj = this.getIceObjectPrx(this.iceName, idL,p);
		
		Method method = obj.getClass().getMethod(arg.getMethod().getName(), arg.getMethod().getParameterTypes());
		
		try {
			Object result = method.invoke(obj, arg.getArguments());
			return result;
		} catch(InvocationTargetException e) {
			
			throw e.getCause();
		}
	}

}

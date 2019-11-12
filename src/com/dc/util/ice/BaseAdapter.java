package com.dc.util.ice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.dc.base.bean.NodePage;
import com.dc.mybatis.service.MybatisService;
import com.dc.util.spring.SpringUtil;
import com.kdy.base.PageBean;
import com.zeroc.Ice.Current;

public class BaseAdapter{
	
	public static final Set<String> METHOD_NAMES = new HashSet<String>(Arrays.asList("findBean", "find","findPage"));
	
	public PageBean findBean(int cid, String queryId,
			Map<String, String> param, String[] idList, Current __current) {
		
		MybatisService myBatisManager = (MybatisService)SpringUtil.getApplicationContext().getBean("myBatisManager");
		
		Map<String,Object> myParam = new HashMap<String,Object>(param);
		
		myParam.put("ids", idList);
		
		NodePage pageBean = myBatisManager.find(queryId, myParam);
		
		return this.getPageBean(pageBean);
	}
	
	public PageBean find(int cid,String queryId, Map<String, String> param,
			Current __current) {
		 MybatisService myBatisManager = (MybatisService)SpringUtil.getApplicationContext().getBean("myBatisManager");
		 
		Map<String,Object> myParams = new HashMap<String,Object>(param);
		
		NodePage pageBean = myBatisManager.find(queryId, myParams);
		return this.getPageBean(pageBean);
	}
	
	public PageBean findPage(int cid,String queryId, Map<String, String> param,
			int page, int rows, Current __current) {
		 MybatisService myBatisManager = (MybatisService)SpringUtil.getApplicationContext().getBean("myBatisManager");
		 
		NodePage pageBean = myBatisManager.findPage(queryId, param, page, rows);
	
		return this.getPageBean(pageBean);
	}
	
	public PageBean getPageBean(NodePage bean){
		
		PageBean result = new PageBean();

		result.setCount(bean.getCount());
		result.setData(bean.getData());
		
		if(bean.getFooter() != null ) {
		
			Map<String,String> footer = new HashMap<String,String>();
			
			for(Object obj : bean.getFooter().entrySet()) {
				
				Entry<String,Object> entry = (Entry<String,Object>)obj;
				
				if(entry.getValue() != null) {
					
					footer.put(entry.getKey(), String.valueOf(entry.getValue()));
				}
			}
			
			result.setFooter(footer);
		
		}
	
		result.setPage(bean.getP());
		result.setRows(bean.getPageSize());

		return result;
		
	}

	/*
	@Override
	public Object invoke(MethodInvocation arg0) throws Throwable {
		// TODO Auto-generated method stub
		
		Method targetMethod = arg0.getMethod();
		
		String methodName = targetMethod.getName();
		
		Class clazz = targetMethod.getReturnType();
		
		if(!(METHOD_NAMES.contains(methodName) && clazz.equals(PageBean.class))) {
			
			return arg0.proceed();
		}
		
		Method method = this.getClass().getMethod(methodName,targetMethod.getParameterTypes());
		
		return method.invoke(this, method.getParameters());
	}
	*/
}

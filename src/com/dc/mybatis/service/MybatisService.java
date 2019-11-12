package com.dc.mybatis.service;

import java.util.Map;

import com.dc.base.bean.NodePage;

public interface MybatisService {
	
	/**
	 * 分页查询
	 * @param sqlId 查询ID
	 * @param params 查询参数
	 * @param page 当前页数
	 * @param rows 分页条数
	 * @return 分页结果集合
	 */
	public NodePage findPage(String sqlId,Map<String,String> params,int page,int rows);
	
	/**
	 * 不分页查询
	 * @param sqlId
	 * @param params
	 * @return
	 */
	public NodePage find(String sqlId,Map<String,Object> params);  

	/**
	 * 不分页查询
	 * @param sqlId
	 * @param params
	 * @return
	 */
	public NodePage findCombobox(String sqlId,Map<String,String> params);  
	
}

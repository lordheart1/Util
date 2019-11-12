package com.dc.mybatis.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import com.dc.base.bean.NodePage;
import com.dc.mybatis.service.MybatisService;
import com.dc.mybatis.service.config.MybatisConfig;
import com.dc.mybatis.service.config.ProcessToValue;

public class MybatisServiceImpl implements MybatisService {

	private static final String COUNT_FIX = "Count";
	private static final String COUNT_SUM = "c";

	private static final String START_INDEX = "page";
	private static final String PAGE_SIZE = "size";

	private static final String SPLIT = "";

	@Resource(name = "sqlSession")
	private SqlSessionTemplate sqlSessionTemplate;

	@Override
	public NodePage findPage(String sqlId, Map<String, String> params,
			int page, int rows) {

		Map<String, Object> sqlParams = new HashMap<String, Object>(params);

		int startIndex = (page - 1) * rows;

		sqlParams.put(START_INDEX, startIndex);
		sqlParams.put(PAGE_SIZE, rows);

		Object obj = this.sqlSessionTemplate.selectOne(sqlId + COUNT_FIX,
				sqlParams);

		NodePage nodePage = new NodePage();

		int count = 0;

		if (obj instanceof Integer) {
			count = Integer.parseInt(obj.toString());
			nodePage.setCount(count);

			Map<String, String> footer = new HashMap<String, String>();
			footer.put(COUNT_SUM, String.valueOf(count));

			nodePage.setFooter(footer);

		} else {
			Map<String, Object> mapCount = (Map<String, Object>) obj;
			count = Integer.parseInt(mapCount.get(COUNT_SUM).toString());

			nodePage.setCount(count);
			nodePage.setFooter(mapCount);
		}

		List<Map<String,Object>> mybatisList = this.sqlSessionTemplate.selectList(sqlId, sqlParams);
		
		int length = (mybatisList == null) ? 0 : mybatisList.size();
		
		List<Map<String,String>> data = new ArrayList<Map<String,String>>(length);
		
		for(Map<String,Object> mybatisRow : mybatisList){
			
			Map<String,String> row = this.changeRow(mybatisRow);
			
			data.add(row);
		}

		nodePage.setData(data);

		return nodePage;
	}

	@Override
	public NodePage find(String sqlId, Map<String, Object> params) {

		Map<String, Object> sqlParams = new HashMap<String, Object>(params);
		
		NodePage nodePage = new NodePage();
		
		boolean isCount = this.sqlSessionTemplate.getConfiguration().hasKeyGenerator(sqlId + COUNT_FIX);
		
		if(isCount) {
		
			Object obj = this.sqlSessionTemplate.selectOne(sqlId + COUNT_FIX,
					sqlParams);
	
			
	
			int count = 0;
	
			if (obj instanceof Integer) {
				count = Integer.parseInt(obj.toString());
				nodePage.setCount(count);
	
				Map<String, String> footer = new HashMap<String, String>();
				footer.put(COUNT_SUM, String.valueOf(count));
	
				nodePage.setFooter(footer);
	
			} else {
				Map<String, Object> mapCount = (Map<String, Object>) obj;
				count = Integer.parseInt(mapCount.get(COUNT_SUM).toString());
	
				nodePage.setCount(count);
				nodePage.setFooter(mapCount);
			}
		}

		List<Map<String,Object>> mybatisList = this.sqlSessionTemplate.selectList(sqlId, sqlParams);
		
		int length = (mybatisList == null) ? 0 : mybatisList.size();
		
		List<Map<String,String>> data = new ArrayList<Map<String,String>>(length);
		
		for(Map<String,Object> mybatisRow : mybatisList){
			
			Map<String,String> row = this.changeRow(mybatisRow);
			
			data.add(row);
		}

		nodePage.setCount(length);
		nodePage.setData(data);

		return nodePage;
	}
	
	@Override
	public NodePage findCombobox(String sqlId, Map<String, String> params) {
		
		Map<String, String> sqlParams = new HashMap<String, String>(params);
		
		NodePage nodePage = new NodePage();
		
		List<Map<String,Object>> mybatisList = this.sqlSessionTemplate.selectList(sqlId, sqlParams);
		
		int length = (mybatisList == null) ? 0 : mybatisList.size();
		
		List<Map<String,String>> data = new ArrayList<Map<String,String>>(length);
		
		for(Map<String,Object> mybatisRow : mybatisList){
			
			Map<String,String> row = this.changeRow(mybatisRow);
			
			data.add(row);
		}
		
		nodePage.setCount(length);
		nodePage.setData(data);
		
		return nodePage;
	}

	private Map<String, String> changeRow(Map<String, Object> row) {

		Map<String, String> result = new HashMap<String, String>(row.size());

		Set<Entry<String, Object>> es = row.entrySet();

		for (Entry<String, Object> e : es) {

			String key = e.getKey();
			Object obj = e.getValue();

			if (obj == null) {
				continue;
			}

			if (obj instanceof java.lang.String) {

				result.put(key, (String) obj);
				continue;
			}

			ProcessToValue process = MybatisConfig.getProcess(obj.getClass());

			String value = process.process(obj);

			result.put(key, value);

		}

		return result;
	}
}

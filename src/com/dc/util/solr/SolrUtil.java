package com.dc.util.solr;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.dc.util.http.HttpUtil;

import net.sf.json.JSONObject;

@Repository("solrUtil")
public class SolrUtil {
	private static final Logger logger = Logger.getLogger(SolrUtil.class);
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	@Resource(name = "httpUtil")
	private HttpUtil httpUtil;
	private String solrHost;
	private String solrUrl = "/update?wt=json";

	public boolean updateSolr(String id, Map<String, Object> updateValues) {
		if ((updateValues == null) || (updateValues.isEmpty())) {
			return false;
		}
		Map<String, Object> params = new IdentityHashMap<String, Object>();
		for (Map.Entry<String, Object> entry : updateValues.entrySet()) {
			Map<String, Object> setSolrBean = new HashMap<String,Object>(1);

			String value = null;

			Object objValue = entry.getValue();
			if (objValue != null) {
				
				if(objValue instanceof List) {
					
					List list = (List)objValue;
					
					for(Object temp : list) {
						
						params.put(new String(entry.getKey()), temp);
					}
					
					continue;
					
				} else {
					if ((objValue instanceof Date)) {
						Calendar cal = Calendar.getInstance();
	
						cal.setTime((Date) objValue);
	
						int zoneOffset = cal.get(15);
	
						int dstOffset = cal.get(16);
	
						cal.add(14, -(zoneOffset + dstOffset));
	
						logger.debug("格林威治时间:" + new Date(cal.getTimeInMillis()));
	
						value = DATE_FORMAT.format(new Date(cal.getTimeInMillis()));
					} else {
						value = objValue.toString();
					}
				}
			}
			setSolrBean.put("set", value);

			params.put((String) entry.getKey(), setSolrBean);
		}
		params.put("id", id);

		Map<String, Object> jsonMap = new TreeMap();

		jsonMap.put("doc", params);
		jsonMap.put("commitWithin", Integer.valueOf(1));

		Object req = new HashMap(1);

		((Map) req).put("add", jsonMap);

		JSONObject jsonObject = JSONObject.fromObject(req);

		String solrJson = jsonObject.toString();

	//	solrJson = "{\"add\":{\"commitWithin\":1,\"doc\":{\"trace_id\":{\"add\":[\"312\",\"311\",\"315\",\"14\"]},\"id\":\"4607afd1-0071-4f90-80e8-c4a3873c30bc\"}}}";

		String url = this.solrHost + this.solrUrl;
		if (logger.isDebugEnabled()) {
			logger.debug("solrUrl:" + url);
			logger.debug("solrJson:" + solrJson);
		}
		String result = this.httpUtil.sendPost(url, solrJson);

		logger.debug("solrResult:" + result);

		return true;
	}

	public boolean updateSolr(List<String> ids, List<Map<String, Object>> updateValuesList) {
		if ((ids == null) || (ids.isEmpty())) {
			return false;
		}
		if (updateValuesList == null) {
			return false;
		}
		int length = ids.size();
		if (length != updateValuesList.size()) {
			logger.debug("length no equel!");

			return false;
		}
		StringBuilder sb = new StringBuilder();

		sb.append("{");
		for (int i = 0; i < length; i++) {
			String id = (String) ids.get(i);

			Map<String, Object> updateValues = (Map) updateValuesList.get(i);

			Map<String, Object> params = new IdentityHashMap<String, Object>();
			for (Map.Entry<String, Object> entry : updateValues.entrySet()) {
				Map<String, Object> setSolrBean = new HashMap(1);

				setSolrBean.put("set", entry.getValue());

				params.put(new String(entry.getKey()), setSolrBean);
			}
			params.put("id", id);

			Map<String, Object> jsonMap = new TreeMap();

			jsonMap.put("doc", params);
			jsonMap.put("commitWithin", Integer.valueOf(1));

			JSONObject jsonObject = JSONObject.fromObject(jsonMap);

			sb.append("\"add\":").append(jsonObject.toString());

			String last = i == length - 1 ? "}" : ",";

			sb.append(last);
		}
		String solrJson = sb.toString();

		String url = this.solrHost + this.solrUrl;
		if (logger.isDebugEnabled()) {
			logger.debug("solrUrl:" + url);
			logger.debug("solrJson:" + solrJson);
		}
		String result = this.httpUtil.sendPost(url, solrJson);

		logger.debug("solrResult:" + result);

		return true;
	}

	public String getSolrHost() {
		return this.solrHost;
	}

	public void setSolrHost(String solrHost) {
		this.solrHost = solrHost;
	}

	public HttpUtil getHttpUtil() {
		return this.httpUtil;
	}

	public void setHttpUtil(HttpUtil httpUtil) {
		this.httpUtil = httpUtil;
	}
}

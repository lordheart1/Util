package com.dc.util.jsonfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * json处理类
 * @author 小俊俊
 *
 */

@Repository("welJsonDao")
public class WelJsonDao {

	private static Map<String, JSONArray> WEL_ARRAYS = new HashMap<String, JSONArray>();
	private static Map<String, JSONObject> WEL_OBJECTS = new HashMap<String, JSONObject>();
	
	public static JsonConfig JSON_CONFIG = new JsonConfig();
	
	static {
 
		JSON_CONFIG.registerJsonValueProcessor("price", new PriceJsonValueProcessor());
		JSON_CONFIG.registerJsonValueProcessor(Date.class,new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		

	}

	private final static Logger logger = Logger
			.getLogger(WelJsonDao.class);

	@Value("${char_set}")
	private String charSet = "utf-8";

	private String getInputStream(String file) {

		InputStream input = null;

		String json = null;

		try {
			
			File f = new File(file);
			
			String fileName = file;
			
			while(!f.exists()) {
				
				fileName = Thread.currentThread().getContextClassLoader().getResource(file).getFile();
			}
			
			
		//	String fileName = Thread.currentThread().getContextClassLoader().getResource(file).getFile();
			
			input = new FileInputStream(fileName);
			byte[] bs = new byte[input.available()];
			input.read(bs);

			json = new String(bs, charSet);
		} catch (Exception e) {

			logger.error(e.getMessage(), e);
		} finally {

			if (input != null) {

				try {
					input.close();
				} catch (IOException e) {
					logger.warn(e.getMessage(), e);
				}
			}
		}

		return json;
	}

	/**
	 * 从文件中读取jsonArray对象
	 * @param file 文件路径
	 * @param cache 是否从缓存中读
	 * @return jsonArray对象
	 */
	public JSONArray getJsonArray(String file, boolean cache) {

		if (cache) {

			JSONArray obj = WEL_ARRAYS.get(file);

			if (obj != null) {

				return obj;
			}
		}

		String json = this.getInputStream(file);

		JSONArray obj = JSONArray.fromObject(json,JSON_CONFIG);

		WEL_ARRAYS.put(file, obj);

		return obj;
	}

	/**
	 * 从文件中读取json对象
	 * @param file 文件路径
	 * @param cache 是否从缓存中读取
	 * @return json对象
	 */
	public JSONObject getJsonObject(String file, boolean cache) {

		if (cache) {

			JSONObject obj = WEL_OBJECTS.get(file);

			if (obj != null) {

				return obj;
			}
		}
		String json = this.getInputStream(file);
		
		logger.debug("json=" + json);

		JSONObject obj = JSONObject.fromObject(json,JSON_CONFIG);
		
		if (cache) {

			WEL_OBJECTS.put(file, obj);
		}

		return obj;
	}
	
	/**
	 * 保存对象以json的格式到文件
	 * @param obj 需要保存的对象
	 * @param file 文件路径
	 * @return true保存成功
	 */
	public boolean saveObject(Object obj,String file) {
		
		JSONObject jsonObject = null;
		
		if(obj instanceof JSONObject) {
			
			jsonObject = (JSONObject)obj;
			
		} else {
			
			if(!(obj instanceof java.util.Collection)) {
			
				jsonObject = JSONObject.fromObject(obj,JSON_CONFIG);
			}
		}
		
		String json = null;
		
		if(jsonObject != null) {
		
			json = jsonObject.toString();
		} else {
			
			JSONArray jsonArray = JSONArray.fromObject(obj,JSON_CONFIG);
			json = jsonArray.toString();
		}
		
		File f = new File(file);
		
		String fileName = file;
		
		if(!f.exists()) {
			
			 fileName = Thread.currentThread().getContextClassLoader().getResource(file).getFile();
		}
	
		
		Writer output = null;
		
		try {
		
			output = new FileWriter(fileName);
			
			output.write(json);
			
			output.flush();
			
			output.close();
			
			WEL_OBJECTS.put(file, jsonObject);
			
			return true;
		} catch(Exception e) {
			
			logger.error(e.getMessage(),e);
		} finally {
			
			if(output != null) {
				
				try {
					output.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		
		return false;
	}
	
	/**
	 * 保存对象以json的格式到文件
	 * @param obj 需要保存的对象
	 * @param file 文件
	 * @return true保存成功
	 */
	public boolean saveObject(Object obj,File file) {
		
		JSONObject jsonObject = JSONObject.fromObject(obj,JSON_CONFIG);
		
		String json = jsonObject.toString();
		
		if(file.exists()) {
			
			file.delete();
		}
		
		if(!file.getParentFile().exists()) {
			
			file.getParentFile().mkdirs();
		}
		
		Writer output = null;
		
		try {
			
			file.createNewFile();
			
			output = new FileWriter(file);
			
			output.write(json);
			
			output.flush();
			
			output.close();
			
	//		WEL_OBJECTS.put(file, jsonObject);
			
			return true;
		} catch(Exception e) {
			
			logger.error(e.getMessage(),e);
		} finally {
			
			if(output != null) {
				
				try {
					output.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		
		return false;
		
		
	}
	
	/**
	 * 修改json文件中的个别值
	 * @param jsonObject json对象
	 * @param key key值
	 * @param value value值
	 */
	public  void putValue(JSONObject jsonObject,String key,String value) {
		
		if(value == null || value.trim().equals("")) {
			
			return;
		}
		
		jsonObject.put(key, value);
	}
}
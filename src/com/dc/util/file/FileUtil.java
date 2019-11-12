package com.dc.util.file;

import java.text.DecimalFormat;

/**
 * 静态文件工具
 * @author 小俊俊
 *
 */
public class FileUtil {

	private static final DecimalFormat PATH_FORMATE = new DecimalFormat("0000,0000,0000,0000");
	private static final String PATH = ",";
	private static final String FILE_PATH = "/";
	
	/**
	 * 更具ID生成目录
	 * @param id ID
	 * @return 目录层级
	 */
	public static String getFilePath(Integer id) {
		
		String path = PATH_FORMATE.format(id);
		
		path = path.replaceAll(PATH,FILE_PATH);
		
		return path;
	}
}

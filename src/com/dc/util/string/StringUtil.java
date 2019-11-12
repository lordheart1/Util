package com.dc.util.string;

import java.math.BigDecimal;

/**
 * 字符串常用功能小工具
 * @author 小俊俊
 *
 */
public class StringUtil {
	
	 private static final char[] cnArr = new char[]{'一','二','三','四','五','六','七','八','九'};
	 private static final char[] chArr = new char[]{'十','百','千','万','亿'};

	/**
	 * 判断字符串是否为空
	 * @param value 字符串
	 * @return true 为空
	 */
	public static boolean isEmpty(String value) {

		if (value == null || value.trim().equals("")) {

			return true;
		}

		return false;
	}
	
	
	public static String trim(String value) {
		
		if (value == null || value.trim().equals("")) {
			
			return value;
		}
		
		return value.trim();
	}
	
	/**
	 * 转换为int
	 * @param value
	 * @return
	 */
	public static int convertToInteger(String value) {
		
		if (value == null || value.trim().equals("")) {
			
			return 0;
		}
		
		return Integer.valueOf(value);
	}
	
	/**
	 * 转换为long
	 * @param value
	 * @return
	 */
	public static long convertToLong(String value) {
		
		if (value == null || value.trim().equals("")) {
			
			return 0;
		}

		return Long.valueOf(value);
	}
	
	/**
	 * 转换为float
	 * @param value
	 * @return
	 */
	public static float convertToFloat(String value) {
		
		if (value == null || value.trim().equals("")) {
			
			return 0;
		}
		
		return Float.valueOf(value);
	}
	
	/**
	 * 转换为byte
	 * @param value
	 * @return
	 */
	public static byte convertToByte(String value) {
		
		if (value == null || value.trim().equals("")) {
			
			return 0;
		}
		
		return Byte.valueOf(value);
	}
	
	/**
	 * 转换为short
	 * @param value
	 * @return
	 */
	public static short convertToShort(String value) {
		
		if (value == null || value.trim().equals("")) {
			
			return 0;
		}
		
		return Short.valueOf(value);
	}
	
	/**
	 * 转换为BigDecimal
	 * @param value
	 * @return BigDecimal
	 */
	public static BigDecimal convertToBigDecimal(String value) {
		
		if (value == null || value.trim().equals("")) {
			
			return BigDecimal.ZERO;
		}
		
		return new BigDecimal(value);
	}

	public static String upFirst(String str) {

		StringBuilder sb = new StringBuilder(str);
		sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
		return sb.toString();

	}
	
	public static String solrQuery(String value) {
		
		String result = value.replaceAll("\\:", "\\\\:");
		
		result = result.replaceAll("\\(", "\\\\(");
		result = result.replaceAll("\\)", "\\\\)");
	
		return result;
	}
	
	public static String toString(Object obj) {
		
		if(obj == null) {
			return null;
		}
		return obj.toString();
	}
	
	/**
	 * 
	 * @param chineseNumber
	 * @return
	 */
	public static int chineseNumber2Int(String chineseNumber){
		  int result = 0;
		  int temp = 1;//存放一个单位的数字如：十万
		  int count = 0;//判断是否有chArr
		
		  for (int i = 0; i < chineseNumber.length(); i++) {
		    boolean b = true;//判断是否是chArr
		    char c = chineseNumber.charAt(i);
		    for (int j = 0; j < cnArr.length; j++) {//非单位，即数字
		      if (c == cnArr[j]) {
		        if(0 != count){//添加下一个单位之前，先把上一个单位值添加到结果中
		          result += temp;
		          temp = 1;
		          count = 0;
		        }
		        // 下标+1，就是对应的值
		        temp = j + 1;
		        b = false;
		        break;
		      }
		    }
		    if(b){//单位{'十','百','千','万','亿'}
		      for (int j = 0; j < chArr.length; j++) {
		        if (c == chArr[j]) {
		          switch (j) {
		          case 0:
		            temp *= 10;
		            break;
		          case 1:
		            temp *= 100;
		            break;
		          case 2:
		            temp *= 1000;
		            break;
		          case 3:
		            temp *= 10000;
		            break;
		          case 4:
		            temp *= 100000000;
		            break;
		          default:
		            break;
		          }
		          count++;
		        }
		      }
		    }
		    if (i == chineseNumber.length() - 1) {//遍历到最后一个字符
		      result += temp;
		    }
		  }
		  return result;
		}
}
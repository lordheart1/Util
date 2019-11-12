package com.dc.util.date;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;



/**
 * 
 * @author 小俊俊
 * 日期格式转换工具
 */
public class DateUtil {
	
	private static final Logger logger = Logger.getLogger(DateUtil.class);
	
	public static final String PATTERN_yyyy_MM_dd = "yyyy-MM-dd";
	public static final String PATTERNpyyyypMMpddHH_mm_ss = "yyyy.MM.dd HH:mm:ss";
	public static final String PATTERN_yyyyMMdd = "yyyyMMdd";
	public static final String PATTERN_yyMMdd = "yyMMdd";
	public static final String PATTERN_yyyyMM = "yyyyMM";
	public static final String PATTERN_MMdd = "MM-dd";
	public static final String PATTERN_yyyy_MM = "yyyy-MM";
	public static final String PATTERN_dd = "dd";
	public static final String PATTERN_yy_MM_dd = "yy/MM/dd";
	public static final String PATTERN_yyyyMMddHHmmss = "yyyyMMddHHmmss";
	public static final String PATTERN_yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
	public static final String PATTERN_HH_mm_ss = "HH:mm:ss";
	public static final String PATTERN_HHmmss = "HHmmss";
	public static final String PATTERN_yyyy = "yyyy";
	public static final String PATTERN_HH_mm = "HH:mm";
	
	public static final String PATTERN_SOLR = "yyyy-MM-dd";
	

	public static final String PATTERN_WEEK = "MM月dd号";
	public static final String PATTERN_MONTH = "yyyy年MM月";
	public static final String PATTERN_YEAR = "yyyy年";
	
	public static final String PATTERN_zhcn_yyyy_MM_dd = "yyyy年MM月dd日";

	public static final long DAY_IN_MILLISECOND = 24 * 60 * 60 * 1000;
	public static final long HOUR_IN_MILLISECOND = 60 * 60 * 1000;

	public static final Timestamp MYSQL_INFINITE_TIME = Timestamp.valueOf("9999-12-31 23:59:59");

	public DateUtil() {
	}

	/**
	 * 获取当前时间
	 * @return 当前时间
	 */
	public static Date getNowUtilDate() {
		return new Date();
	}
	
	/**
	 * 获取当前时间
	 * @return 当前时间
	 */
	public static Timestamp getNowTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	public static Date createUtilDate(int i, int j, int k, int l, int i1, int j1) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(i, j - 1, k, l, i1, j1);
		calendar.set(14, 0);
		return new Date(calendar.getTimeInMillis());
	}

	public static Date createUtilDate(int i, int j, int k) {
		return createUtilDate(i, j, k, 0, 0, 0);
	}

	public static java.sql.Date createSqlDate(int i, int j, int k, int l,
			int i1, int j1) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(i, j - 1, k, l, i1, j1);
		calendar.set(14, 0);
		return new java.sql.Date(calendar.getTimeInMillis());
	}

	public static java.sql.Date createSqlDate(int i, int j, int k) {
		return createSqlDate(i, j, k, 0, 0, 0);
	}

	/**
	 * 日期对象格式化
	 * @param date 日期
	 * @param s 格式化模板
	 * @return 格式化后字符串
	 */
	public static String convertDateToString(Date date, String s) {
		if (date == null) {
			return null;
		}
		if (s == null || s.length() == 0)
			s = PATTERN_yyyy_MM_dd_HH_mm_ss;
		SimpleDateFormat simpledateformat = new SimpleDateFormat(s);
		return simpledateformat.format(date);
	}

	/**
	 * 重新格式化字符串
	 * @param date 日期字符串
	 * @param oldFmt 元格式化模板
	 * @param newFmt 新格式化模板
	 * @return 按新模板格式化后的字符串
	 */
	public static String convertDateFormat(String date, String oldFmt,
			String newFmt) {
		if (date == null) {
			return null;
		}

		return DateUtil.convertDateToString(DateUtil.createUtilDate(date,
				oldFmt), newFmt);
	}

	/**
	 * 按默认模板的格式化字符串 （yyyy-MM-dd HH:mm:ss）
	 * @param date 日期对象
	 * @return 格式化字符串
	 */
	public static String convertDateToString(Date date) {
		return convertDateToString(date, PATTERN_yyyy_MM_dd_HH_mm_ss);
	}

	/**
	 * 更具字符串创建日期格式
	 * @param s 日期字符串
	 * @return 日期对象
	 */
	public static Timestamp createTimestamp(String s) {
		if (s == null || s.length() == 0)
			return null;
		try {
			Date date = createUtilDate(s);
			if (date != null)
				return new Timestamp(date.getTime());
			return Timestamp.valueOf(s);
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
	}

	public static java.sql.Date createSqlDate(String s) {
		if (s == null || s.length() == 0)
			return null;
		Date date = createUtilDate(s);
		if (date != null)
			return new java.sql.Date(date.getTime());
		return java.sql.Date.valueOf(s);

	}

	public static java.sql.Date createSqlDate(String s, String s1) {
		if (s == null || s.length() == 0)
			return null;
		if (s1 == null || s1.length() == 0)
			return createSqlDate(s);
		try {
			Date date;
			SimpleDateFormat simpledateformat = new SimpleDateFormat(s1.trim());
			date = simpledateformat.parse(s.trim());
			if (date != null)
				return new java.sql.Date(date.getTime());
			return java.sql.Date.valueOf(s);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 更具日期格式模板创建日期对象
	 * 
	 * @param dateStr
	 *            日期字符串
	 * @param format
	 *            日期格式模板
	 * @return
	 */
	public static Date createUtilDate(String dateStr, String format) {
		if (dateStr == null || dateStr.length() == 0)
			return null;
		if (format == null || format.length() == 0)
			return createUtilDate(dateStr);
		try {
			SimpleDateFormat simpledateformat = new SimpleDateFormat(format
					.trim());
			return simpledateformat.parse(dateStr.trim());
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
	}

	/**
	 * 更具日期字符串创建日期
	 * @param s 日期字符串
	 * @return 日期
	 */
	public static Date createUtilDate(String s) {
		if (s == null || s.length() == 0)
			return null;
		String s1;
		s1 = "";
		s = s.trim();
		if (s.length() == 10) {
			if (s.indexOf("/") != -1)
				s1 = "yyyy/MM/dd";

			else if (s.indexOf("-") != -1)
				s1 = "yyyy-MM-dd";

		} else if (s.length() == 19) {
			if (s.indexOf("/") != -1)
				s1 = "yyyy-MM-dd HH:mm:ss";

			else if (s.indexOf("-") != -1)
				s1 = "yyyy-MM-dd HH:mm:ss";

		} else if (s.length() == 8)
			s1 = "yyyyMMdd";
		try {
			SimpleDateFormat simpledateformat = new SimpleDateFormat(s1);
			return simpledateformat.parse(s.trim());
		} catch (Exception exception) {
			return null;
		}
	}

	/**
	 * 日期转Calendar
	 * @param date 日期对象
	 * @return calendar对象
	 */
	public static Calendar convertDateToCalendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	public static Timestamp convertCalendarToTimes(Calendar calendar) {
		return new Timestamp(calendar.getTimeInMillis());
	}

	public static Date convertCalendarToUtilDate(Calendar calendar) {
		return new Date(calendar.getTimeInMillis());
	}

	public static java.sql.Date convertCalendarToSqlDate(Calendar calendar) {
		return new java.sql.Date(calendar.getTimeInMillis());
	}

	public static int getYear(Date date) {
		return convertDateToCalendar(date).get(1);
	}

	public static int getMonth(Date date) {
		return convertDateToCalendar(date).get(2) + 1;
	}

	public static int getDayOfMonth(Date date) {
		return convertDateToCalendar(date).get(5);
	}

	public static int getHour(Date date) {
		return convertDateToCalendar(date).get(11);
	}

	public static int getMinute(Date date) {
		return convertDateToCalendar(date).get(12);
	}

	public static int getSecond(Date date) {
		return convertDateToCalendar(date).get(13);
	}

	public static int getMillisecond(Date date) {
		return convertDateToCalendar(date).get(14);
	}

	public static long getMilliseconds(Date date) {
		return date.getTime();
	}
	
	public static long getMilliseconds(String dateStr) {
		Date date = createUtilDate(dateStr);
		return date==null?0:date.getTime();
	}

	public static int getDayOfWeek(Date date) {
		return convertDateToCalendar(date).get(7);
	}

	public static Date dateAdd(String s, Date date, int i) {
		Calendar calendar = convertDateToCalendar(date);
		if ("y".equals(s))
			calendar.add(1, i);
		else if ("M".equals(s))
			calendar.add(2, i);
		else if ("d".equals(s))
			calendar.add(5, i);
		else if ("H".equals(s))
			calendar.add(10, i);
		else if ("m".equals(s))
			calendar.add(12, i);
		else if ("s".equals(s))
			calendar.add(13, i);
		else
			return null;
		return convertCalendarToTimes(calendar);
	}

	public static Timestamp dateAdd(int i, Date date, int j) {
		Calendar calendar = convertDateToCalendar(date);
		calendar.add(i, j);
		return convertCalendarToTimes(calendar);
	}

	/**
	 * 是否是闰年
	 * @param date 日期对象
	 * @return true 是闰年
	 */
	public static boolean isLeapYear(Date date) {
		return isLeapYear(getYear(date));
	}

	/**
	 * 是否是闰年
	 * @param i 年份
	 * @return boolean true 是闰年 
	 */
	public static boolean isLeapYear(int i) {
		GregorianCalendar gregoriancalendar = new GregorianCalendar();
		return gregoriancalendar.isLeapYear(i);
	}

	public static Timestamp formatAsTimestamp(Date date) {
		if (date==null) {
			return null;
		}
		return new Timestamp(formatAsUtilDate(date).getTime());
	}

	/**
	 * 获取月的最后一天
	 * @param i 年份
	 * @param j 月份
	 * @return i年j月的最后一天的日期对象
	 */
	public static Date getLastDayOfMonth(int i, int j) {
		if (j == 1 || j == 3 || j == 5 || j == 7 || j == 8 || j == 10
				|| j == 12)
			return createTimestamp(i, j, 31);
		if (j == 4 || j == 6 || j == 9 || j == 11)
			return createTimestamp(i, j, 30);
		if (j == 2) {
			if (isLeapYear(i))
				return createUtilDate(i, j, 29);
			else
				return createUtilDate(i, j, 28);
		} else {
			return null;
		}
	}

	public static Date getLastDayOfMonth(Date date) {
		return getLastDayOfMonth(getYear(date), getMonth(date));
	}

	public static Date formatAsUtilDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(11, 0);
		calendar.set(12, 0);
		calendar.set(13, 0);
		calendar.set(14, 0);
		return calendar.getTime();
	}

	public static java.sql.Date formatAsSqlDate(java.sql.Date date) {
		return new java.sql.Date(formatAsUtilDate(date).getTime());
	}

	public static Date getNowDate() {
		Calendar calendar = Calendar.getInstance();
		calendar
				.set(calendar.get(1), calendar.get(2), calendar.get(5), 0, 0, 0);
		calendar.set(14, 0);
		return convertCalToTs(calendar);
	}

	public static java.sql.Date getTodayDate() {
		Calendar calendar = Calendar.getInstance();
		java.sql.Date date = new java.sql.Date(calendar.getTimeInMillis());
		return date;
	}

	public static Timestamp getNowTime() {
		return new Timestamp(System.currentTimeMillis());
	}

	public static Timestamp convertCalToTs(Calendar calendar) {
		return new Timestamp(calendar.getTime().getTime());
	}

	public static Date convertTsToDt(Timestamp timestamp) {
		return new Date(timestamp.getTime());
	}

	public static Timestamp convertDtToTs(Date date) {
		return new Timestamp(date.getTime());
	}

	public static Timestamp createTimestamp(int i, int j, int k) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(i, j - 1, k, 0, 0, 0);
		calendar.set(14, 0);
		return convertCalToTs(calendar);
	}

	public static Timestamp createTimestampBySplit(String s, String s1) {
		if (s == null || s.trim().length() < 1)
			return null;
		if ("".equals(s1))
			s1 = "-";
		if (s.lastIndexOf(" ") != -1)
			s = s.substring(0, 10);
		int i;
		int j;
		int k;
		StringTokenizer stringtokenizer = new StringTokenizer(s, s1);
		i = Integer.parseInt(stringtokenizer.nextToken());
		j = Integer.parseInt(stringtokenizer.nextToken());
		k = Integer.parseInt(stringtokenizer.nextToken());
		return createTimestamp(i, j, k);

	}

	public static int getYear(Timestamp timestamp) {
		
		return convertTsToCal(timestamp).get(1);
	}

	public static int getMonth(Timestamp timestamp) {
		return convertTsToCal(timestamp).get(2) + 1;
	}

	public static int getDate(Timestamp timestamp) {
		return convertTsToCal(timestamp).get(5);
	}

	public static int getHour(Timestamp timestamp) {
		return convertTsToCal(timestamp).get(11);
	}

	public static int getMinute(Timestamp timestamp) {
		return convertTsToCal(timestamp).get(12);
	}

	public static int getSecond(Timestamp timestamp) {
		return convertTsToCal(timestamp).get(13);
	}

	public static int getMillisecond(Timestamp timestamp) {
		return convertTsToCal(timestamp).get(14);
	}

	public static long getMilliseconds(Timestamp timestamp) {
		return timestamp.getTime();
	}

	public static int getDay(Timestamp timestamp) {
		return convertTsToCal(timestamp).get(7);
	}

	public static String getStrDay(Timestamp timestamp) {
		if (timestamp == null)
			return null;
		int i = getDay(timestamp);
		String s = "";
		switch (i) {
		case 1: // '\001'
			s = "\u661F\u671F\u5929";
			break;

		case 2: // '\002'
			s = "\u661F\u671F\u4E00";
			break;

		case 3: // '\003'
			s = "\u661F\u671F\u4E8C";
			break;

		case 4: // '\004'
			s = "\u661F\u671F\u4E09";
			break;

		case 5: // '\005'
			s = "\u661F\u671F\u56DB";
			break;

		case 6: // '\006'
			s = "\u661F\u671F\u4E94";
			break;

		case 7: // '\007'
			s = "\u661F\u671F\u516D";
			break;

		default:
			s = "";
			break;
		}
		return s;
	}

	public static Timestamp dateAdd(String s, Timestamp timestamp, int i) {
		Calendar calendar = Calendar.getInstance();
		calendar = convertTsToCal(timestamp);
		if ("yy".equals(s))
			calendar.add(1, i);
		else if ("mm".equals(s))
			calendar.add(2, i);
		else if ("dd".equals(s))
			calendar.add(5, i);
		else if ("hh".equals(s))
			calendar.add(10, i);
		else if ("mi".equals(s))
			calendar.add(12, i);
		else if ("ss".equals(s))
			calendar.add(13, i);
		else
			return null;
		return convertCalToTs(calendar);
	}

	public static int dateDiff(String s, Date date1, Date date2) {
		Timestamp timestamp = new Timestamp(date1.getTime());
		Timestamp timestamp1 = new Timestamp(date2.getTime());

		return dateDiff(s, timestamp, timestamp1);
	}

	public static int dateDiff(String s, Timestamp timestamp,
			Timestamp timestamp1) {
		if (timestamp == null || timestamp1 == null)
			return -1;
		Date date = null;
		Date date1 = null;
		date = new Date(timestamp.getTime());
		date1 = new Date(timestamp1.getTime());
		Calendar calendar = null;
		Calendar calendar1 = null;
		calendar = Calendar.getInstance();
		calendar1 = Calendar.getInstance();
		calendar.setTime(date);
		long l = date.getTime() + (long) calendar.get(15)
				+ (long) calendar.get(16);
		calendar1.setTime(date1);
		long l1 = date1.getTime() + (long) calendar1.get(15)
				+ (long) calendar1.get(16);
		int i = (int) (l / 0x36ee80L);
		int j = (int) (l1 / 0x36ee80L);
		int k = i / 24;
		int i1 = j / 24;
		int j1 = i1 - k;
		int k1 = calendar1.get(7) - calendar.get(7) >= 0 ? 0 : 1;
		int i2 = j1 / 7 + k1;
		int j2 = calendar1.get(1) - calendar.get(1);
		int k2 = (j2 * 12 + calendar1.get(2)) - calendar.get(2);
		if ("YEAR".equals(s))
			return j2;
		if ("MONTH".equals(s))
			return k2;
		if ("DATE".equals(s))
			return j1;
		if ("WEEK".equals(s))
			return i2;
		else
			return j1;
	}

	
	public static boolean isMatchDate(int i, int j, int k, Timestamp timestamp) {
		int l = getYear(timestamp);
		int i1 = getMonth(timestamp);
		int j1 = getDay(timestamp);
		if (i != -1 && i != l)
			return false;
		if (j != -1 && j != i1)
			return false;
		return k == -1 || k == j1;
	}

	public static Timestamp getFirstDayOfMonth(String s, String s1) {
		if (s == null || s1 == null) {
			return null;
		} else {
			int i = Integer.valueOf(s).intValue();
			int j = Integer.valueOf(s1).intValue();
			return createTimestamp(i, j, 1);
		}
	}

	
	public static boolean isWorkday(Timestamp timestamp) {
		int i = getMonth(timestamp);
		int j = getDate(timestamp);
		int k = getDay(timestamp);
		if (k == 1 || k == 7)
			return false;
		if (i == 1 && 1 <= j && j <= 3)
			return false;
		if (i == 5 && 1 <= j && j <= 7)
			return false;
		return i != 10 || 1 > j || j > 7;
	}

	public static Timestamp formatToDate(Timestamp timestamp) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timestamp.getTime());
		calendar.set(11, 0);
		calendar.set(12, 0);
		calendar.set(13, 0);
		calendar.set(14, 0);
		return new Timestamp(calendar.getTime().getTime());
	}

	public static Date formatToDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(11, 0);
		calendar.set(12, 0);
		calendar.set(13, 0);
		calendar.set(14, 0);
		return calendar.getTime();
	}

	public static java.sql.Date formatToDate(java.sql.Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date.getTime());
		calendar.set(11, 0);
		calendar.set(12, 0);
		calendar.set(13, 0);
		calendar.set(14, 0);
		return new java.sql.Date(calendar.getTime().getTime());
	}

	public static Calendar convertTsToCal(Timestamp timestamp) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(timestamp.getTime()));
		return calendar;
	}

	public static String convertTsToStr(Timestamp timestamp) {
		if (timestamp != null)
			return timestamp.toString().substring(0, 10);
		else
			return "";
	}

	public static String convertTsToStrWithSecs(Timestamp timestamp) {
		if (timestamp != null)
			return timestamp.toString().substring(0, 19);
		else
			return "";
	}

	public static String convertTsToStrWithDayOfWeek(Timestamp timestamp) {
		if (timestamp != null)
			return timestamp.toString().substring(0, 10) + " "
					+ getStrDay(timestamp);
		else
			return "";
	}

	public static Timestamp createTimestamp(int i, int j, int k, int l, int i1,
			int j1) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(i, j - 1, k, l, i1, j1);
		calendar.set(14, 0);
		calendar.set(9, 0);
		return convertCalToTs(calendar);
	}

	public static Timestamp createTimestamp(String s, String s1) {
		if (s == null || s.trim().length() < 1)
			return null;
		if ("".equals(s1))
			s1 = "-";
		if (s.lastIndexOf(" ") != -1)
			s = s.substring(0, 10);
		int i;
		int j;
		int k;
		StringTokenizer stringtokenizer = new StringTokenizer(s, s1);
		i = Integer.parseInt(stringtokenizer.nextToken());
		j = Integer.parseInt(stringtokenizer.nextToken());
		k = Integer.parseInt(stringtokenizer.nextToken());
		return createTimestamp(i, j, k);

	}

	/**
	 * 璇诲彇 寮�鏀剧殑鑰冭瘯鍦烘閲岀殑鏈�灏忔棩鏈熷拰鏈�澶ф棩鏈�
	 * 
	 * @param exams
	 * @return Object[0] 鏈�灏忔棩鏈� Object[1] 鏈�澶ф棩鏈�
	 */
	public static Object[] getPairDate(List<Map<String, Object>> exams) {
		Object[] pairDateObj = new Object[2];
		if (exams != null && !exams.isEmpty()) {
			Set<String> dateSet = new HashSet<String>();

			int size = exams.size();
			for (int i = 0; i < size; i++) {
				Map<String, Object> map = exams.get(i);
				Object value = map.get("EXAM_DATE");
				if (value != null) {
					dateSet.add(value.toString());
				}
			}

			if (!dateSet.isEmpty()) {
				List<String> dateList = new ArrayList<String>();
				dateList.addAll(dateSet);
				Collections.sort(dateList);

				String minDateStr = dateList.get(0);
				String maxDateStr = dateList.get(dateList.size() - 1);
				pairDateObj[0] = DateUtil.createUtilDate(minDateStr,
						DateUtil.PATTERN_yyyyMMddHHmmss);
				pairDateObj[1] = DateUtil.createUtilDate(maxDateStr,
						DateUtil.PATTERN_yyyyMMddHHmmss);
			}
		}
		return pairDateObj;
	}

	@Deprecated
	/**
	 * 姝ゅ閫昏緫鏈夊緟鍟嗘Ψ 鍙傝�� getMinDateOrMaxDate
	 */
	public static Date getDate(List<Map<String, Object>> exams, boolean end) {
		Date date = null;
		if (exams != null && !exams.isEmpty()) {
			Set<String> dateSet = new HashSet<String>();

			int size = exams.size();
			for (int i = 0; i < size; i++) {
				Map<String, Object> map = exams.get(i);
				Object value = null;
				if (end) {
					value = map.get("EXAM_DATE");
				} else {
					value = map.get("START_TIME");
				}
				if (value != null) {
					dateSet.add(value.toString());
				}
			}

			if (!dateSet.isEmpty()) {
				List<String> dateList = new ArrayList<String>();
				dateList.addAll(dateSet);
				Collections.sort(dateList);

				String d = null;
				if (end) {
					d = dateList.get(dateList.size() - 1);
				} else {
					d = dateList.get(0);
				}

				if (d != null) {
					date = DateUtil.createUtilDate(d,
							DateUtil.PATTERN_yyyyMMddHHmmss);
				}
			}
		}
		return date;
	}

	public static int getBeginExamDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int days = 0;

		if (cal.get(Calendar.DAY_OF_WEEK) == 1) {

		} else if (cal.get(Calendar.DAY_OF_WEEK) == 2) {
			days = -1;
		} else if (cal.get(Calendar.DAY_OF_WEEK) == 3) {
			days = -2;
		} else if (cal.get(Calendar.DAY_OF_WEEK) == 4) {
			days = -3;
		} else if (cal.get(Calendar.DAY_OF_WEEK) == 5) {
			days = -4;
		} else if (cal.get(Calendar.DAY_OF_WEEK) == 6) {
			days = -5;
		} else if (cal.get(Calendar.DAY_OF_WEEK) == 7) {
			days = -6;
		}

		// cal.set(Calendar.DATE, days);
		// return cal.getTime();
		return days;
	}

	/***************************************************************************
	 * 鍒ゆ柇涓�涓椂闂村拰鐩墠鏃堕棿鐨勬瘮杈� <0锛� date2<date1 =0锛� date2=date1 >0锛� date2>date1
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public final static int getDaysBetween(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			return 1;
		}
		Calendar d1 = Calendar.getInstance();
		d1.setTime(date1);
		Calendar d2 = Calendar.getInstance();
		d2.setTime(date2);
		int days = d2.get(java.util.Calendar.DAY_OF_YEAR)
				- d1.get(java.util.Calendar.DAY_OF_YEAR);
		if (d1.get(java.util.Calendar.YEAR) > d2.get(java.util.Calendar.YEAR)) {
			d1 = (java.util.Calendar) d1.clone();
			do {
				days -= d1.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);
				// System.out.println("-----"+days);
				d2.add(java.util.Calendar.YEAR, 1);
			} while (d1.get(java.util.Calendar.YEAR) != d2
					.get(java.util.Calendar.YEAR));
		}
		if (d1.get(java.util.Calendar.YEAR) < d2.get(java.util.Calendar.YEAR)) {
			d1 = (java.util.Calendar) d1.clone();
			do {
				days += d1.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);
				// System.out.println("+++++"+days);
				d1.add(java.util.Calendar.YEAR, 1);
			} while (d1.get(java.util.Calendar.YEAR) != d2
					.get(java.util.Calendar.YEAR));
		}
		return days;
	}

	public static Date parseDate(String dateString, String pattern) {
		try {
			return org.apache.commons.lang.time.DateUtils.parseDate(dateString,
					new String[] { pattern });
		} catch (ParseException e) {
			return null;
		}

	}

	/**
	 * 鍒ゆ柇涓や釜鏃ユ湡鐨勬棭鏅�
	 * 
	 * @param firstDate
	 *            yyyyMMddHHmmss
	 * @param secondDate
	 *            yyyyMMddHHmmss
	 * @return true firstDate 鏃╀簬 secondDate锛屽惁鍒欎负false
	 */
	public final static boolean isBefor(String firstDateStr,
			String secondDateStr, String pattern) {
		Date d1 = parseDate(firstDateStr, PATTERN_yyyyMMddHHmmss);
		Date d2 = parseDate(secondDateStr, PATTERN_yyyyMMddHHmmss);

		return d1.before(d2);
	}

	public static String getBeginDayDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.HOUR_OF_DAY, 0);

		date = c.getTime();
		return DateUtil.convertDateToString(date,
				DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss);
	}

	public static String getEndDayDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.HOUR_OF_DAY, 23);

		date = c.getTime();
		return DateUtil.convertDateToString(date,
				DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss);
	}

	public static String getBeginMonthDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DATE, 1);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.HOUR_OF_DAY, 0);

		date = c.getTime();
		return DateUtil.convertDateToString(date,
				DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss);
	}

	public static String getEndMonthDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DATE, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.HOUR_OF_DAY, 23);

		date = c.getTime();
		return DateUtil.convertDateToString(date,
				DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss);
	}

	public static String getOperateDate(String operateDate) {
		if (operateDate == null) {
			return null;
		}
		Date date = DateUtil.createUtilDate(operateDate,
				DateUtil.PATTERN_yyyyMMddHHmmss);

		if (date == null) {
			return null;
		}
		return DateUtil.convertDateToString(date,
				DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss);
	}

	public static String getNowStr() {
		Date date = new Date();
		return "鏃堕棿鏄細"
				+ DateUtil.convertDateToString(date,
						DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss);
	}
	/*****
	 * 鑾峰彇浠婂ぉ绠�鍗曟棩鏈�(20130330)
	 * @return
	 */
	public static String getTodayString() {
		Date date = new Date();
		return  DateUtil.convertDateToString(date,DateUtil.PATTERN_yyyyMMdd);
	}
	
	/**\
	 * 鑾峰彇褰撴湀 (2013-08)
	 * @return
	 */
	public static String getCurrMonString() {
		Date date = new Date();
		return  DateUtil.convertDateToString(date,DateUtil.PATTERN_yyyy_MM);
	}

	/****
	 * 璇ユ柟娉曡浆鎹㈠帇缂╂棩鏈熸暟鎹殑鏃堕棿绫诲瀷
	 *
	 * @param data(褰㈠紡濡�:20130331)
	 * @return 
	 * @throws ParseException 
	 */
	public static Date formatSimpleDate(String data) throws ParseException {
		if (data.length()!=8) {
			return null;
		}
		Date date = null;
		SimpleDateFormat format=new SimpleDateFormat(PATTERN_yyyyMMdd);
		String soruce=data;
		date=format.parse(soruce);
		return date;
	}
	
	public static String getStringDate(Date date) {
		SimpleDateFormat format=new SimpleDateFormat(PATTERN_yyyy_MM_dd_HH_mm_ss);
		return format.format(date);
	}
	
	public static String formatDateToString(Date date,String pattern) {
		if (date==null) {
			return "";
		}
		SimpleDateFormat format=new SimpleDateFormat(pattern);
		return format.format(date);
	}
	
	public static String getFirstDateOfMonth() {
		Calendar currDate = Calendar.getInstance();
		currDate.set(currDate.DAY_OF_MONTH, 1);
		currDate.set(currDate.HOUR, 0);
		currDate.set(currDate.MINUTE, 0);
		currDate.set(currDate.SECOND, 0);
		return formatDateToString(currDate.getTime(), PATTERN_yyyy_MM_dd_HH_mm_ss);
	}
	
	public static String getLastDateOfMonth() {
		Calendar currDate = Calendar.getInstance();
		int day = currDate.getActualMaximum(currDate.DAY_OF_MONTH);
		currDate.set(currDate.DAY_OF_MONTH, day);
		currDate.set(currDate.HOUR_OF_DAY, 23);
		currDate.set(currDate.MINUTE, 59);
		currDate.set(currDate.SECOND, 59);
		return formatDateToString(currDate.getTime(), PATTERN_yyyy_MM_dd_HH_mm_ss);
	}
	
	public static Date getFirstDayOfMonth() {
		Calendar currDate = Calendar.getInstance();
		currDate.set(currDate.DAY_OF_MONTH, 1);
		currDate.set(currDate.HOUR, 0);
		currDate.set(currDate.MINUTE, 0);
		currDate.set(currDate.SECOND, 0);
		return currDate.getTime();
	}
	
	public static Date getLastDayOfMonth() {
		Calendar currDate = Calendar.getInstance();
		int day = currDate.getActualMaximum(currDate.DAY_OF_MONTH);
		currDate.set(currDate.DAY_OF_MONTH, day);
		currDate.set(currDate.HOUR_OF_DAY, 23);
		currDate.set(currDate.MINUTE, 59);
		currDate.set(currDate.SECOND, 59);
		return currDate.getTime();
	}
	
	public static Date getFirstDayOfThreeMonth(int month) {
		Calendar currDate = Calendar.getInstance();
		currDate.add(Calendar.MONTH, -month);
		currDate.set(currDate.DAY_OF_MONTH, 1);
		currDate.set(currDate.HOUR, 0);
		currDate.set(currDate.MINUTE, 0);
		currDate.set(currDate.SECOND, 0);
		return currDate.getTime();
	}
	
	/** 
     * 鑾峰彇褰撳勾鐨勭涓�澶� 
     * @param year 
     * @return 
     */  
    public static Date getCurrYearFirst(){  
        Calendar currCal=Calendar.getInstance();    
        int currentYear = currCal.get(Calendar.YEAR);  
        return getYearFirst(currentYear);  
    }  
      
    /** 
     * 鑾峰彇褰撳勾鐨勬渶鍚庝竴澶� 
     * @param year 
     * @return 
     */  
    public static Date getCurrYearLast(){  
        Calendar currCal=Calendar.getInstance();    
        int currentYear = currCal.get(Calendar.YEAR);  
        return getYearLast(currentYear);  
    }
    
	/** 
     * 鑾峰彇鏌愬勾绗竴澶╂棩鏈� 
     * @param year 骞翠唤 
     * @return Date 
     */  
    public static Date getYearFirst(int year){  
        Calendar calendar = Calendar.getInstance();  
        calendar.clear();  
        calendar.set(Calendar.YEAR, year);  
        Date currYearFirst = calendar.getTime();  
        return currYearFirst;  
    }  
      
    /** 
     * 鑾峰彇鏌愬勾鏈�鍚庝竴澶╂棩鏈� 
     * @param year 骞翠唤 
     * @return Date 
     */  
    public static Date getYearLast(int year){  
        Calendar calendar = Calendar.getInstance();  
        calendar.clear();  
        calendar.set(Calendar.YEAR, year);  
        calendar.roll(Calendar.DAY_OF_YEAR, -1);  
        Date currYearLast = calendar.getTime();  
          
        return currYearLast;  
    }
    
    
    
	
	

	public static Date getFirstDateOfWeek() {
		Calendar monday = Calendar.getInstance();
		return getADayOfWeek(monday.getTime(), Calendar.MONDAY);
	}

	public static Date getFirstDateOfWeek(Date date) {
		return getADayOfWeek(date, Calendar.MONDAY);
	}

	public static Date getLastDateOfWeek() {
		Calendar sunday = Calendar.getInstance();
		return getADayOfWeek(sunday.getTime(), Calendar.SUNDAY);
	}

	public static Date getLastDateOfWeek(Date date) {
		return getADayOfWeek(date, Calendar.SUNDAY);
	}

	private static Date getADayOfWeek(Date day, int dayOfWeek) {
		
		Calendar c = Calendar.getInstance();
		
		c.setTime(day);
		
		int week = c.get(Calendar.DAY_OF_WEEK);
		if (week == dayOfWeek)
			return c.getTime();
		int diffDay = dayOfWeek - week;
		if (week == Calendar.SUNDAY) {
			diffDay -= 7;
		} else if (dayOfWeek == Calendar.SUNDAY) {
			diffDay += 7;
		}
		c.add(Calendar.DATE, diffDay);
		return c.getTime();
	}
	
	
	public static boolean isInDates(String strDate,String strDateBegin,String strDateEnd){ 
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date myDate = null;
		Date dateBegin = null;
		Date dateEnd = null;
		try {
			myDate = sd.parse(strDate);
			dateBegin = sd.parse(strDateBegin);
			dateEnd = sd.parse(strDateEnd);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		strDate = String.valueOf(myDate);
		strDateBegin = String.valueOf(dateBegin);
		strDateEnd = String.valueOf(dateEnd);
		
		int strDateH = Integer.parseInt(strDate.substring(11,13));
		int strDateM = Integer.parseInt(strDate.substring(14,16));
		int strDateS = Integer.parseInt(strDate.substring(17,19));
		
		int strDateBeginH = Integer.parseInt(strDateBegin.substring(11,13));
		int strDateBeginM = Integer.parseInt(strDateBegin.substring(14,16));
		int strDateBeginS = Integer.parseInt(strDateBegin.substring(17,19));
		
		int strDateEndH = Integer.parseInt(strDateEnd.substring(11,13));
		int strDateEndM = Integer.parseInt(strDateEnd.substring(14,16));
		int strDateEndS = Integer.parseInt(strDateEnd.substring(17,19));
		
		if((strDateH>=strDateBeginH && strDateH<=strDateEndH)){
			if(strDateH>strDateBeginH && strDateH<strDateEndH){
				return true;
			}else if(strDateH==strDateBeginH && strDateM>strDateBeginM && strDateH<strDateEndH){
				return true;
			}else if(strDateH==strDateBeginH && strDateM==strDateBeginM && strDateS>strDateBeginS && strDateH<strDateEndH){
				return true;
			}else if(strDateH==strDateBeginH && strDateM==strDateBeginM && strDateS==strDateBeginS && strDateH<strDateEndH){
				return true;
			}else if(strDateH>strDateBeginH && strDateH==strDateEndH && strDateM<strDateEndM){
				return true;
			}else if(strDateH>strDateBeginH && strDateH==strDateEndH && strDateM==strDateEndM && strDateS<strDateEndS){
				return true;
			}else if(strDateH>strDateBeginH && strDateH==strDateEndH && strDateM==strDateEndM && strDateS==strDateEndS){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
    }
	
	public static long getHour(Date date1, Date date2){
		
		return (date1.getTime()-date2.getTime())/HOUR_IN_MILLISECOND;
		
	}
	
	public static Date setDay(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, day);
		
		return calendar.getTime();
	}
}

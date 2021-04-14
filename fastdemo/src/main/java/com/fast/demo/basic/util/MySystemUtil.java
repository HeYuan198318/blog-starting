package com.fast.demo.basic.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * @author C3005890
 * 
 */
public class MySystemUtil {

	public static final String regexS = "^\\d+$";
	public final static String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 字符串 防止空 包括空格之類的
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		int strLen;
		if (str != null && (strLen = str.length()) != 0) {
			for (int i = 0; i < strLen; ++i) {
				if (!Character.isWhitespace(str.charAt(i))) {
					return false;
				}
			}
			return true;
		} else {
			return true;
		}
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isStrEmpty(String string) {
		boolean flag = false;
		if (string == null || string.length() < 1) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 字符串转换为 日期格式
	 * 
	 * @param str
	 * @return
	 */
	public static Date StrToDate(String str, String pattern) {
		SimpleDateFormat format = null;
		Date date = null;
		if (isStrEmpty(pattern)) {
			format = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss);
		} else {
			format = new SimpleDateFormat(pattern);
		}
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 字符串转换为 日期格式 yyyy-MM-dd HH-mm-ss
	 * 
	 * @return
	 */
	public static Date StrToDate(String str) {
		SimpleDateFormat format = null;
		Date date = null;
		format = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss);
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * date 转换字符串 成日期格式
	 * 
	 * @param date    源日期
	 * @param pattern 格式
	 * @return
	 */
	public static String DateToStr(Date date, String pattern) {
		SimpleDateFormat format = null;
		if (isStrEmpty(pattern)) {
			format = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss);
		} else {
			format = new SimpleDateFormat(pattern);
		}
		String str = format.format(date);
		return str;
	}

	/**
	 * 计算相差天数 计算二个时间间隔天数，保留整数
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int daysBetween(Date startDate, Date endDate) {
		Date date1 = startDate;
		Date date2 = endDate;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		long time1 = cal.getTimeInMillis();
		cal.setTime(date2);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		Integer count = Integer.parseInt(String.valueOf(between_days));
		if (count < 1) {
			count = 1;
		}
		return count;
	}

	/**
	 * 计算相差天数 计算二个时间间隔天数，保留一位小数
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static String getQuot(Date startDate, Date endDate) {
		String retQuot = "";
		try {
			long timeout = endDate.getTime() - startDate.getTime();

			double quot = ((double) timeout) / 1000 / 60 / 60 / 24;

			DecimalFormat formater = new DecimalFormat();
			formater.setMaximumFractionDigits(1);
			formater.setGroupingSize(0);
			formater.setRoundingMode(RoundingMode.FLOOR);
			retQuot = formater.format(quot);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retQuot;
	}

	/**
	 * 判断字符串 是否是纯数字组成
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumerString(String str) {
		return str.matches(regexS);
	}

	/** 转大写 **/
	private static char charToUpperCase(char ch) {
		if (ch <= 122 && ch >= 97) {
			ch -= 32;
		}
		return ch;
	}

	/**
	 * 轉大寫
	 * 
	 * @param str
	 * @return
	 */
	public static String toUpperCase(String str) {
		char[] ch = str.toCharArray();
		StringBuffer sbf = new StringBuffer();
		for (int i = 0; i < ch.length; i++) {
			sbf.append(charToUpperCase(ch[i]));
		}
		return sbf.toString();
	}

	// --------------------------------------------------------------------------------------------

	/**
	 * 判断对象是否为空(參數變長)(有一個為空則為true)
	 * 
	 * @author C3901094
	 * @date 2019年11月22日 上午11:39:29
	 * @param objs
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Boolean isAnyEmpty(Object... objs) {
		if (objs == null || objs.length == 0)
			return true;
		for (Object obj : objs) {
			if (obj == null)
				return true;
			if (obj instanceof String && "".equals(((String) obj).trim()))
				return true;
			if (obj instanceof Collection && ((Collection) obj).isEmpty())
				return true;
			if (obj instanceof Map) {// 若為Map,則全為空才算空
				Map objMap = ((Map) obj);
				boolean flg = true;
				if (objMap.isEmpty())
					return true;
				for (Object item : objMap.values()) {// 判斷是否全為空
					if (!isAnyEmpty(item)) {
						flg = false;
						break;
					}
				}
				return flg;
			}
		}

		return false;
	}

	/**
	 * 判断对象中属性值是否全为空
	 * @author C3901094
	 * @date 2019年11月22日 上午11:39:57
	 * @param object
	 * @return
	 */
    public static boolean innerFieldsAllEmpty(Object object) {
    	if (null == object) {
        	return true;
        }
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();//獲取字段名
				// 过滤class属性 + 序列化生成的版本號
				if(!key.equals("class") && !"serialVersionUID".equals(key)) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(object);
					if (value != null && !"".equals(value.toString())) {
						return false;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return true;
    }

	/**
     * 判斷對象中存在空屬性值
     * @author C3901094
     * @date 2019年11月22日 上午11:40:15
     * @param object
     * @return
     */
	public static boolean existNullInnerField(Object object) {
		if (null == object) {
			return true;
		}
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();//獲取字段名
				// 过滤class属性
				if(!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(object);
					if (value == null || "".equals(value.toString())) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * -給定日期之初
	 * 
	 * @author C3901094
	 * @date 2020年5月4日 上午11:21:09
	 * @param date
	 * @return
	 */
	public static Date getDayStart(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}

	/**
	 * -給定日期之末
	 * 
	 * @author C3901094
	 * @date 2020年5月4日 上午11:21:09
	 * @param date
	 * @return
	 */
	public static Date getDayEnd(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		return c.getTime();
	}

}

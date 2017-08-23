/**
 * @Description: 
 * @framework.com.champion.utils
 * @FileName:StringUtil.java
 * @Author: William
 * @CreateTime: 2017-06-06 01:39:24
 */
package org.common.util;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 
 * @ClassName: StringUtil
 * @Auther: William
 * @CreateTime: 2017-06-06 01:39:24
 */
public class StringUtil {

	/**
	 * 
	 * @Description: 判断字符串是否为空
	 * @Param: @param input
	 * @Param: @return
	 * @ReturnType boolean
	 * @Author: William
	 * @CreateTime: 2017-06-06 01:40:50
	 */
	public static boolean isEmpty(String input) {
		if (input == null) {
			return true;
		}
		return StringUtils.isEmpty(input);
	}
	
	/**
	 * 
	 * @Description: 判断字符串是否不为空
	 * @Param: @param input
	 * @Param: @return
	 * @ReturnType boolean
	 * @Author: William
	 * @CreateTime: 2017-06-06 01:40:50
	 */
	public static boolean isNotEmpty(String input) {
		return !isEmpty(input);
	}

	/**
	 * @Description 防止SQL注入
	 * @MethodName TransactSQLInjection
	 * @param input
	 * @return java.lang.String
	 * @author william [yeemin_shon@163.com]
	 * @Date 2017/6/15 0015 9:43
	 */
	public static String TransactSQLInjection(Object input) {
		String temp = (String) input;
		if (temp.indexOf(";") > -1) {
			temp = temp.replaceAll(";", "\\;");
		}
		if (temp.indexOf("'") > -1) {
			temp = temp.replaceAll("'", "\'");
		}
		return temp;
	}

//	/**
//	 * @Description 缩略字符串
//	 * @MethodName abbr
//	 * @param str
//	 * @param length
//	 * @return java.lang.String
//	 * @author william [yeemin_shon@163.com]
//	 * @Date 2017/6/26 0026 16:44
//	 */
//	public static String abbr(String str, int length) {
//		if (str == null) {
//			return "";
//		}
//		try {
//			StringBuilder sb = new StringBuilder();
//			int currentLength = 0;
//			for (char c : replaceHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray()) {
//				currentLength += String.valueOf(c).getBytes("GBK").length;
//				if (currentLength <= length - 3) {
//					sb.append(c);
//				} else {
//					sb.append("...");
//					break;
//				}
//			}
//			return sb.toString();
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		return "";
//	}

//	/**
//	 * @Description 替换html标签
//	 * @MethodName replaceHtml
//	 * @param html
//	 * @return java.lang.String
//	 * @author william [yeemin_shon@163.com]
//	 * @Date 2017/6/26 0026 16:44
//	 */
//	public static String replaceHtml(String html) {
//		if (isEmpty(html)){
//			return "";
//		}
//		String regEx = "<.+?>";
//		Pattern p = Pattern.compile(regEx);
//		Matcher m = p.matcher(html);
//		String s = m.replaceAll("");
//		return s;
//	}
//
//	/**
//	 * @Description 反转义html字符
//	 * @MethodName unescapeHtml
//	 * @param html
//	 * @return java.lang.String
//	 * @author songxiangfu [2274466718@qq.com]
//	 * @Date 2017/6/28 14:34
//	 */
//	public static String unescapeHtml(String html){
//		if(isEmpty(html)){
//			return "";
//		}
//		return StringEscapeUtils.unescapeHtml4(html);
//	}
//
//	public static String replaceWinSpace(String input) {
//		return input.replace("%20", " ");
//	}
//
//	public static String htmlId(String input) {
//		String[] strs = input.split("/");
//		if (strs.length > 2) {
//			StringBuilder sb = new StringBuilder();
//			if (isEmpty(strs[0])) {
//				for (int i=2; i<strs.length; i ++) {
//					sb.append(strs[i]).append("-");
//				}
//			} else {
//				for (int i=1; i<strs.length; i ++) {
//					sb.append(strs[i]).append("-");
//				}
//			}
//			return sb.subSequence(0, sb.length()-1).toString();
//		}
//		return "menuId";
//	}
//
//	/**
//	 * @Description 排序因子
//	 * @MethodName threeNumSort
//	 * @param input
//	 * @return java.lang.String
//	 * @author william [yeemin_shon@163.com]
//	 * @Date 2017/7/17 0017 15:12
//	 */
//	public static String threeNumSort(String input) {
//		if (isNotEmpty(input)) {
//			int length = input.length();
//			if (length <3 ) {
//				String prefix = "";
//				for (int i=0; i<3-length; i ++) {
//					prefix += "0";
//				}
//				input = prefix + input;
//			} else if (length > 3) {
//				input = input.substring(length-3, length);
//			}
//		} else {
//			//未写排序数字，默认000
//			return "000";
//		}
//		return input;
//	}
//
//	public static byte[] getBytes(String str){
//		if (str != null){
//			try {
//				return str.getBytes("utf-8");
//			} catch (UnsupportedEncodingException e) {
//				return null;
//			}
//		}else{
//			return null;
//		}
//	}
//
//	public static byte[] getBytesKey(Object object){
//		if(object instanceof String){
//			return getBytes((String)object);
//		}else{
//			return ObjectUtils.serialize(object);
//		}
//	}
//
//	public static byte[] toBytes(Object object){
//		return ObjectUtils.serialize(object);
//	}
//
//	public static Boolean toBoolean(String input) {
//		if (isEmpty(input) || "0".equals(input) || "false".equals(input)) {
//			return Boolean.FALSE;
//		}
//		return Boolean.TRUE;
//	}
}

package com.gupiao.util;
/**
 * String util class
 * @author ykx
 *
 */
public class StringUtils {
	
	/**
	 * judge the string whether empty
	 * @param string
	 * @return
	 */
	public static boolean isEmpty(String string) {
		
		if (string == null || "".equals(string) || string.length() <= 0) {
			return true;
		}
		
		return false;
	}
}

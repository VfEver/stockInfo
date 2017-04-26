package com.gupiao.util;
/**
 * Date util class
 * @author ykx
 *
 */
public class DateUtils {
	
	//put the input into ****/**/** format
	public static String parseToFormat(String input) {
		
		String defaultResult = "2016/06/06";
		
		if(!StringUtils.isEmpty(input)) {
			
			char[] chs = input.toCharArray();
			defaultResult = String.valueOf(chs[0]) + String.valueOf(chs[1]) + 
					String.valueOf(chs[2]) + String.valueOf(chs[3]) + "/" + 
					String.valueOf(chs[4]) + String.valueOf(chs[5]) + "/" +
					String.valueOf(chs[6]) + String.valueOf(chs[7]);
		}
		
		return defaultResult;
	}
}

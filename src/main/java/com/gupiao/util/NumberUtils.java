package com.gupiao.util;
/**
 * number utils class
 * @author ykx
 *
 */
public class NumberUtils {

	public static boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch(Exception ex) {
			return false;
		}
	}
	
	public static boolean isDouble(String input) {
		try {
			Double.parseDouble(input);
			return true;
		} catch(Exception ex) {
			return false;
		}
	}
}

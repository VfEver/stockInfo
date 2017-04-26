package com.gupiao.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

/**
 * find stocks' infos by ali api
 * @author ykx
 *
 */
public class AliAPIUtils {
	
	/**
	 * get stock info
	 * @param querys
	 * @param link
	 * @return
	 */
	public static String getStockInfo(Map<String, String> querys, String link) {
		
	    String host = "https://ali-stock.showapi.com";
	    String path = link;
	    String method = "GET";
	    String appcode = "b6e3a9c9d5924c72b5ac7225c2c85758";
	    
	    Map<String, String> headers = new HashMap<String, String>();
	    headers.put("Authorization", "APPCODE " + appcode);

	    try {
	    	HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
	    	return EntityUtils.toString(response.getEntity());
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		
		return null;
	}
}

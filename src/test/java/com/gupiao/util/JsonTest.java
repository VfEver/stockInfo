package com.gupiao.util;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

public class JsonTest {
	
	
	
	public static void main(String[] args) {
		String link = "/realtime-k";
		
		Map<String, String> querys = new HashMap<String, String>();
		querys.put("beginDay", "20161101");
		querys.put("code", "000651");
		querys.put("time", "day");
		
		String result = AliAPIUtils.getStockInfo(querys, link);
		
		JSONObject json = JSONObject.fromObject(result);
		System.out.println(json.getJSONObject("showapi_res_body").getJSONArray("dataList"));
	}
}

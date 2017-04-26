package com.gupiao.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.gupiao.util.AliAPIUtils;
import com.gupiao.util.NumberUtils;
import com.gupiao.util.StringUtils;
/**
 * get stock handicap info
 * @author ykx
 *
 */
public class StockHandicapServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String code = (String) request.getSession().getAttribute("stockCode");
		
		if (StringUtils.isEmpty(code)) {
			code = "000651";
		}
		
		String link = "/real-stockinfo";
		
		Map<String, String> querys = new HashMap<String, String>();
		querys.put("code", code);
		querys.put("needIndex", "0");
		querys.put("need_k_pic", "0");
		
		String stockInfo = AliAPIUtils.getStockInfo(querys, link);
		
		JSONObject json = JSONObject.fromObject(stockInfo).getJSONObject("showapi_res_body").getJSONObject("stockMarket");
		
		JSONArray sell = new JSONArray();
		JSONArray buy = new JSONArray();
		
		JSONObject temp1 = null;
		JSONObject temp2 = null;
		for (int i = 1; i != 6; ++i) {
			temp1 = new JSONObject();
			temp1.put("name", "卖" + i);
			
			if (NumberUtils.isDouble(json.getString("sell" + i + "_m"))) {
				temp1.put("price", Double.parseDouble(json.getString("sell" + i + "_m")));
			} else {
				temp1.put("price", 0);
			}
			
			if (NumberUtils.isInteger(json.getString("sell" + i + "_n"))) {
				temp1.put("count", Double.parseDouble(json.getString("sell" + i + "_n")));
			} else {
				temp1.put("count", 0);
			}
			sell.add(temp1);
			
			temp2 = new JSONObject();
			temp2.put("name", "买" + i);
			
			if (NumberUtils.isDouble(json.getString("buy" + i + "_m"))) {
				temp2.put("price", Double.parseDouble(json.getString("buy" + i + "_m")));
			} else {
				temp2.put("price", 0);
			}
			
			if (NumberUtils.isInteger(json.getString("buy" + i + "_n"))) {
				temp2.put("count", Double.parseDouble(json.getString("buy" + i + "_n")));
			} else {
				temp2.put("count", 0);
			}
			buy.add(temp2);
		}
		
		JSONObject result = new JSONObject();
		result.put("sell", sell);
		result.put("buy", buy);
		
		response.getWriter().println(result);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}

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
import com.gupiao.util.DateUtils;
import com.gupiao.util.StringUtils;
/**
 * find the stock detail info(k-line)
 * @author ykx
 *
 */
public class StockDetailServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String code = (String) request.getSession().getAttribute("stockCode");
		
		if (StringUtils.isEmpty(code)) {
			code = "000651";
		}
		
		String link = "/realtime-k";
		
		Map<String, String> querys = new HashMap<String, String>();
		querys.put("beginDay", "20161101");
		querys.put("code", code);
		querys.put("time", "day");
		
		String result = AliAPIUtils.getStockInfo(querys, link);
		
		JSONObject json = JSONObject.fromObject(result);
		JSONArray array = json.getJSONObject("showapi_res_body").getJSONArray("dataList");
		
		JSONArray resultArr = new JSONArray();
		JSONObject j = new JSONObject();
		
		for (Object obj : array) {
			JSONObject temp = JSONObject.fromObject(obj);
			
			j.put("date", DateUtils.parseToFormat(temp.getString("time")));
			j.put("volumn", temp.getString("volumn"));
			double[] data = new double[4];
			data[0] = Double.parseDouble(temp.getString("open"));
			data[1] = Double.parseDouble(temp.getString("close"));
			data[2] = Double.parseDouble(temp.getString("min"));
			data[3] = Double.parseDouble(temp.getString("max"));
			
			j.put("value", data);
			
			resultArr.add(j);
		}
		
		response.getWriter().println(resultArr);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

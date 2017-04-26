package com.gupiao.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gupiao.util.AliAPIUtils;
import com.gupiao.util.StringUtils;

/**
 * get stock head detail
 * @author zys
 *
 */
public class StockInfoServlet extends HttpServlet {

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
		
		response.getWriter().println(stockInfo);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

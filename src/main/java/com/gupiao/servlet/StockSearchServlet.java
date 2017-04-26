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
 * search stock
 * @author zys
 *
 */
public class StockSearchServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String stockCode = request.getParameter("stockCode"); 
		if (StringUtils.isEmpty(stockCode)) {
			stockCode = "000651";
		}
		
		Map<String, String> querys = new HashMap<String, String>();
		querys.put("code", stockCode);
	    querys.put("needIndex", "0");
	    querys.put("need_k_pic", "0");
	    
	    String link = "/real-stockinfo";
	    
	    String stockInfo = AliAPIUtils.getStockInfo(querys, link);
	    
	    response.getWriter().println(stockInfo);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doGet(request, response);
	}

}

package com.gupiao.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gupiao.util.AliAPIUtils;
/**
 * get stock list
 * @author ykx
 *
 */
public class StockListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String page = request.getParameter("pageNum");
		
		String link = "/stocklist";
		Map<String, String> querys = new HashMap<String, String>();
	    querys.put("page", page);
		String result = AliAPIUtils.getStockInfo(querys, link);
		
		response.getWriter().println(result);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

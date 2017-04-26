package com.gupiao.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gupiao.util.StringUtils;
/**
 * put stock code into session
 * @author ykx
 *
 */
public class StockSessiontServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String stockCode = request.getParameter("stockCode");
		
		HttpSession session = request.getSession();
		if (!StringUtils.isEmpty(stockCode)) {

			session.setAttribute("stockCode", stockCode);
		} else {
			session.setAttribute("stockCode", "000651");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
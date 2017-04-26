package com.gupiao.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.ibatis.session.SqlSession;

import com.gupiao.dao.UserDao;
import com.gupiao.util.MybatisUtils;
import com.gupiao.util.StringUtils;
/**
 * jedge the stock is self or not
 * @author ykx
 *
 */
public class IsSelfStockServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String stockCode = request.getParameter("stockCode");
		String userID = request.getParameter("userID");
		
		if (StringUtils.isEmpty(stockCode)) {
			stockCode = (String) request.getSession().getAttribute("stockCode");
		}
		
		JSONObject json = new JSONObject();
		if (!StringUtils.isEmpty(userID)) {
			Map<String, String> map = new HashMap<>();
			map.put("userID", userID);
			map.put("stockCode", "%" + stockCode + "%");
			
			SqlSession sqlSession = MybatisUtils.getSqlSession();
			UserDao userDao = sqlSession.getMapper(UserDao.class);
			List<Map<String, String>> list = userDao.isSelfStock(map);
			
			json.put("status", 200);
			if (list.size() > 0) {
				json.put("isSelfStock", true);
			} else {
				json.put("isSelfStock", false);
			}
		}
		
		response.getWriter().println(json);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

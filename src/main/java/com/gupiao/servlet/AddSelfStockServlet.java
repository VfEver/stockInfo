package com.gupiao.servlet;

import java.io.IOException;
import java.util.HashMap;
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
 * add stock into self
 * @author ykx
 *
 */
public class AddSelfStockServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String userID = request.getParameter("userID");
		String stockCode = request.getParameter("stockCode");
		
		JSONObject json = new JSONObject();
		
		if (!StringUtils.isEmpty(userID) && !StringUtils.isEmpty(stockCode)) {
			
			json.put("status", 200);
			SqlSession sqlSession = MybatisUtils.getSqlSession();
			UserDao userDao = sqlSession.getMapper(UserDao.class);
			Map<String, String> map = new HashMap<>();
			map.put("userID", userID);
			map.put("stockCode", stockCode);
			
			userDao.saveUserSelfStock(map);
			
			sqlSession.commit();
			MybatisUtils.closeSqlSession();
		} else {
			json.put("status", -1);
			json.put("reason", "出现问题，请重试");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

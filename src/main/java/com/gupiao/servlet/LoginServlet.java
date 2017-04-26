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
import com.gupiao.entity.User;
import com.gupiao.util.MybatisUtils;
import com.gupiao.util.StringUtils;
/**
 * user login
 * @author ykx
 *
 */
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		JSONObject json = new JSONObject();
		
		if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
			
			SqlSession sqlSession = MybatisUtils.getSqlSession();
			Map<String, String> map = new HashMap<String, String>();
			map.put("username", username);
			map.put("password", password);
			UserDao userDao = sqlSession.getMapper(UserDao.class);
			User user = userDao.findUser(map);
			MybatisUtils.closeSqlSession();
			if (user != null) {
				json.put("status", 200);
				json.put("userID", user.getUserID());
				json.put("username", user.getUsername());
			} else {
				json.put("status", -1);
				json.put("reason", "账号或者密码错误，请重新登录");
			}
		}
		
		response.getWriter().println(json);
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doGet(request, response);
	}

}
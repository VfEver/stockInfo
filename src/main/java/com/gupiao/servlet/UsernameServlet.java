package com.gupiao.servlet;

import java.io.IOException;

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

public class UsernameServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String username = request.getParameter("username");
		JSONObject json = new JSONObject();
		if (!StringUtils.isEmpty(username)) {
			SqlSession sqlSession = MybatisUtils.getSqlSession();
			UserDao userDao = sqlSession.getMapper(UserDao.class);
			User user = userDao.findUserByUsername(username);
			if (user != null) {
				json.put("reason", "此用户名已经被注册");
				json.put("used", true);
			} else {
				json.put("used", false);
			}
		}
		response.getWriter().println(json);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

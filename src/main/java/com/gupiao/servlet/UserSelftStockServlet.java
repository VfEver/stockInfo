package com.gupiao.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;

import com.gupiao.dao.UserDao;
import com.gupiao.util.AliAPIUtils;
import com.gupiao.util.MybatisUtils;
import com.gupiao.util.StringUtils;
/**
 * find user self stock
 * @author ykx
 *
 */
public class UserSelftStockServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String userID = request.getParameter("userID");
		if (!StringUtils.isEmpty(userID)) {
			SqlSession sqlSession = MybatisUtils.getSqlSession();
			UserDao userDao = sqlSession.getMapper(UserDao.class);
			
			List<String> stockCodes = userDao.findUserSelfStock(userID);
			
			StringBuilder codes = new StringBuilder();
			for (String code : stockCodes) {
				if (codes.length() > 0) {
					codes.append(",");
				}
				codes.append(code);
			}
			
			String link = "/batch-real-stockinfo";
			
			Map<String, String> querys = new HashMap<String, String> ();
			querys.put("needIndex", "0");
			querys.put("stocks", codes.toString());
			
			String result = AliAPIUtils.getStockInfo(querys, link);
			
			response.getWriter().println(result);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

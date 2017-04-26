package com.gupiao.util;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * mybatis utils class,create sqlSessionFactory
 * @author ykx
 *
 */
public class MybatisUtils {
	
	private static SqlSessionFactory sqlSessionFactory = null;
	private static SqlSession sqlSession = null;
	
	/**
	 * get SqlSession
	 * @return
	 */
	public static SqlSession getSqlSession() {
		
		InputStream inputStream = null;
		if (sqlSessionFactory == null) {
			try {
				String resource = "mybatis.xml";
				inputStream = Resources.getResourceAsStream(resource);
				sqlSessionFactory = new SqlSessionFactoryBuilder().build
						(inputStream);
				return sqlSessionFactory.openSession();
			} catch (Exception e){
				e.printStackTrace();
				return null;
			}
		} else {
			
			return sqlSessionFactory.openSession();
		}
		
	}
	
	/**
	 * close sqlSession
	 */
	public static void closeSqlSession() {
		
		if (sqlSession != null) {
			try {
				sqlSession.close();
			}  catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

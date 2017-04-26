package com.gupiao.dao;

import java.util.List;
import java.util.Map;

import com.gupiao.entity.User;
/**
 * user dao
 * @author ykx
 *
 */
public interface UserDao {
	
	/**
	 * save user
	 * @param user
	 */
	public void saveUser(User user);
	
	/**
	 * find user by username and password
	 * @param username
	 * @param password
	 * @return
	 */
	public User findUser(Map<String, String> map);
	
	/**
	 * 将股票添加用户自选股
	 * @param map
	 */
	public void saveUserSelfStock(Map<String, String> map);
	
	/**
	 * 用户天机自选股之后，增加此股票被关注的人数
	 * @param stockCode
	 */
	public void updateStockCount(String stockCode);
	
	/**
	 * 查询用户的所有自选股
	 * @param userID
	 * @return
	 */
	public List<String> findUserSelfStock(String userID);
	
	/**
	 * 判断是否为自选股
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> isSelfStock(Map<String, String> map);
	
	/**
	 * 根据用户名查询用户
	 * @param username
	 * @return
	 */
	public User findUserByUsername(String username);
}

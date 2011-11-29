package cn.edu.sdut.openeshop.data;

import java.util.List;

import cn.edu.sdut.openeshop.model.User;

public interface UserStoreInf {
	/**
	 * 根据用户名检查用户是否已经存在？
	 * @param username
	 * @return true，如果用户已经存在了，否则false
	 */
	public boolean userExists(String username);
	
	/**
	 * 将用户user保存起来
	 * @param user
	 */
	public void addUser(User user);
	
	/**
	 * 列出所有的用户
	 * @return 用户列表
	 */
	public List<User> getAllUsers();
}

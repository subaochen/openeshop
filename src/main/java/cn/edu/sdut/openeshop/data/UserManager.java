package cn.edu.sdut.openeshop.data;

import java.util.List;

import javax.ejb.Local;
import javax.inject.Named;

import cn.edu.sdut.openeshop.model.Member;

public interface UserManager {
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
	public void addUser(Member user);
	
	/**
	 * 根据用户名查找用户
	 * @param username 用户名
	 * @return User对象
	 */
	public Member findUser(String username);
	
	/**
	 * 列出所有的用户
	 * @return 用户列表
	 */
	public List<Member> getAllUsers();
}

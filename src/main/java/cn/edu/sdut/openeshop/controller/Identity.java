package cn.edu.sdut.openeshop.controller;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import cn.edu.sdut.openeshop.data.UserStoreInMem;
import cn.edu.sdut.openeshop.model.User;

@Model
public class Identity {
	private User user;

	@Inject
	UserStoreInMem userStoreInMem;
	@Produces
	@SessionScoped
	private User currentUser;

	public void register() {
		if (getUser().getPassword() != null
				&& getUser().getPassword().equalsIgnoreCase(
						getUser().getRePassword())) {
			System.out.println("user:" + getUser().getUsername()
					+ " registered");
			if (userStoreInMem.userExists(getUser().getUsername())) {
				System.out.println("user:" + getUser().getUsername()
						+ " has exists!");
			} else {
				userStoreInMem.addUser(user);
			}
		}
	}

	/**
	 * 登录
	 * 
	 * TODO 首先演示直接返回viewId的页面跳转方式
	 * 
	 * @return
	 */
	public String login() {
		// 确保用户输入了用户名和密码，并且用户名已经注册过
		if (user == null || user.getUsername() == null
				|| user.getUsername().isEmpty() || user.getPassword() == null
				|| user.getPassword().isEmpty()
				|| !userStoreInMem.userExists(user.getUsername()))
			return "failed";

		currentUser = userStoreInMem.findUser(user.getUsername());
		if (user.getPassword().equalsIgnoreCase(currentUser.getPassword())) {
			System.out.println("user:" + currentUser.getUsername() + " logged in");
			return "loggedIn";
		}

		return "falied";
	}

	public User getUser() {
		if (user == null)
			user = new User();
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
}

package cn.edu.sdut.openeshop.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import cn.edu.sdut.openeshop.data.UserStoreInMem;
import cn.edu.sdut.openeshop.model.User;

@Named
@RequestScoped
public class Register {
	private User user = new User();
	private String rePassword;

	@Inject
	UserStoreInMem userStoreInMem;

	public String register() {
		if (getUser().getPassword() != null
				&& getUser().getPassword().equalsIgnoreCase(getRePassword())) {
			System.out.println("user:" + getUser().getUsername()
					+ " registered");
			if (userStoreInMem.userExists(getUser().getUsername())) {
				System.out.println("user:" + getUser().getUsername()
						+ " has exists!");
			} else {
				userStoreInMem.addUser(user);
			}
		}

		return "/register.jsf";
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRePassword() {
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

}

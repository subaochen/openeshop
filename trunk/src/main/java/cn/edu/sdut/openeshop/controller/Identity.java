package cn.edu.sdut.openeshop.controller;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import cn.edu.sdut.openeshop.data.UserStoreInMem;
import cn.edu.sdut.openeshop.model.User;

@Model
public class Identity {
	private User user;
	
	@Inject
	UserStoreInMem userStoreInMem;

	public void register() {
		if (getUser().getPassword() != null
				&& getUser().getPassword().equalsIgnoreCase(
						getUser().getRePassword())) {
			System.out.println("user:" + getUser().getUsername()
					+ " registered");
			if(userStoreInMem.userExists(getUser().getUsername())){
				System.out.println("user:" + getUser().getUsername() + " has exists!");
			} else {
				userStoreInMem.addUser(user);
			}
		}
	}

	public User getUser() {
		if(user == null) user = new User();
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}

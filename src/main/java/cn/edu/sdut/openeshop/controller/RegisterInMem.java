package cn.edu.sdut.openeshop.controller;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import cn.edu.sdut.openeshop.data.StoreInMem;
import cn.edu.sdut.openeshop.data.UserManager;
import cn.edu.sdut.openeshop.data.UserManagerInMem;

@Named("register")
@RequestScoped
@Alternative
public class RegisterInMem extends RegisterBase implements Register{

	@Inject @StoreInMem
	private UserManager userStoreInMem;

	public String register() {
		if (getUser().getPassword() != null
				&& getUser().getPassword().equalsIgnoreCase(getRePassword())) {
			System.out.println("user:" + getUser().getUsername()
					+ " registered");
			if (userStoreInMem.userExists(getUser().getUsername())) {
				System.out.println("user:" + getUser().getUsername()
						+ " has exists!");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("用户名已经存在了！"));
				return "/register.jsf";
			} else {
				userStoreInMem.addUser(user);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("用户注册成功！"));
				return "/login.jsf";
			}
		}

		System.out.println("user=" + user);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("两次输入的密码不一致，请重新注册！"));
		return "/register.jsf";
	}

}

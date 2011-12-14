package cn.edu.sdut.openeshop.controller;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import cn.edu.sdut.openeshop.data.UserManager;
import cn.edu.sdut.openeshop.model.Member;

@Named("register")
@RequestScoped
public class RegisterBean implements RegisterInf {
	private Member user = new Member();
	private String rePassword;
	
	@Inject
	private UserManager um;

	@Override
	public String register() {
		if(um.userExists(user.getUsername())) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("用户名已经存在了！"));
			return "/register.jsf";
		}
		
		um.addUser(user);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("用户注册成功！"));

		return "/login.jsf";
	}

	public String getRePassword() {
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

	public Member getUser() {
		return user;
	}

	public void setUser(Member user) {
		this.user = user;
	}

}

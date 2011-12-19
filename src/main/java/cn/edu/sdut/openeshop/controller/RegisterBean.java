package cn.edu.sdut.openeshop.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import cn.edu.sdut.openeshop.data.UserManager;
import cn.edu.sdut.openeshop.model.Member;

@Named("register")
@RequestScoped
public class RegisterBean extends RegisterBase implements Register {
	
	@Inject
	private UserManager um;
	
	public RegisterBean(){
		System.out.println("使用无参构造方法创建 RegisterBean, UserManager = " + um);
	}

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
	
	@PostConstruct
	public void postConstruct(){
		System.out.println("RegisterBean:调用PostConstruct,UserManager = " + um);
		
	}
}

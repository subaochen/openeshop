package cn.edu.sdut.openeshop.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import cn.edu.sdut.openeshop.data.UserManager;
import cn.edu.sdut.openeshop.model.Member;

@Named("register")
@RequestScoped
public class RegisterBean extends RegisterBase implements Register {
	
	private UserManager um;
	
	@Inject @Registered Event<Member> registeredEvent;
	
	public RegisterBean(){
		System.out.println("使用无参构造方法创建 RegisterBean, UserManager = " + getUm());
	}

	@Override
	public String register() {
		if(getUm().userExists(user.getUsername())) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("用户名已经存在了！"));
			return "/register.jsf";
		}
		
		getUm().addUser(user);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("用户注册成功！"));
		registeredEvent.fire(user);

		return "/login.jsf";
	}
	
	@PostConstruct
	public void postConstruct(){
		System.out.println("RegisterBean:调用PostConstruct,UserManager = " + getUm());
		
	}

	public UserManager getUm() {
		return um;
	}

	@Inject
	public void setUm(UserManager um) {
		this.um = um;
		System.out.println("使用set方法创建 RegisterBean, UserManager = " + getUm());
	}
}

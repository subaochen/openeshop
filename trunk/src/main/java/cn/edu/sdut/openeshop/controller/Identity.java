package cn.edu.sdut.openeshop.controller;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.FaceletContext;
import javax.inject.Inject;
import javax.inject.Named;

import cn.edu.sdut.openeshop.data.StoreInDb;
import cn.edu.sdut.openeshop.data.UserManager;
import cn.edu.sdut.openeshop.data.UserManagerInMem;
import cn.edu.sdut.openeshop.model.Member;

@Model
public class Identity {
	@Inject
	Credentials credentials;
	
	@Inject @StoreInDb
	UserManager um;

	@Produces
	@Named
	@SessionScoped
	@LoggedIn
	private Member currentUser;

	/**
	 * 登录
	 * 
	 * TODO 首先演示直接返回viewId的页面跳转方式
	 * 
	 * @return
	 */
	public String login() {
		// 确保用户输入了用户名和密码，并且用户名已经注册过
		// TODO 通过JSF的表单验证防止用户名和密码为空
		if (credentials.getUsername() == null
				|| credentials.getUsername().isEmpty() || credentials.getPassword() == null
				|| credentials.getPassword().isEmpty()
				|| !um.userExists(credentials.getUsername())){
			System.out.println("can not login in,credentials = " + credentials);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("登录失败，用户名或者密码错误"));
			return "/login.jsf";
		}

		currentUser = um.findUser(credentials.getUsername());
		if (credentials.getPassword().equalsIgnoreCase(currentUser.getPassword())) {
			System.out.println("user:" + currentUser.getUsername() + " logged in");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("欢迎您，" + currentUser.getUsername()));
			return "/cp/profile.jsf";
		}

		System.out.println("can not login in:other reason");
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("登录失败，请联系管理员"));
		return "/login.jsf";
	}
	
	public boolean isLoggedIn(){
		return currentUser != null;
	}
	
	public String logout(){
		currentUser = null;
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("您已退出登录，请重新登录"));
		return "/login.jsf";
	}

	public Member getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(Member currentUser) {
		this.currentUser = currentUser;
	}
}

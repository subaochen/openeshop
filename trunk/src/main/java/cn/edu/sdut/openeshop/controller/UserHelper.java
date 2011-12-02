package cn.edu.sdut.openeshop.controller;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import cn.edu.sdut.openeshop.data.UserStoreInMem;
import cn.edu.sdut.openeshop.model.User;

@Named
public class UserHelper {
	@Inject
	UserStoreInMem userStoreInMem;
	
	// TODO 这里如何避免使用LoggedIn而不会产生歧义？
	@Inject @LoggedIn
	User currentUser;
	
	/**
	 * 保存个人资料
	 * @return
	 */
	public String saveProfile(){
		System.out.println("currentUser=" + currentUser);
		if(!currentUser.getPassword().equalsIgnoreCase(currentUser.getRePassword())){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("两次输入密码不一致，无法保存用户个人资料"));	
		} else {
		    userStoreInMem.addUser(currentUser);
		    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("成功保存用户个人资料"));
		}
		
		return "/profile.jsf";
	}

}

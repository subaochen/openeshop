package cn.edu.sdut.openeshop.controller;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import cn.edu.sdut.openeshop.data.UserManagerInMem;
import cn.edu.sdut.openeshop.model.Member;

@Named
public class UserHelper {
	@Inject
	UserManagerInMem userStoreInMem;
	
	@Inject 
	@LoggedIn
	Member currentUser;
	
	/**
	 * 保存个人资料
	 * @return
	 */
	public String saveProfile(){
//		System.out.println("currentUser=" + currentUser);
//		if(!currentUser.getPassword().equalsIgnoreCase(currentUser.getRePassword())){
//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("两次输入密码不一致，无法保存用户个人资料"));	
//		} else {
//		    userStoreInMem.addUser(currentUser);
//		    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("成功保存用户个人资料"));
//		}
		
		return "/profile.jsf";
	}

}

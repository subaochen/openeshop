package cn.edu.sdut.openeshop.controller;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import cn.edu.sdut.openeshop.data.UserManager;
import cn.edu.sdut.openeshop.model.Member;

@Named
@RequestScoped
public class UserHelper {
	private String newPassword = "";
	private String oldPassword = "";
	private String rePassword = "";

	@Inject
	UserManager um;

	@Inject
	@LoggedIn
	Member currentUser;

	/**
	 * 保存个人资料，目前只修改用户密码和邮箱
	 * 
	 * @return
	 */
	public String saveProfile() {
		System.out.println("currentUser=" + currentUser + ",newPassword="
				+ newPassword + ",rePassword=" + rePassword);
		if (!currentUser.getPassword().equalsIgnoreCase(oldPassword)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("老密码输入错误，无法保存用户个人资料"));
		} else if (!newPassword.equalsIgnoreCase(rePassword)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("两次密码输入不一致，无法保存用户个人资料"));
		} else {
			currentUser.setPassword(newPassword);
			um.updateUser(currentUser);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("成功保存用户个人资料"));
		}

		return "/cp/profile.jsf";
	}

	public String getRePassword() {
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}

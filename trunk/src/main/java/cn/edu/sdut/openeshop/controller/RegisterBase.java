package cn.edu.sdut.openeshop.controller;

import cn.edu.sdut.openeshop.model.Member;

public class RegisterBase {
	protected Member user = new Member();
	protected String rePassword;
	
	public RegisterBase() {
		System.out.println("使用无参构造方法创建 RegisterBase");
	}
	
	public Member getUser() {
		return user;
	}
	public void setUser(Member user) {
		this.user = user;
	}
	public String getRePassword() {
		return rePassword;
	}
	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}
	
}

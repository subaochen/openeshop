package cn.edu.sdut.openeshop.controller;

import javax.enterprise.inject.Model;

@Model
public class Identity {
	private String username;
	private String password;
	private String rePassword;
	private String email;
	
	public void register(){
		if(password != null && password.equalsIgnoreCase(rePassword)){
			System.out.println("user:" + username + " registered");
		}
	}
	
	public void hello(){
		System.out.println("hello");
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRePassword() {
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}

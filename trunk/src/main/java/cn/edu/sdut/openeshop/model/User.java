package cn.edu.sdut.openeshop.model;

public class User {
	private String username;
	private String password;
	private String rePassword;
	private String email;
	
	public User(){
		this.username = "";
		this.password = "";
		this.rePassword = "";
		this.email = "";
	}
	
	public User(String username,String password){
		this.username = username;
		this.password = password;
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

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password
				+ ", rePassword=" + rePassword + ", email=" + email + "]";
	}
}

package cn.edu.sdut.openeshop.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "member")
public class Member implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String username;
	private String password;
	private String email;

	public Member() {
		this.username = "";
		this.password = "";
		this.email = "";
	}

	public Member(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@Id
	@SequenceGenerator(name = "member_seq", sequenceName = "member_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Member [id=" + id + ", username=" + username + ", password="
				+ password + ", email=" + email + "]";
	}


}

package cn.edu.sdut.openeshop.controller;

import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cn.edu.sdut.openeshop.model.Member;

@Named("register")
@RequestScoped
@Stateful
public class RegisterBean implements RegisterInf {
	private Member user = new Member();
	private String rePassword;
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public String register() {
		List<Member> list = em
				.createQuery(
						"select user from User user where user.username=:username")
				.setParameter("username", getUser().getUsername())
				.getResultList();
		
		if(!list.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("用户名已经存在了！"));
			return "/register.jsf";
		}
		
		em.persist(getUser());
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("用户注册成功！"));

		return "/login.jsf";
	}

	public String getRePassword() {
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

	public Member getUser() {
		return user;
	}

	public void setUser(Member user) {
		this.user = user;
	}

}

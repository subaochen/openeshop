package cn.edu.sdut.openeshop.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cn.edu.sdut.openeshop.model.Member;

@Stateless
public class UserManagerBean implements UserManager {
	@PersistenceContext
	private EntityManager em;

	public UserManagerBean() {
		System.out.println("使用无参构造方法创建 UserManagerBean");
	}

	public void addUser(Member user) {
		em.persist(user);
	}

	/**
	 * 用户username是否存在？
	 * 
	 * @param username
	 * @return true，如果用户已经存在；false，如果用户不存在
	 */
	public boolean userExists(String username) {
		if (findUser(username) != null)
			return true;
		else
			return false;
	}

	/**
	 * 获得所有用户
	 * 
	 * @return
	 * 
	 *         TODO 如何防止被执行多遍？
	 */
	@Produces
	@Named
	public List<Member> getAllUsers() {
		return em.createQuery("from Member m").getResultList();
	}

	public Member findUser(String username) {
		List<Member> list = em
				.createQuery("from Member m where m.username=:username")
				.setParameter("username", username).getResultList();
		if (list.isEmpty())
			return null;
		else
			return list.get(0);

	}

	@Override
	public String toString() {
		return "UserManagerBean [em=" + em + "]";
	}
	
	@PostConstruct
	public void postConstruct(){
		System.out.println("UserManagerBean:调用PostConstruct, em = " + em);
	}

	@Override
	public void updateUser(Member user) {
		em.merge(user);
	}


}

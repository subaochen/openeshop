package cn.edu.sdut.openeshop.data;

import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cn.edu.sdut.openeshop.model.Member;

@RequestScoped
@Stateful
public class UserManager {
	@PersistenceContext
	private EntityManager em;
	
	public void addUser(Member user){
		em.persist(user);
	}
	
	/**
	 * 用户username是否存在？
	 * @param username
	 * @return true，如果用户已经存在；false，如果用户不存在
	 */
	public boolean userExists(String username){
		if(findUser(username) != null) return true;
		else return false;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Member> getAllUsers(){
		return em.createQuery("from Member m").getResultList();
	}


	public Member findUser(String username) {
		List<Member> list = em.createQuery("from Member m where m.username=:username").setParameter("username", username).getResultList();
		if(list.isEmpty()) return null;
		else return list.get(0);
		
	}

}

package cn.edu.sdut.openeshop.data;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cn.edu.sdut.openeshop.model.Goods;

@Stateless
public class GoodsManager {
	@PersistenceContext
	private EntityManager em;
	
	@Produces @Named
	public List<Goods> getAllGoods(){
		return em.createQuery("from Goods goods").getResultList();
	}

}

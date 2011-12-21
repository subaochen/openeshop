package cn.edu.sdut.openeshop.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cn.edu.sdut.openeshop.model.Goods;
import cn.edu.sdut.openeshop.tools.HttpParam;

@Stateless
@Named
public class GoodsManager {
	@PersistenceContext
	private EntityManager em;
	
	private Goods instance;
	
	@HttpParam("goodsId") String goodsId;
	
	public GoodsManager(){
		System.out.println("goodsId=" + goodsId);
		if(instance == null) instance = new Goods();
		Long id = instance.getId();
		if(id != null && id != 0) instance = loadInstance(id);
	}
	
	private Goods loadInstance(Long id) {
		return em.find(Goods.class, id);
	}

	@Produces @Named
	public List<Goods> getAllGoods(){
		return em.createQuery("from Goods goods").getResultList();
	}
	
	public void persist(){
		em.persist(instance);
	}
	
	public void update(){
		em.merge(instance);
	}

	public Goods getInstance() {
		return instance;
	}

	public void setInstance(Goods instance) {
		this.instance = instance;
	}
	
	public boolean isManaged(){
		return instance != null & instance.getId() != null && instance.getId() != 0;
	}
	
	@PostConstruct
	public void postConstruct(){
		System.out.println("goods id =" + goodsId);
	}
}

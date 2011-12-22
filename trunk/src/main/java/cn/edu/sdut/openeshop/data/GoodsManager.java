package cn.edu.sdut.openeshop.data;

import java.util.List;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cn.edu.sdut.openeshop.model.Goods;

@Stateless
@Named
public class GoodsManager {
	@PersistenceContext
	private EntityManager em;
	
	private Goods instance = new Goods();
	private Long goodsId;
	
	
	public void wire(){
		System.out.println("call goodsManager wire,goodsId="+goodsId);
		if(getGoodsId() != null && getGoodsId() != 0) 
			loadInstance(getGoodsId());
	}
	
	private void loadInstance(Long id) {
		instance = em.find(Goods.class, id);
	}

	@Produces @Named
	public List<Goods> getAllGoods(){
		return em.createQuery("from Goods goods").getResultList();
	}
	
	public String persist(){
		em.persist(instance);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("成功保存商品信息"));
		return "/admin/goods_list.jsf";
	}
	
	public String update(){
		em.merge(instance);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("成功更新商品信息"));
		return "/admin/goods_list.jsf";
	}

	public Goods getInstance() {
		return instance;
	}

	public void setInstance(Goods instance) {
		this.instance = instance;
	}
	
	public boolean isManaged(){
		return instance != null && instance.getId() != null && instance.getId() != 0;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}
}
